package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SmartServo180Ex {
    private Servo servo;
    private boolean wasPressed = false;

    public void init(HardwareMap hardwareMap) {
        servo = hardwareMap.get(Servo.class, "smart_servo");
        servo.scaleRange(0.0, 1.0);
        servo.setPosition(0.0);
    }
    public void update(boolean aPressed) {
        if (aPressed && !wasPressed) {
            servo.setPosition(1.0); // πάει 180°
            servo.setPosition(0.0); // γυρνά 0°
        }
        wasPressed = aPressed;
    }
}
