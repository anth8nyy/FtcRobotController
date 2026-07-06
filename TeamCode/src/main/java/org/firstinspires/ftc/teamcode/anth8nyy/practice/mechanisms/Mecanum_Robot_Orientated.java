package org.firstinspires.ftc.teamcode.anth8nyy.practice.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Mecanum_Robot_Orientated {

        DcMotor frontLeftDrive;
        DcMotor frontRightDrive;
        DcMotor backLeftDrive;
        DcMotor backRightDrive;

        public void init(HardwareMap hardwareMap) {
            frontLeftDrive  = hardwareMap.get(DcMotor.class, "front_left_drive");
            frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
            backLeftDrive   = hardwareMap.get(DcMotor.class, "back_left_drive");
            backRightDrive  = hardwareMap.get(DcMotor.class, "back_right_drive");

            backRightDrive.setDirection(DcMotor.Direction.REVERSE);
            frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);

            frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public void drive(double forward, double right, double rotate) {
            double frontLeftPower  = forward + right + rotate;
            double frontRightPower = forward - right - rotate;
            double backRightPower  = forward + right - rotate;
            double backLeftPower   = forward - right + rotate;

            double maxPower = 1.0;
            double maxSpeed = 1.0;

            maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
            maxPower = Math.max(maxPower, Math.abs(frontRightPower));
            maxPower = Math.max(maxPower, Math.abs(backRightPower));
            maxPower = Math.max(maxPower, Math.abs(backLeftPower));

            frontLeftDrive.setPower(maxSpeed  * (frontLeftPower  / maxPower));
            frontRightDrive.setPower(maxSpeed * (frontRightPower / maxPower));
            backLeftDrive.setPower(maxSpeed   * (backLeftPower   / maxPower));
            backRightDrive.setPower(maxSpeed  * (backRightPower  / maxPower));
        }

    }

