package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Servo180Ex {
    private Servo servoPo;

    public void init(HardwareMap hwMap){
        servoPo = hwMap.get(Servo.class,"servo_Pos");
        servoPo.scaleRange(0.5,1.0); //set range from midpoint to 180d
    }
    public void setServoPo(double angle){
        servoPo.setPosition(angle);

    }

}
