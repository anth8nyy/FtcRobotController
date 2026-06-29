package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class AdvancedThrower {
    private DcMotorEx rightMotor;
    public boolean on = false;
    private double startVeloc = 200; // starting velocity in ticks/s

    public void init(HardwareMap hardwareMap) {
        rightMotor = hardwareMap.get(DcMotorEx.class, "rightThrower");
        rightMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT); // FLOAT γιατί είναι thrower
        rightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void start() {
        if (!on) {
            on = true;

            // ramp up in a background thread so the main OpMode doesn't freeze
            new Thread(() -> {
                try {
                    // keep increasing velocity until we reach 1200 ticks/s
                    while (startVeloc <= 1200) {
                        rightMotor.setVelocity(startVeloc); // apply current velocity
                        startVeloc += 200;                  // step up by 200 ticks/s
                        Thread.sleep(20);                   // wait 20ms before next step
                    }
                    rightMotor.setVelocity(1200);           // make sure we land exactly at 1200
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();     // restore interrupt flag if stopped early
                }
            }).start();
        }
    }

    public void stop() {
        if (on) {
            on = false;
            startVeloc = 200;               // reset so next start() ramps from the beginning
            rightMotor.setVelocity(0.0);    // cut motor power
        }
    }
}
