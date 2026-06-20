package org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class AprilTagDetectionEx {
    private LLResult lastResult;
    private Pose3D lastBotPose;
    private SmartServoEx servo;
    Limelight3A limelight;
    private IMU imu;
    private double distance;

    public void init(HardwareMap hardwareMap, SmartServoEx servo){
        this.servo = servo;
        limelight = hardwareMap.get(Limelight3A.class,"limelight");
        limelight.pipelineSwitch(8); //I have to change this
        imu = hardwareMap.get(IMU.class,"imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }
    public void getMeasurements() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw());
        lastResult = limelight.getLatestResult();
        if (lastResult != null && lastResult.isValid()) {
            lastBotPose = lastResult.getBotpose_MT2();
            distance = getDistance(lastResult.getTa());
        }
    }
    public double getDistance(double ta){
        double scale = 30665.95;
        distance = (scale / ta);
        return distance;
    }
    public void checkDistance() {
        if (distance>0.2 && distance < 10.0){
            servo.setServoRot(1.0);
        }
        else {
            servo.setServoRot(0.0);
        }
    }
    public void start() {
        limelight.start();
    }
    public double getStoredDistance()  { return distance; }
    public double getTx()              { return lastResult != null ? lastResult.getTx() : 0; }
    public double getTy()              { return lastResult != null ? lastResult.getTy() : 0; }
    public double getTa()              { return lastResult != null ? lastResult.getTa() : 0; }
    public String getBotPose()         { return lastBotPose != null ? lastBotPose.toString() : "N/A"; }
    public double getYaw()             { return lastBotPose != null ? lastBotPose.getOrientation().getYaw() : 0; }
}
