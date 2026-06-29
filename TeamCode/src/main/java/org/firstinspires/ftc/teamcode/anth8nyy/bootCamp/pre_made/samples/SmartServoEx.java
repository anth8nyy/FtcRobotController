package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SmartServoEx {
    CRServo servoRot;
    public void init(HardwareMap hardwareMap){
        servoRot = hardwareMap.get(CRServo.class,"Servo_Rot");

        servoRot.setDirection(CRServo.Direction.REVERSE);
    }
    public void setServoRot(double power){
        servoRot.setPower(power);
    }
}
