package org.firstinspires.ftc.teamcode.anth8nyy.GNRT;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {
    private DcMotor leftDrive, rightDrive;

    public Drive(HardwareMap hardwareMap) {
        // map hardware names to your robot config
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");

        // reverse one side so positive power drives forward on both sides
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    // sets raw power to each side independently (tank drive)
    public void tankDrive(double leftPower, double rightPower) {
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }

    // convenience overload with a speed scalar (e.g. for slow mode)
    public void tankDrive(double leftPower, double rightPower, double speedScale) {
        leftDrive.setPower(leftPower * speedScale);
        rightDrive.setPower(rightPower * speedScale);
    }

    // reads sticks/bumper straight from the gamepad and drives accordingly
    public void drive(GamepadEx gamepad) {
        double leftPower = gamepad.getLeftStickY();   // left side power
        double rightPower = gamepad.getRightStickY(); // right side power
        double speedScale = gamepad.isDown(GamepadEx.Button.RIGHT_BUMPER) ? 1.0 : 0.6;

        tankDrive(leftPower, rightPower, speedScale);
    }
    public double getLeftPower() {
        return leftDrive.getPower();
    }

    public double getRightPower() {
        return rightDrive.getPower();
    }
}