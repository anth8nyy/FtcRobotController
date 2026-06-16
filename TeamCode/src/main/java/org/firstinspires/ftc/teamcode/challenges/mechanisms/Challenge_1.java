package org.firstinspires.ftc.teamcode.challenges.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Challenge_1 {
    DcMotor frontLeftMotor, frontRightMotor;


    public void init(HardwareMap hwMap) {
        frontLeftMotor = hwMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hwMap.get(DcMotor.class, "frontRightMotor");


        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void speedMultiplier(double stickY, double stickX, double rightTrigger) {
        double speedMultiplier;
        if (rightTrigger > 0.4 && rightTrigger < 0.7) {
            speedMultiplier = 1.0;
        } else if (rightTrigger > 0.7) {
            speedMultiplier = 0.7;
        } else {
            speedMultiplier = 0.4;
        }
        double drive = -stickY;
        double turn = stickX;

        double leftPower = drive + turn;
        double rightPower = drive - turn;
        double maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        if (maxPower > 1.0) {
            leftPower /= maxPower;
            rightPower /= maxPower;
        }
        leftPower *= speedMultiplier;
        rightPower *= speedMultiplier;
        frontLeftMotor.setPower(leftPower);
        frontRightMotor.setPower(rightPower);

    }
}
