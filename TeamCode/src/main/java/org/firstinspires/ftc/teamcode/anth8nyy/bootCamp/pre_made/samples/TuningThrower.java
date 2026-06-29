package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class TuningThrower{
    public DcMotorEx thrower;
    public double highVelocity = 1500;
    public double lowVelocity = 900;
    double curTargetVelocity = highVelocity;
    double F = 0;
    double P = 0;
    double[] stepSizes = {10.0,1.0,0.1,0.001,0.0001};
    int stepIndex = 1;

    public void init(HardwareMap hardwareMap){
        thrower = hardwareMap.get(DcMotorEx.class,"motor");
        thrower.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        thrower.setDirection(DcMotorSimple.Direction.REVERSE);

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P,0,0,F);
        thrower.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficients);
    }
    public void thrower(boolean y,boolean b, boolean dpadLeft, boolean dpadRight,boolean dpadUp,boolean dpadDown){
        if (y){
            if (curTargetVelocity == highVelocity){
                curTargetVelocity = lowVelocity;
            }
        }
        else {
            curTargetVelocity = highVelocity;
        }
        if (b){
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        if (dpadLeft){
            F += stepSizes[stepIndex];
        }
        if (dpadRight){
            F -= stepSizes[stepIndex];
        }
        if (dpadUp){
            P += stepSizes[stepIndex];
        }
        if (dpadDown){
            P -= stepSizes[stepIndex];
        }
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P,0,0,F);
        thrower.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,pidfCoefficients);
        thrower.setVelocity(curTargetVelocity);
        double curVelocity = thrower.getVelocity();
        double error = curTargetVelocity - curVelocity;
    }
}
