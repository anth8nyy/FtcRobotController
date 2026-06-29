package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.driveTrains;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.driveTrains.PovDriveSample;

@Disabled
@TeleOp
public class PovOp extends OpMode {
    PovDriveSample drive = new PovDriveSample();
    @Override
    public void init() {
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        drive.tankDrive(gamepad1.left_stick_y, gamepad1.right_stick_x,gamepad1.right_trigger);  // speed multiplier is the max speed I want my robot to have
    }
}
