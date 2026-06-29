package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.driveTrains.PovDriveSample;
import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.ThrowerMechanism;

@Disabled
@TeleOp
public class PovAndThrow extends OpMode {
    PovDriveSample drive = new PovDriveSample();
    ThrowerMechanism thrower = new ThrowerMechanism();
    @Override
    public void init() {
        drive.init(hardwareMap);
        thrower.init(hardwareMap);
    }

    @Override
    public void loop() {
        drive.tankDrive(gamepad1.left_stick_y,gamepad1.right_stick_x,0.7); // speed multiplier is the max speed I want my robot to have
        if(gamepad1.a){
            thrower.shoot();
        }
        else {
            thrower.stop();
        }
    }
}
