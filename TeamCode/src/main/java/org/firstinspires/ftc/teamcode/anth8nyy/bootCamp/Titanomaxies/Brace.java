package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Brace {
    DcMotor brace;
    public void init(HardwareMap hardwareMap){
        brace = hardwareMap.get(DcMotor.class,"Config.brace");
        brace.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void start(boolean a,boolean y){
        if (a){
            brace.setPower(1.0);
        }
        else if(y){
            brace.setPower(-1.0);
        }
        else{
            brace.setPower(0.0);
        }
    }
    public double getPower(){
        return brace.getPower();
    }
}
