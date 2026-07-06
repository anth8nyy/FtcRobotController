package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class SmartServo180Ex {
    private Servo servo;




    public void init(HardwareMap hardwareMap) {
        servo = hardwareMap.get(Servo.class, "smart_servo");
        servo.scaleRange(0,1);
        servo.setPosition(1);
    }

    public void update(boolean a) {
        // edge detection — only trigger on the moment A is pressed
        if (a) {
            servo.setPosition(0); // go to 180°
        }else{
            servo.setPosition(1);
        }

    }
}
