package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.IMU;

public class PovDriveFieldOr {
    public DcMotor leftDrive  = null;
    public DcMotor rightDrive = null;
    private IMU imu = null;

    private static final double DEFAULT_SPEED = 0.6; // minimum power when trigger is not pressed
    private static final double MAX_SPEED     = 1.0; // maximum power when trigger is fully pressed

    public void init(HardwareMap hardwareMap) {
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        // left motor is physically mounted in reverse, so we flip its direction in code
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        // use encoders for more accurate, consistent motor power
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hardwareMap.get(IMU.class, "imu");

        // This needs to be changed to match the orientation on your robot
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection =
                RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection =
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

        RevHubOrientationOnRobot orientationOnRobot = new
                RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
    }

    /**
     * Field-relative tank drive.
     * forward / right come from the joystick representing field directions.
     * rotate comes from the other stick's X axis for turning.
     */
    public void driveFieldRelative(double forward, double right, double rotate, double speedMultiplier) {
        // get the robot's current heading from the IMU, in radians
        double heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // project the field-relative stick vector onto the robot's forward axis
        // tank drive has no strafe, so only the forward component after rotation matters
        double robotForward = forward * Math.cos(-heading) - right * Math.sin(-heading);

        // clamp in case the projection pushes slightly past the -1.0 to 1.0 range
        robotForward = Math.max(-1.0, Math.min(1.0, robotForward));

        // pass the rotated forward value and rotation into the tank drive mixer
        tankDrive(robotForward, rotate, speedMultiplier);
    }

    private void tankDrive(double stickY, double stickX, double speedMultiplier) {
        // map the trigger (0.0 to 1.0) to a power range between DEFAULT_SPEED and MAX_SPEED
        double maxPower = DEFAULT_SPEED + speedMultiplier * (MAX_SPEED - DEFAULT_SPEED);

        // arcade mix: stickY drives forward/back, stickX drives turning
        double left  = stickY + stickX;
        double right = stickY - stickX;

        // normalize: if either side would exceed 1.0, scale both down proportionally
        double max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0) {
            left  /= max;
            right /= max;
        }

        // apply the speed limit from the trigger
        left  *= maxPower;
        right *= maxPower;

        // send final power values to the motors
        leftDrive.setPower(left);
        rightDrive.setPower(right);
    }

}