package org.firstinspires.ftc.teamcode.anth8nyy.Utils.WildfiresTracking;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * BallTrackingOpMode
 *
 * Iterative OpMode (extends OpMode, ΟΧΙ LinearOpMode) που ανοίγει την
 * webcam, τρέχει την pipeline WildfireTrackingClaudeSample, και καλεί
 * τις public μεθόδους της για να πάρει τα αποτελέσματα εντοπισμού.
 *
 * Δομή OpMode (iterative):
 *  - init()      -> καλείται 1 φορά όταν πατάς INIT
 *  - init_loop() -> επαναλαμβάνεται μέχρι να πατήσεις PLAY (προαιρετικό)
 *  - start()     -> καλείται 1 φορά όταν πατάς PLAY
 *  - loop()      -> επαναλαμβάνεται συνεχώς όσο τρέχει το OpMode
 *  - stop()      -> καλείται 1 φορά όταν σταματήσει το OpMode
 */
@TeleOp(name = "Ball Tracking OpMode", group = "Vision")
public class WildfireTrackingClaudeOpMode  extends OpMode {

    private OpenCvWebcam webcam;
    private WildfireTrackingClaudeSample pipeline;

    private static final int FRAME_WIDTH = 640;
    private static final int FRAME_HEIGHT = 480;
    private static final int FRAME_CENTER_X = FRAME_WIDTH / 2;

    @Override
    public void init() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        // Δημιουργούμε το αντικείμενο pipeline από το άλλο αρχείο
        // και απλά καλούμε τις μεθόδους του - όλη η λογική vision είναι εκεί.
        pipeline = new WildfireTrackingClaudeSample();
        webcam.setPipeline(pipeline);

        webcam.setMillisecondsPermissionTimeout(2500);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(FRAME_WIDTH, FRAME_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", errorCode);
            }
        });

        telemetry.addLine("Pipeline initialized - waiting for start");
    }

    @Override
    public void init_loop() {
        // Προαιρετικό: μπορείς εδώ να δείχνεις telemetry ενώ περιμένεις το PLAY
        telemetry.addData("Ball Found (preview)", pipeline.isBallFound());
    }

    @Override
    public void start() {
        telemetry.addLine("Ball tracking started");
    }

    @Override
    public void loop() {

        // --- Καλούμε απλά τις μεθόδους της pipeline ---
        boolean found = pipeline.isBallFound();
        int centerX = pipeline.getBallCenterX();
        int centerY = pipeline.getBallCenterY();
        double area = pipeline.getBallArea();

        telemetry.addData("Ball Found", found);
        telemetry.addData("Center X", centerX);
        telemetry.addData("Center Y", centerY);
        telemetry.addData("Area", area);

        if (found) {
            int error = centerX - FRAME_CENTER_X;

            if (Math.abs(error) < 20) {
                telemetry.addLine("Ball centered - go forward");
            } else if (error > 0) {
                telemetry.addLine("Ball is to the right - turn right");
            } else {
                telemetry.addLine("Ball is to the left - turn left");
            }
        }

        telemetry.update();
    }

    @Override
    public void stop() {
        webcam.stopStreaming();
    }
}
