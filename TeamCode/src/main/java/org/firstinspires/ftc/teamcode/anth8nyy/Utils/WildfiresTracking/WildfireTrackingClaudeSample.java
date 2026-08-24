package org.firstinspires.ftc.teamcode.anth8nyy.Utils.WildfiresTracking;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * WildfireTrackingClaudeSample
 * Pipeline (OpenCV) που εντοπίζει μια μπάλα συγκεκριμένου χρώματος μέσω
 * HSV thresholding. Δεν περιέχει καμία λογική OpMode - μόνο επεξεργασία
 * εικόνας. Χρησιμοποιείται από ένα ξεχωριστό OpMode αρχείο, το οποίο
 * καλεί τις public μεθόδους της (getBallCenterX(), isBallFound(), κλπ).
 */
public class WildfireTrackingClaudeSample extends OpenCvPipeline {

    // ============================================================
// FULLY COMMENTED VERSION - every line explained
// ============================================================

    // Lower HSV bound for the color we want to detect.
// Scalar(Hue, Saturation, Value) -> H=5, S=100, V=100
// Anything BELOW these values (in at least one channel) will NOT match.
    private Scalar lowerBound = new Scalar(5, 100, 100);

    // Upper HSV bound for the color we want to detect.
// H=20, S=255, V=255
// Anything ABOVE these values will NOT match.
// Together, lowerBound/upperBound define a "slice" of the HSV color space
// (here, roughly orange) that we consider "ball color".
    private Scalar upperBound = new Scalar(20, 255, 255);

    // Mat = OpenCV's basic image/matrix container (like a 2D array of pixels).
// hsvMat will hold the current frame AFTER converting it from RGB(A) to HSV.
    private Mat hsvMat = new Mat();

    // maskMat will hold the BINARY mask: white pixels = matched the color range,
// black pixels = did not match. This is what we run contour detection on.
    private Mat maskMat = new Mat();

    // hierarchy is a required output parameter for findContours() - it stores
// parent/child relationships between contours (e.g. holes inside shapes).
// We don't use it here, but the OpenCV method signature requires it.
    private Mat hierarchy = new Mat();

    // Minimum contour area (in pixels^2) to be considered a "real" ball
// detection, not just random noise/small color speckles in the frame.
// 'static final' = constant, shared by all instances, never changes at runtime.
    private static final double MIN_AREA_THRESHOLD = 500;

// --- Detection results (thread-safe via volatile) ---

    // True if a ball was found in the LAST processed frame, false otherwise.
// 'volatile' guarantees that when the camera thread updates this value,
// the OpMode thread (running loop()) always sees the latest value
// immediately, instead of a possibly cached/stale copy.
    private volatile boolean ballFound = false;

    // X pixel-coordinate of the ball's center in the last frame.
// -1 means "no ball found" (a safe placeholder impossible pixel coordinate).
    private volatile int ballCenterX = -1;

    // Y pixel-coordinate of the ball's center in the last frame.
// Same -1 = "not found" convention as ballCenterX.
    private volatile int ballCenterY = -1;

    // Area (in pixels^2) of the detected ball's bounding contour.
// Useful later to estimate distance: bigger area = ball is closer to camera.
    private volatile double ballArea = 0;

    // @Override marks that this method overrides the abstract method
// defined in the parent class OpenCvPipeline. This method is called
// AUTOMATICALLY by EasyOpenCV for every single camera frame.
    @Override
    public Mat processFrame(Mat input) {
        // 'input' = the raw frame captured by the webcam this cycle (as a Mat).

        // Convert the input frame's color space from RGB to HSV, and store
        // the result in hsvMat. HSV separates color (Hue) from brightness
        // (Value), which makes color-based detection much more stable under
        // different lighting conditions than plain RGB.
        // NOTE: EasyOpenCV frames are often RGBA, not RGB - if colors look
        // wrong, try Imgproc.COLOR_RGBA2HSV instead.
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);
        // Core.inRange scans every pixel in hsvMat. If a pixel's HSV value
        // falls between lowerBound and upperBound (inclusive), the
        // corresponding pixel in maskMat is set to white (255).
        // Otherwise it's set to black (0). Result: a black & white mask
        // image where white = "this pixel looks like our target color".
        Core.inRange(hsvMat, lowerBound, upperBound, maskMat);

        // Erode "shrinks" white regions in the mask - it removes small,
        // isolated white specks (noise) that don't represent the real ball.
        // The empty 'new Mat()' argument means "use the default 3x3 kernel".
        Imgproc.erode(maskMat, maskMat, new Mat());

