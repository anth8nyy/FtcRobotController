// MecanumDriveOp.java
package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.driveTrains;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.driveTrains.RobotTeleopMecanumFieldRelativeDrive;

@TeleOp(name = "Mecanum Field Relative", group = "bootCamp")
public class MecanumDriveOp extends OpMode {
    RobotTeleopMecanumFieldRelativeDrive drive = new RobotTeleopMecanumFieldRelativeDrive();

    @Override
    public void init() {
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        drive.driveFieldRelative(
                    gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x
        );
        telemetry.addData("Yaw angle",drive.getHeadingDegrees());
    }
}
