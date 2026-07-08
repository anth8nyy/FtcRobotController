package org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

public class AprilTagWebCam {
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;
    private List<AprilTagDetection> detectedTags = new ArrayList<>();
    private Servo servo;
    private Telemetry telemetry;

    private static final double SERVO_TAG_CLOSE = 1.0;
    private static final double SERVO_TAG_FAR = 0.0;
    private static final double RANGE_THRESHOLD_CM = 10;

    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        servo = hardwareMap.get(Servo.class, Config.ServoForAprilTag.SERVO.hardwareName);
        servo.setDirection(Config.ServoForAprilTag.SERVO.direction);

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, Config.WEBCAM));
        builder.setCameraResolution(new Size(640, 480));
        builder.addProcessor(aprilTagProcessor);

        visionPortal = builder.build();
    }

    public void refreshDetections() {
        if (aprilTagProcessor == null) {
            return;
        }
        detectedTags = aprilTagProcessor.getDetections();
    }

    public void updateServoForTag(AprilTagDetection detectedId) {
        if (servo == null) {
            return;
        }
        if (detectedId == null || detectedId.ftcPose == null) {
            servo.setPosition(SERVO_TAG_FAR);
            return;
        }
        servo.setPosition(detectedId.ftcPose.range < RANGE_THRESHOLD_CM ? SERVO_TAG_CLOSE : SERVO_TAG_FAR);
    }

    public List<AprilTagDetection> getDetectedTags() {
        return detectedTags;
    }

    public void displayDetectionTelemetry(AprilTagDetection detectedId) {
        if (detectedId == null || telemetry == null) {
            return;
        }
        if (detectedId.metadata != null) {
            telemetry.addData("Detected Tags",getDetectedTags());
            telemetry.addLine(String.format("\n==== (ID %d) %s", detectedId.id, detectedId.metadata.name));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detectedId.ftcPose.x, detectedId.ftcPose.y, detectedId.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detectedId.ftcPose.pitch, detectedId.ftcPose.roll, detectedId.ftcPose.yaw));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detectedId.ftcPose.range, detectedId.ftcPose.bearing, detectedId.ftcPose.elevation));
        } else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", detectedId.id));
            telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detectedId.center.x, detectedId.center.y));
        }
    }

    public AprilTagDetection getTagBySpecificId(int id1,int id2) {
        for (AprilTagDetection detection : detectedTags) {
            if (detection.id == id1) {
                return detection;
            }else if(detection.id == id2) {
                return detection;
            }
        }
        return null;
    }
    public void stop() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}
