package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.driveTrains;

import static org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.driveTrains.Config.ramp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Ramp {
    public DcMotorEx ramp;
    public void init(HardwareMap hardwareMap){
        ramp = hardwareMap.get(DcMotorEx.class, Config.ramp);
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