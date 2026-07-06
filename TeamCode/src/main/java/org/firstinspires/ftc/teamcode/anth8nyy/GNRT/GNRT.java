package org.firstinspires.ftc.teamcode.anth8nyy.GNRT;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Disabled
@TeleOp(name = "GNRT")
public class GNRT extends OpMode {

    private Drive drive;
    private Intake intake;
    private Shooter shooter;
    private Brace_Climber braceClimber;
    private Rumble rumble;
    private AprilTagWebCam aprilTagWebCam;
    private GamepadEx gamepad;

    @Override
    public void init() {
        drive.init(hardwareMap);
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        braceClimber.init(hardwareMap);
        aprilTagWebCam.init(hardwareMap,telemetry);
        gamepad.init(gamepad1);
    }

    @Override
    public void start() {
        rumble.start();
    }

    @Override
    public void loop() {
        gamepad.update();
        drive.povDrive(gamepad);
        shooter.gamepadUpdate(gamepad);
        aprilTagWebCam.refreshDetections();
        AprilTagDetection id20 = aprilTagWebCam.getTagBySpecificId(20);
        aprilTagWebCam.updateServoForTag(id20);
        rumble.update(gamepad);
    }
}