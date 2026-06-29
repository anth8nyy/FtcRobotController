package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples;

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
    private DcMotorThrowerMechEx thrower;
    public Limelight3A limelight;
    private IMU imu;
    private double distance = Double.MAX_VALUE;

    public void init(HardwareMap hardwareMap, DcMotorThrowerMechEx thrower){
        this.thrower = thrower;
        limelight = hardwareMap.get(Limelight3A.class,"limelight");
        limelight.pipelineSwitch(8); //I have to change this
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
            distance = getDistance(lastResult.getTa());
        }
    }
    public double getDistance(double ta){
        if(ta>0.0001){
            double scale = 30665.95;
            distance = (scale / ta);
            return distance;
        }
        else{
            distance = Double.MAX_VALUE;
            return distance;
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
