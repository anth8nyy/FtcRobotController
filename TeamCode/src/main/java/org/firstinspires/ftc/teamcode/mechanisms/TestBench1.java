package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TestBench1 {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private double rightTick;
    private double leftTick;

    public void init(HardwareMap hwMap){
        //initializing left motor
        leftMotor = hwMap.get(DcMotor.class,"leftMotor");
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //initializing right motor
        rightMotor = hwMap.get(DcMotor.class,"rightMotor");
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setLeftMotorSpeed(double leftMotorSpeed){
        leftMotor.setPower(leftMotorSpeed);
    }

    public void setRightMotorSpeed(double rightMotorSpeed){
        rightMotor.setPower(rightMotorSpeed);
    }

    public double getRightTick(){
        return rightMotor.getCurrentPosition()/rightTick;
    }

    public double getLeftTick(){
        return  leftMotor.getCurrentPosition()/leftTick;
    }
}
