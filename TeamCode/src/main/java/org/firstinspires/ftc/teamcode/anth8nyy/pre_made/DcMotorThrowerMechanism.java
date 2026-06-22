package org.firstinspires.ftc.teamcode.anth8nyy.pre_made;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples.DcMotorThrowerMechEx;

@Disabled
@TeleOp
public class DcMotorThrowerMechanism extends OpMode {
    DcMotorThrowerMechEx thrower = new DcMotorThrowerMechEx();
    @Override
    public void init() {
        thrower.init(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            thrower.start();
        }
        else{
            thrower.stop();
        }
    }
}
