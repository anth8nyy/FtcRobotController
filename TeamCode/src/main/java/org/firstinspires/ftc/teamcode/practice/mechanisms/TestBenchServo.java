package org.firstinspires.ftc.teamcode.practice.mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TestBenchServo {
    private Servo servoPo;
    private CRServo servoRot;

    public void init(HardwareMap hwMap){
        servoPo = hwMap.get(Servo.class,"servo_Pos");
        servoRot = hwMap.get(CRServo.class,"Servo_Rot");
        servoPo.scaleRange(0.5,1.0); //set range from midpoint to 180d
        servoRot.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void setServoPo(double angle){
        servoPo.setPosition(angle);
    }
    public void setServoRot(double power){
        servoRot.setPower(power);
    }
}
