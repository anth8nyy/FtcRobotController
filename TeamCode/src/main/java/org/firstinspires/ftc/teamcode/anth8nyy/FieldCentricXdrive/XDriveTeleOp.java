package org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Subsystems.XDrive;
import org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Utils.Config;
import org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Utils.DriveMath;

/**
 * Driver-controlled OpMode for the robot-relative X-Drive.
 *
 * <h2>Controls (gamepad 1)</h2>
 * <ul>
 *     <li>Left stick Y  - drive forward / backward</li>
 *     <li>Left stick X  - strafe left / right</li>
 *     <li>Right stick X - rotate (turn)</li>
 *     <li>Right bumper  - hold for the boosted speed multiplier</li>
 * </ul>
 */
@TeleOp(name = "X-Drive TeleOp (Robot-Centric)", group = "Main")
public class XDriveTeleOp extends OpMode {

    private final XDrive drive = new XDrive();

    @Override
    public void init() {
        drive.init(hardwareMap);

        telemetry.addLine("X-Drive ready. Press START.");
        telemetry.update();
    }

    @Override
    public void loop() {

        double speed = gamepad1.right_bumper ? Config.DriveValues.MAX_SPEED.value : Config.DriveValues.NORMAL_SPEED.value;

        double forward = DriveMath.applyDeadzone(-gamepad1.left_stick_y, Config.DriveValues.INPUT_DEADZONE.value) * speed;
        double right = DriveMath.applyDeadzone(gamepad1.left_stick_x, Config.DriveValues.INPUT_DEADZONE.value) * speed;
        double rotate = DriveMath.applyDeadzone(gamepad1.right_stick_x, Config.DriveValues.INPUT_DEADZONE.value) * speed;

        drive.drive(forward, right, rotate);

        telemetry.addData("Mode", gamepad1.right_bumper ? "FAST" : "NORMAL");
        telemetry.update();
    }
}
