package org.firstinspires.ftc.teamcode.pre_made.samples;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class PovDriveSample {
    public DcMotor leftDrive   = null;
    public DcMotor  rightDrive  = null;

    public void init(HardwareMap hardwareMap){
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    public void tankDrive(double stickY,double stickX){
        double left;
        double right;
        double drive;
        double turn;
        double max;

        drive = stickY;
        turn  = stickX;

        left   = Range.clip(drive + turn, -0.7, 0.7) ;
        right   = Range.clip(drive - turn, -0.7, 0.7) ;


        // Normalize the values so neither exceed +/- 1.0
        max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0)
        {
            left /= max;
            right /= max;
        }
        leftDrive.setPower(left);
        rightDrive.setPower(right);
    }
}
