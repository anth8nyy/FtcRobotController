package org.firstinspires.ftc.teamcode.pre_made.samples;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoRotEx {
    CRServo servoRot;
    public void init(HardwareMap hardwareMap){
        servoRot = hardwareMap.get(CRServo.class,"Servo_Rot");

        servoRot.setDirection(CRServo.Direction.REVERSE);
    }
    public void setServoRot(double power){
        servoRot.setPower(power);
    }
}
