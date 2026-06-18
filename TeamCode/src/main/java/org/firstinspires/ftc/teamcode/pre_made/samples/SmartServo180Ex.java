package org.firstinspires.ftc.teamcode.pre_made.samples;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SmartServo180Ex {
    private Servo servo;
    private double currentPosition = 1.0; // start at 180°
    private static final double SPEED = 0.004; // tune this for faster/slower

    public void init(HardwareMap hardwareMap) {
        servo = hardwareMap.get(Servo.class, "smart_servo");
        servo.scaleRange(0.0, 1.0);
        servo.setPosition(currentPosition);
    }
    public void setAngle(double degrees) {
        // convert -90 to 90 range into 0.0 to 1.0
        double position = (degrees + 180) / 360.0; // double position = (degrees + x) / 2 * x
        servo.setPosition(position);
    }

    // call this every loop() with gamepad1.a
    public void update(boolean aPressed) {
        if (aPressed) {
            currentPosition -= SPEED; // move toward -180°
        } else {
            currentPosition += SPEED; // move back toward 180°
        }

        // clamp between 0.0 and 1.0
        currentPosition = Math.max(0.0, Math.min(1.0, currentPosition));
        servo.setPosition(currentPosition);
    }
}

