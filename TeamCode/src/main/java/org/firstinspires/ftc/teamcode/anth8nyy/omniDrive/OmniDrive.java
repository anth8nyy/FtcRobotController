package org.firstinspires.ftc.teamcode.anth8nyy.omniDrive;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class OmniDrive {
    DcMotorEx frontLeftDrive;
    DcMotorEx frontRightDrive;
    DcMotorEx backLeftDrive;
    DcMotorEx backRightDrive;
    IMU imu;
    Telemetry telemetry;
    public OmniDrive(HardwareMap hardwareMap, Telemetry telemetry){
        frontLeftDrive.setDirection(OmniConfiguration.LEFT_FRONT_DIR);
        frontRightDrive.setDirection(OmniConfiguration.RIGHT_FRONT_DIR);
        backLeftDrive.setDirection(OmniConfiguration.LEFT_BACK_DIR);
        backRightDrive.setDirection(OmniConfiguration.RIGHT_BACK_DIR);


        this.telemetry = telemetry;
        telemetry.addData("Drive train","initialized");
    }
}
