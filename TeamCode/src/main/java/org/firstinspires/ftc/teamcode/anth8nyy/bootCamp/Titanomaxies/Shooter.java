package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {

    private static final double TICKS_PER_REV = 28; // your motor's actual value
    public static DcMotorEx shooterMotor;

    private boolean isRunning = false;
    private boolean lastButtonState = false;

    public void init(HardwareMap hardwareMap) {
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooter");
        shooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void update(boolean a) {
        if (a && !lastButtonState) {
            isRunning = !isRunning;
        }
        lastButtonState = a;

        if (isRunning) {
            shooterMotor.setVelocity(100000);
        } else {
            shooterMotor.setVelocity(0);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
    public double getPower(){
        return shooterMotor.getVelocity();
    }
}