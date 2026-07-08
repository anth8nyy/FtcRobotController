package org.firstinspires.ftc.teamcode.anth8nyy.GNRT;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Subsystems.Brace_Climber;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.AprilTagWebCam;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.Config;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.GamepadEx;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.Rumble;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
@Disabled
@TeleOp(name = "GNRT")
public class GNRT extends OpMode {

    private final Drive drive = new Drive();
    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final Brace_Climber braceClimber = new Brace_Climber();
    private final Rumble rumble = new Rumble();
    private final AprilTagWebCam aprilTagWebCam = new AprilTagWebCam();
    private final GamepadEx driver = new GamepadEx();
    private final GamepadEx operator = new GamepadEx();

    @Override
    public void init() {
        drive.init(hardwareMap);
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        braceClimber.init(hardwareMap);
        aprilTagWebCam.init(hardwareMap, telemetry);
        driver.init(gamepad1);
        operator.init(gamepad2);
    }

    // Runs repeatedly while sitting in INIT, before START is pressed - this is the window
    // where the driver can actually select an alliance with the dpad.
    @Override
    public void init_loop() {
        driver.update();

        if (driver.justPressed(GamepadEx.Button.DPAD_LEFT)) {
            Config.CURRENT_ALLIANCE = Config.Alliance.RED;
        } else if (driver.justPressed(GamepadEx.Button.DPAD_RIGHT)) {
            Config.CURRENT_ALLIANCE = Config.Alliance.BLUE;
        }

        telemetry.addLine("DPAD LEFT = RED, DPAD RIGHT = BLUE");
        telemetry.addData("Current Alliance", Config.CURRENT_ALLIANCE);
        telemetry.update();
    }

    @Override
    public void start() {
        rumble.start();
    }

    @Override
    public void loop() {
        driver.update();

        drive.povDrive(driver);
        intake.start(driver);
        shooter.gamepadUpdate(operator);
        braceClimber.start(operator);

        aprilTagWebCam.refreshDetections();
        AprilTagDetection trackedTag = Config.CURRENT_ALLIANCE == Config.Alliance.RED
                ? aprilTagWebCam.getTagBySpecificId(Config.RED_SUPPRESSION_FRONT_TAG_ID, Config.RED_SUPPRESSION_SIDE_TAG_ID)
                : aprilTagWebCam.getTagBySpecificId(Config.BLUE_SUPPRESSION_FRONT_TAG_ID, Config.BLUE_SUPPRESSION_SIDE_TAG_ID);
        aprilTagWebCam.updateServoForTag(trackedTag);
        aprilTagWebCam.displayDetectionTelemetry(trackedTag);
        telemetry.update();

        rumble.update(driver);
        rumble.update(operator);
    }

    @Override
    public void stop() {
        // Releases the camera so the next OpMode can open it. This was never called
        // before, which can leave the VisionPortal locked between opmode runs.
        aprilTagWebCam.stop();
    }
}