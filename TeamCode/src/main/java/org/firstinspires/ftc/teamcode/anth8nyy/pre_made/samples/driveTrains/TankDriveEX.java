package org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples.driveTrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TankDriveEX {
    /* Declare OpMode members. */
    public DcMotor leftDrive   = null;
    public DcMotor  rightDrive  = null;


    public void init(HardwareMap hardwareMap) {
        // Define and Initialize Motors
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left and right sticks forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
        // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void tankDrive(double left_stick_y, double right_stick_y) {
        double left;
        double right;

        // Run wheels in tank mode (note: The joystick goes negative when pushed forward, so negate it)
        left = -left_stick_y;
        right = -right_stick_y;

        leftDrive.setPower(left);
        rightDrive.setPower(right);
    }
}
