package org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.Config;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.GamepadEx;

public class Brace_Climber {
    private DcMotorEx brace;
    public void init(HardwareMap hardwareMap){
        brace = hardwareMap.get(DcMotorEx.class, Config.Motors.BRACE_MOTOR.hardwareName);
        brace.setDirection(Config.Motors.BRACE_MOTOR.direction);
    }
    public void start(GamepadEx gamepad){
        if(gamepad.isDown(GamepadEx.Button.Y)){
            brace.setPower(1.0);
        }
        else{
            brace.setPower(0.0);
        }
    }
}
