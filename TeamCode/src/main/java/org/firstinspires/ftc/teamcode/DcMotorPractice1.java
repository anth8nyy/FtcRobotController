package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.TestBench1;

@TeleOp
public class DcMotorPractice1 extends OpMode {
    TestBench1 bench1 = new TestBench1();
    @Override
    public void init() {
        bench1.init(hardwareMap);
    }

    @Override
    public void loop() {
        double rightMotorSpeed = gamepad1.right_stick_y;
        double leftMotorSpeed = gamepad1.left_stick_y;

        bench1.setLeftMotorSpeed(leftMotorSpeed);
        bench1.setRightMotorSpeed(rightMotorSpeed);
        telemetry.addData("Right Motor Rotations",bench1.getRightTick());
        telemetry.addData("Left Motor Rotations",bench1.getLeftTick());
    }
}
