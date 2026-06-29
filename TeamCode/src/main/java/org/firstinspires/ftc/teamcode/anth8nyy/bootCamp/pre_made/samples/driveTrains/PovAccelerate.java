package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.driveTrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PovAccelerate {
    public DcMotor leftDrive  = null;
    public DcMotor rightDrive = null;
    private static final double DEFAULT_SPEED = 0.6;
    private static final double MAX_SPEED     = 1.0;

    // how fast the motor ramps toward the target power (per loop call)
    // lower = slower acceleration, higher = snappier
    private static final double RAMP_SPEED = 0.05;

    // current actual power being sent to the motors
    private double currentLeft  = 0.0;
    private double currentRight = 0.0;

    public void init(HardwareMap hardwareMap) {
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    public void tankDrive(double stickY, double stickX, double speedMultiplier) {
        // apply squared curve for fine control at low stick values
        stickY = applySquaredCurve(stickY);
        stickX = applySquaredCurve(stickX);

        // map trigger to speed range
        double maxPower = DEFAULT_SPEED + speedMultiplier * (MAX_SPEED - DEFAULT_SPEED);

        // arcade mix
        double targetLeft  = stickY + stickX;
        double targetRight = stickY - stickX;

        // normalize so neither side exceeds 1.0
        double max = Math.max(Math.abs(targetLeft), Math.abs(targetRight));
        if (max > 1.0) {
            targetLeft  /= max;
            targetRight /= max;
        }

        // apply speed limit
        targetLeft  *= maxPower;
        targetRight *= maxPower;

        // ramp current power toward target — this is what creates the acceleration effect
        // instead of jumping to targetLeft instantly, we step toward it by RAMP_SPEED each loop
        currentLeft  += Math.signum(targetLeft  - currentLeft)  * Math.min(RAMP_SPEED, Math.abs(targetLeft  - currentLeft));
        currentRight += Math.signum(targetRight - currentRight) * Math.min(RAMP_SPEED, Math.abs(targetRight - currentRight));

        leftDrive.setPower(currentLeft);
        rightDrive.setPower(currentRight);
    }

    // squares input for non-linear curve, preserves sign for reverse
    private double applySquaredCurve(double input) {
        return Math.signum(input) * input * input;
    }
}