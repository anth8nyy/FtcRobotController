package org.firstinspires.ftc.teamcode.anth8nyy.pre_made;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples.driveTrains.PovDriveSample;
import org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples.ServoPosEx;

@Disabled
@TeleOp
public class Pov_and_servoPo extends OpMode {
    PovDriveSample drive = new PovDriveSample();
    ServoPosEx servo = new ServoPosEx();
    @Override
    public void init() {
        drive.init(hardwareMap);
        servo.init(hardwareMap);
    }

    @Override
    public void loop() {
        drive.tankDrive(gamepad1.left_stick_y,gamepad1.right_stick_x,0.7); // speed multiplier is the max speed I want my robot to have
        if(gamepad1.a){
            servo.setServoPo(1.0);
        }
        else {
            servo.setServoPo(0.5);
        }


    }
}
