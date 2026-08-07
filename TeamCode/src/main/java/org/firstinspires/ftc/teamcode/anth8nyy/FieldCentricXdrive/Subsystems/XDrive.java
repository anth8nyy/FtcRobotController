package org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Utils.Config;
import org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Utils.DriveMath;

public class XDrive {

    private DcMotor frontLeftDrive;
    private DcMotor frontRightDrive;
    private DcMotor backLeftDrive;
    private DcMotor backRightDrive;
    private IMU imu;

    // true = field-relative driving, false = robot-relative. Toggled with the A button.
    private boolean fieldCentric = true;
    // Previous A state, so we only toggle once per press (rising edge) instead of every loop.
    private boolean previousA = false;

    public void init(HardwareMap hardwareMap) {
        frontLeftDrive = hardwareMap.get(DcMotor.class, Config.Motors.FRONT_LEFT.hardwareName);
        frontRightDrive = hardwareMap.get(DcMotor.class, Config.Motors.FRONT_RIGHT.hardwareName);
        backLeftDrive = hardwareMap.get(DcMotor.class, Config.Motors.BACK_LEFT.hardwareName);
        backRightDrive = hardwareMap.get(DcMotor.class, Config.Motors.BACK_RIGHT.hardwareName);

        frontLeftDrive.setDirection(Config.Motors.FRONT_LEFT.direction);
        frontRightDrive.setDirection(Config.Motors.FRONT_RIGHT.direction);
        backLeftDrive.setDirection(Config.Motors.BACK_LEFT.direction);
        backRightDrive.setDirection(Config.Motors.BACK_RIGHT.direction);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hardwareMap.get(IMU.class, Config.IMU);

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT
                )
        );
        imu.initialize(parameters);
        imu.resetYaw();
    }

    // Call this once per loop from the OpMode.
    //   A             - toggle between robot-centric and field-centric
    //   Options       - reset the field heading to the robot's current facing
    //   Right bumper  - hold for MAX_SPEED (otherwise NORMAL_SPEED)
    //   Left stick Y  - drive
    //   Left stick X  - strafe
    //   Right stick X - rotate

    public void drive(Gamepad gamepad, Telemetry telemetry) {
        // Toggle mode only on a fresh A press (A down now, up last loop).
        if (gamepad.a && !previousA) {
            fieldCentric = !fieldCentric;
        }
        previousA = gamepad.a;

        // Options re-zeroes the field heading.
        if (gamepad.options) {
            resetHeading();
        }

        double speed = gamepad.right_bumper ? Config.DriveValues.MAX_SPEED.value : Config.DriveValues.NORMAL_SPEED.value;

        // Stick Y is negative when pushed up, so negate it to make "up" = forward.
        double forward = DriveMath.applyDeadzone(-gamepad.left_stick_y, Config.DriveValues.INPUT_DEADZONE.value) * speed;
        double strafe = DriveMath.applyDeadzone(gamepad.left_stick_x, Config.DriveValues.INPUT_DEADZONE.value) * speed;
        double rotate = DriveMath.applyDeadzone(gamepad.right_stick_x, Config.DriveValues.INPUT_DEADZONE.value) * speed;

        if (fieldCentric) {
            driveFieldRelative(forward, strafe, rotate);
        } else {
            driveRobotCentric(forward, strafe, rotate);
        }

        telemetry.addData("Mode", fieldCentric ? "FIELD-CENTRIC" : "ROBOT-CENTRIC");
        telemetry.addData("Speed", gamepad.right_bumper ? "FAST" : "NORMAL");
        telemetry.addData("Heading (deg)", "%.1f", getHeadingDegrees());
        telemetry.addLine("A = toggle mode   |   OPTIONS = reset heading");
        telemetry.update();
    }

    public void driveFieldRelative(double forward, double strafe, double rotate) {
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        theta = AngleUnit.normalizeRadians(theta - getHeadingRadians());

        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        driveRobotCentric(newForward, newStrafe, rotate);
    }

    public void driveRobotCentric(double forward, double strafe, double rotate) {
        double frontLeft = forward + strafe + rotate;
        double frontRight = forward - strafe - rotate;
        double backLeft = forward - strafe + rotate;
        double backRight = forward + strafe - rotate;

        double max = DriveMath.maxAbs(frontLeft, frontRight, backLeft, backRight);
        if (max > 1.0) {
            frontLeft /= max;
            frontRight /= max;
            backLeft /= max;
            backRight /= max;
        }

        setMotorPowers(frontLeft, frontRight, backLeft, backRight);
    }

    public void setMotorPowers(double frontLeft, double frontRight, double backLeft, double backRight) {
        frontLeftDrive.setPower(frontLeft);
        frontRightDrive.setPower(frontRight);
        backLeftDrive.setPower(backLeft);
        backRightDrive.setPower(backRight);
    }

    // Makes the robot's current facing the new "field forward". Bound to OPTIONS in the teleop.
    public void resetHeading() {
        imu.resetYaw();
    }

    public double getHeadingRadians() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public double getHeadingDegrees() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
}
