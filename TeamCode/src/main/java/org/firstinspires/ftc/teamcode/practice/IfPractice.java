package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class IfPractice extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        double motorspeed = gamepad1.left_stick_y;
            if (!gamepad1.a){
                motorspeed *= 0.5;
            }
            telemetry.addData("the speed is", motorspeed);
    }

}
