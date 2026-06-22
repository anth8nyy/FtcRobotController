package org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class DcMotorThrowerMechEx {
    private DcMotorEx rightMotor;
    private DcMotorEx leftMotor;
    private boolean on = false;

    public void init(HardwareMap hardwareMap) {
        rightMotor = hardwareMap.get(DcMotorEx.class, "rightThrower");
        rightMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT); // FLOAT γιατί είναι thrower
        rightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


        leftMotor = hardwareMap.get(DcMotorEx.class, "leftThrower");
        leftMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT); // FLOAT γιατί είναι thrower
        leftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }


    public void start() {
        if (!on) {
            on = true;
            rightMotor.setVelocity(1200.0);
            leftMotor.setVelocity(1200.0);
        }
    }

    public void stop() {
        if (on) {
            on = false;
            rightMotor.setVelocity(0.0);
            leftMotor.setVelocity(0.0);
        }
    }
}
