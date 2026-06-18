package org.firstinspires.ftc.teamcode.anth8nyy.practice;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.practice.mechanisms.TestBenchServo;

@Disabled
@TeleOp
public class ServoEx extends OpMode {
    TestBenchServo bench = new TestBenchServo();
    double leftTrigger, rightTrigger;
    @Override
    public void init() {
        bench.init(hardwareMap);
        leftTrigger = 0.0;
        rightTrigger = 0.0;
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            bench.setServoPo(-1.0);
        }
        else {
            bench.setServoPo(1.0);
        }
        if(gamepad1.b){
            bench.setServoRot(1.0);
        }
        else{
            bench.setServoRot(0);
        }
        leftTrigger = gamepad1.left_trigger;
        rightTrigger = gamepad1.right_trigger;

        bench.setServoPo(leftTrigger);
        bench.setServoRot(rightTrigger);

    }
}
