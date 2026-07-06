package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    DcMotor intakeMotor;
    public void init(HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotor.class, Config.intake);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void start(boolean leftBumper,boolean rightBumper){
        if(leftBumper) {
            intakeMotor.setPower(-1);
        }
        else if(rightBumper){
            intakeMotor.setPower(1);
        }
        else {
            intakeMotor.setPower(0.0);
        }
    }
    public double getPower(){
        return intakeMotor.getPower();
    }
}
