package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.driveTrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class PovDriveSample {
    public DcMotor leftDrive   = null;
    public DcMotor  rightDrive  = null;
    private static final double DEFAULT_SPEED = 0.6;
    private static final double MAX_SPEED     = 1.0;


    public void init(HardwareMap hardwareMap){
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    public void tankDrive(double stickY,double stickX ,double speedMultiplier){
        double maxPower = DEFAULT_SPEED + speedMultiplier * (MAX_SPEED - DEFAULT_SPEED);

        // Arcade mix — unchanged from original.
        double left  = stickY + stickX;
        double right = stickY - stickX;

        // Normalization — unchanged from original.
        double max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0) {
            left  /= max;
            right /= max;
        }

        // CHANGED: Old code multiplied by speedMultiplier here.
        //   Now we multiply by maxPower (the mapped value from above).
        left  *= maxPower;
        right *= maxPower;

        leftDrive.setPower(left);
        rightDrive.setPower(right);
    }
}
