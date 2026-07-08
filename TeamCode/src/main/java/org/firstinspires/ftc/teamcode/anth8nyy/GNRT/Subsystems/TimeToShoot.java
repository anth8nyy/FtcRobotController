package org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.Config;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.GamepadEx;

public class TimeToShoot {
    private Servo servoLeft;
    private Servo servoRight;

    private boolean shooting = false; // tracks current toggle state

    public void init(HardwareMap hardwareMap){
        servoLeft = hardwareMap.get(Servo.class, Config.ServoForShooting.SERVO_LEFT.hardwareName);
        servoRight = hardwareMap.get(Servo.class, Config.ServoForShooting.SERVO_RIGHT.hardwareName);

        servoLeft.setDirection(Config.ServoForShooting.SERVO_LEFT.direction);
        servoRight.setDirection(Config.ServoForShooting.SERVO_RIGHT.direction);
    }

    public void start(GamepadEx gamepad){
        if (gamepad.justPressed(GamepadEx.Button.B)) {
            shooting = !shooting; // flip the state on each press

            double pos = shooting ? 1.0 : 0.0;
            servoLeft.setPosition(pos);
            servoRight.setPosition(pos);
        }
    }
}