package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.SmartServoEx;
import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.driveTrains.PovDriveSample;

@Disabled
@TeleOp
public class Pov_and_smart_servo extends OpMode {
    SmartServoEx servo = new SmartServoEx();
    PovDriveSample drive = new PovDriveSample();

    @Override
    public void init() {
        servo.init(hardwareMap);
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        drive.tankDrive(gamepad1.left_stick_y, gamepad1.right_stick_x, 0.7); // speed multiplier is the max speed I want my robot to have
        if (gamepad1.a) {
            servo.setServoRot(1.0);
        } else {
            servo.setServoRot(0.0);
        }
    }
}