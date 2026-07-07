package org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.Config;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.GamepadEx;

public class Shooter {
    private DcMotorEx left_shooter;
    private DcMotorEx right_shooter;

    private double targetVelocity = 0;
    private boolean shooterOff = true;

    public void init(HardwareMap hardwareMap) {
        left_shooter = hardwareMap.get(DcMotorEx.class, Config.Motors.LEFT_SHOOTER_MOTOR.hardwareName);
        right_shooter = hardwareMap.get(DcMotorEx.class, Config.Motors.RIGHT_SHOOTER_MOTOR.hardwareName);

        left_shooter.setDirection(Config.Motors.LEFT_SHOOTER_MOTOR.direction);
        right_shooter.setDirection(Config.Motors.RIGHT_SHOOTER_MOTOR.direction);

        left_shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right_shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void gamepadUpdate(GamepadEx gamepad) {
        if (gamepad.justPressed(GamepadEx.Button.B)) {
            shooterOff = !shooterOff;
        }

        targetVelocity = !shooterOff ? Config.ShooterValues.SHOOTING_VELOCITY.value : 0; // IF - ELSE
        update();
    }

    public void setTargetVelocity(double velocity) {
        targetVelocity = velocity;
    }

    public void update() {
        double power;
        if (shooterOff) {
            power = 0;
        } else {
            double kV = Config.ShooterValues.kV.value;
            double kS = Config.ShooterValues.kS.value;
            double kP = Config.ShooterValues.kP.value;

            double feedForward = (kV * targetVelocity) + kS;
            double feedBack = (targetVelocity - getVelocity()) * kP;
            power = feedForward + feedBack;
        }
        left_shooter.setPower(power);
        right_shooter.setPower(power);
    }

    public double getVelocity() {
        return (left_shooter.getVelocity() + right_shooter.getVelocity()) / 2.0;
    }
    public double getTargetVelocity() {
        return targetVelocity;
    }
    public boolean isReady() {
        return Math.abs(targetVelocity - getVelocity()) < Config.ShooterValues.RPM_ERROR.value;
    }
    public void stop() {
        shooterOff = true;
        targetVelocity = 0;
    }
}