        // Dilate "grows" white regions back - it restores the size of the
        // real ball blob (which erode also shrank), while the small noise
        // specks removed by erode stay gone. Erode+dilate together = a
        // standard noise-cleanup technique called "opening".
        Imgproc.dilate(maskMat, maskMat, new Mat());

        // Create an empty list to hold all contours (outlines of white
        // blobs) that OpenCV will find in the mask.
        List<MatOfPoint> contours = new ArrayList<>();

        // findContours scans maskMat and detects the outline of every
        // separate white region, storing each outline as a MatOfPoint
        // (a list of (x,y) points) inside the 'contours' list.
        // RETR_EXTERNAL = only outer contours (ignore holes inside shapes).
        // CHAIN_APPROX_SIMPLE = compress the contour points to save memory
        // (e.g. store a straight line as just its 2 endpoints).
        Imgproc.findContours(maskMat, contours, hierarchy,
                Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Variable to track the largest contour area found so far in this frame.
        double maxArea = 0;

        // Variable to store a reference to the actual largest contour object.
        // Starts as null (nothing found yet).
        MatOfPoint biggestContour = null;

        // Loop through every contour that was detected in this frame.
        for (MatOfPoint contour : contours) {

            // Calculate the area (in pixels^2) enclosed by this contour's outline.
            double area = Imgproc.contourArea(contour);

            // If this contour is bigger than the biggest one we've seen so far...
            if (area > maxArea) {
                // ...update maxArea to this new bigger value...
                maxArea = area;
                // ...and remember THIS contour as the current "biggest".
                biggestContour = contour;
            }
        }
        // After the loop: biggestContour holds the largest white blob found,
        // which we assume is the ball (assuming it's the biggest colored
        // object of that color in the frame).

        // Only treat this as a valid detection if:
        // 1) we actually found at least one contour (not null), AND
        // 2) its area is big enough to not be random noise/false positive.
        if (biggestContour != null && maxArea > MIN_AREA_THRESHOLD) {

            // Calculate the smallest upright rectangle that fully contains
            // the biggest contour - gives us x, y, width, height.
            Rect boundingRect = Imgproc.boundingRect(biggestContour);

            // Mark that a ball WAS found this frame.
            ballFound = true;

            // Calculate the horizontal center of the bounding box:
            // left edge (x) + half the width = the middle X pixel.
            ballCenterX = boundingRect.x + boundingRect.width / 2;

            // Calculate the vertical center of the bounding box the same way.
            ballCenterY = boundingRect.y + boundingRect.height / 2;

            // Store the contour's area - can be used later to estimate
            // how far away the ball is (bigger area = closer to camera).
            ballArea = maxArea;

            // Draw a GREEN rectangle around the detected ball directly on
            // the 'input' frame, so you can see the detection box live in
            // the camera preview. Scalar(0,255,0) = green, thickness = 3px.
            Imgproc.rectangle(input, boundingRect, new Scalar(0, 255, 0), 3);

            // Draw a filled RED circle (radius 6px) at the ball's center
            // point on the 'input' frame. Scalar(255,0,0) = red,
            // thickness = -1 means "filled" instead of just an outline.
            Imgproc.circle(input, new Point(ballCenterX, ballCenterY), 6,
                    new Scalar(255, 0, 0), -1);

        } else {
            // No valid ball detected this frame - reset all result fields
            // back to their "not found" default values.
            ballFound = false;
            ballCenterX = -1;
            ballCenterY = -1;
            ballArea = 0;
        }

        // processFrame() MUST return a Mat - this is the image that gets
        // shown in the camera preview / Driver Station. We return 'input'
        // (which now has the green box + red dot drawn on it, if a ball
        // was found), so you can visually confirm the detection is working.
        return input;
    }
    // ================= Public methods για χρήση από το OpMode =================

    /** @return true αν εντοπίστηκε μπάλα στο τελευταίο frame */
    public boolean isBallFound() {
        return ballFound;
    }

    /** @return X συντεταγμένη (pixels) του κέντρου της μπάλας, ή -1 αν δεν βρέθηκε */
    public int getBallCenterX() {
        return ballCenterX;
    }

    /** @return Y συντεταγμένη (pixels) του κέντρου της μπάλας, ή -1 αν δεν βρέθηκε */
    public int getBallCenterY() {
        return ballCenterY;
    }

    /** @return εμβαδόν (pixels^2) του contour της μπάλας - χρήσιμο για εκτίμηση απόστασης */
    public double getBallArea() {
        return ballArea;
    }

    /** Επιτρέπει αλλαγή των HSV ορίων χρώματος run-time (π.χ. από το OpMode ή dashboard) */
    public void setColorBounds(Scalar newLower, Scalar newUpper) {
        this.lowerBound = newLower;
        this.upperBound = newUpper;
    }
}