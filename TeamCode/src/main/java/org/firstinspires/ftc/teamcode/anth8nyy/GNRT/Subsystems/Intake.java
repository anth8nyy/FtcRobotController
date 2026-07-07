package org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.Config;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.GamepadEx;

public class Intake {
    private DcMotor intake;
    private static final double INTAKE_POWER_ON = 1.0;
    private static final double INTAKE_POWER_REVERSE = -1.0;

    public void init(HardwareMap hardwareMap){
        intake = hardwareMap.get(DcMotor.class, Config.Motors.INTAKE_MOTOR.hardwareName);
        intake.setDirection(Config.Motors.INTAKE_MOTOR.direction);

    }
    public void start(GamepadEx gamepad){
        intake.setPower(INTAKE_POWER_ON);
        if (gamepad.isDown(GamepadEx.Button.LEFT_BUMPER)){
            intake.setPower(INTAKE_POWER_REVERSE);
        }
    }

}
