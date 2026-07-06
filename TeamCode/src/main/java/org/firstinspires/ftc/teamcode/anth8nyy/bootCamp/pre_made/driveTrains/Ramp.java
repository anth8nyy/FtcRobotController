package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.driveTrains;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies.Config;

public class Ramp {
    public DcMotorEx ramp;
    public void init(HardwareMap hardwareMap){
        ramp = hardwareMap.get(DcMotorEx.class, Config.shooter);
        ramp.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
    }
    public void up_down(boolean y,boolean a){
        if(y) {
            ramp.setPower(0.7);

        }
        else if(a){
            ramp.setPower(-0.7);
        }
        else{
            ramp.setPower(0);
        }
    }
    public void down(boolean a){
        ramp.setPower(-1.0);
    }
}