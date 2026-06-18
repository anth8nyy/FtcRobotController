package org.firstinspires.ftc.teamcode.anth8nyy.pre_made;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples.ServoRotEx;

@Disabled
@TeleOp
public class Pov_and_servoRot extends OpMode {
    ServoRotEx servo = new ServoRotEx();

    @Override
    public void init() {
        servo.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            servo.setServoRot(1.0);
        } else {
            servo.setServoRot(0.0);
        }
    }
}