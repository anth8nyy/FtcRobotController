package org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Subsystems.XDrive;
import org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Utils.Config;
import org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Utils.DriveMath;

/**
 * Driver-controlled OpMode for the <b>field-relative</b> X-Drive.
 *     <li>Left stick Y  - drive away / toward the driver (field forward / back)</li>
 *     <li>Left stick X  - strafe (field left / right)</li>
 *     <li>Right stick X - rotate (turn)</li>
 *     <li>Options       - reset field heading to the robot's current facing</li>
 *     <li>Right bumper  - hold for the boosted speed multiplier</li>
 */
@TeleOp(name = "X-Drive TeleOp (Field-Centric)", group = "Main")
public class XDriveFieldCentricTeleOp extends OpMode {

    private final XDrive drive = new XDrive();

    @Override
    public void init() {
        drive.init(hardwareMap);

        telemetry.addLine("X-Drive (Field-Centric) ready. Press START.");
        telemetry.addLine("Point the robot away from you, then press OPTIONS to zero the heading.");
        telemetry.update();
    }

    @Override
    public void loop() {

        double speed = gamepad1.right_bumper ? Config.DriveValues.MAX_SPEED.value : Config.DriveValues.NORMAL_SPEED.value;

        double forward = DriveMath.applyDeadzone(-gamepad1.left_stick_y, Config.DriveValues.INPUT_DEADZONE.value) * speed;
        double right = DriveMath.applyDeadzone(gamepad1.left_stick_x, Config.DriveValues.INPUT_DEADZONE.value) * speed;
        double rotate = DriveMath.applyDeadzone(gamepad1.right_stick_x, Config.DriveValues.INPUT_DEADZONE.value) * speed;

        // Re-zero the field heading so the robot's current facing becomes "field forward".
        if (gamepad1.a) {
            drive.resetHeading();
        }

        drive.driveFieldRelative(forward, right, rotate);
        telemetry.addData("Mode", gamepad1.right_bumper ? "FAST" : "NORMAL");
        telemetry.addLine("OPTIONS = reset heading");
        telemetry.update();
    }
}
