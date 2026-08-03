package org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
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

    public void init(HardwareMap hardwareMap) {
        frontLeftDrive = hardwareMap.get(DcMotor.class, Config.Motors.FRONT_LEFT.hardwareName);
        frontRightDrive = hardwareMap.get(DcMotor.class, Config.Motors.FRONT_RIGHT.hardwareName);
        backLeftDrive = hardwareMap.get(DcMotor.class, Config.Motors.BACK_LEFT.hardwareName);
        backRightDrive = hardwareMap.get(DcMotor.class, Config.Motors.BACK_RIGHT.hardwareName);
        imu = hardwareMap.get(IMU.class, Config.IMU);

        frontLeftDrive.setDirection(Config.Motors.FRONT_LEFT.direction);
        frontRightDrive.setDirection(Config.Motors.FRONT_RIGHT.direction);
        backLeftDrive.setDirection(Config.Motors.BACK_LEFT.direction);
        backRightDrive.setDirection(Config.Motors.BACK_RIGHT.direction);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT
                )
        );
        imu.initialize(parameters);
        imu.resetYaw();
    }

    public void driveFieldRelative(double forward, double right, double rotate) {
        double theta = Math.atan2(forward, right);
        double r = Math.hypot(right, forward);

        theta = AngleUnit.normalizeRadians(theta - getHeadingRadians());

        double newForward = r * Math.sin(theta);
        double newRight = r * Math.cos(theta);

        drive(newForward, newRight, rotate);
    }

    public void drive(double forward, double right, double rotate) {
        double frontLeft = forward + right + rotate;
        double frontRight = forward - right - rotate;
        double backLeft = forward - right + rotate;
        double backRight = forward + right - rotate;

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
        backRightDrive.setPower(backLeft);
    }

    /** Makes the robot's current facing the new "field forward". Bound to OPTIONS in the teleop. */
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
