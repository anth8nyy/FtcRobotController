package org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SmartServo180Ex {
    private Servo servo;
    private double currentPosition = 0.0;
    private boolean goingForward = false;
    private boolean wasPressed = false;
    private static final double SPEED = 0.004; //I can change the speed of the servo

    public void init(HardwareMap hardwareMap) {
        servo = hardwareMap.get(Servo.class, "smart_servo");
        servo.scaleRange(0.0, 1.0);
        servo.setPosition(currentPosition);
    }

    public void update(boolean aPressed) {
        // detect single press, not hold
        if (aPressed && !wasPressed) {
            goingForward = true;
        }
        wasPressed = aPressed;

        if (goingForward) {
            currentPosition += SPEED; // move toward 180°
            if (currentPosition >= 1.0) {
                currentPosition = 1.0;
                goingForward = false; // reached 180°, now go back
            }
        } else {
            currentPosition -= SPEED; // move back toward -180°
            currentPosition = Math.max(0.0, currentPosition);
        }

        servo.setPosition(currentPosition);
    }
}
