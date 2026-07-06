package org.firstinspires.ftc.teamcode.anth8nyy.GNRT;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Brace_Climber {
    private DcMotorEx brace;
    public void init(HardwareMap hardwareMap){
        brace = hardwareMap.get(DcMotorEx.class,Config.Motors.BRACE_MOTOR.hardwareName);
        brace.setDirection(Config.Motors.BRACE_MOTOR.direction);
    }
}
