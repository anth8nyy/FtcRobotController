package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class AprilTagDetEx {
    private static final int    APRILTAG_PIPELINE = 8;       //I have to change this
    private static final double DISTANCE_SCALE    = 30665.95; // units: centimeters
    private LLResult lastResult;
    private Pose3D lastBotPose;
    private DcMotorThrowerMechEx thrower;
    Limelight3A limelight;
    private IMU imu;
    private double distance = Double.MAX_VALUE;

    public void init(HardwareMap hardwareMap, DcMotorThrowerMechEx thrower){
        this.thrower = thrower;
        limelight = hardwareMap.get(Limelight3A.class,"limelight");
        limelight.pipelineSwitch(APRILTAG_PIPELINE);
        imu = hardwareMap.get(IMU.class,"imu");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);
        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
        limelight.start();
    }
    public void getMeasurements() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw());
        lastResult = limelight.getLatestResult();
        if (lastResult != null && lastResult.isValid()) {
            lastBotPose = lastResult.getBotpose_MT2();
            if(lastResult.getTa()>0.00001) {
                distance = (DISTANCE_SCALE / lastResult.getTa());
            }
            else{
                distance = Double.MAX_VALUE;
            }
        }
        else {
            lastBotPose = null;       // tag is gone, clear the pose
            distance = Double.MAX_VALUE; // and treat distance as unknown
        }
    }
    public void checkDistance() {
        if (distance>0.2 && distance < 10.0){
            thrower.start();
        }
        else {
            thrower.stop();
        }
    }
    public double getStoredDistance()  { return distance; }
    public double getTx()              { return lastResult != null ? lastResult.getTx() : 0; }
    public double getTy()              { return lastResult != null ? lastResult.getTy() : 0; }
    public double getTa()              { return lastResult != null ? lastResult.getTa() : 0; }
    public String getBotPose()         { return lastBotPose != null ? lastBotPose.toString() : "N/A"; }
    public double getYaw()             { return lastBotPose != null ? lastBotPose.getOrientation().getYaw() : 0; }
}
