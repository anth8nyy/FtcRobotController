package org.firstinspires.ftc.teamcode.anth8nyy.pre_made.driveTrains;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples.driveTrains.RobotTeleopMecanumFieldRelativeDrive;
@Disabled
@TeleOp
public class MecanumDriveOp extends OpMode {
    RobotTeleopMecanumFieldRelativeDrive drive = new RobotTeleopMecanumFieldRelativeDrive();
    @Override
    public void init() {
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        drive.mecanumDrive(gamepad1.a, gamepad1.left_bumper,gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x );

    }
}
