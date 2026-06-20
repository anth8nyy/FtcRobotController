package org.firstinspires.ftc.teamcode.anth8nyy.pre_made;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples.AprilTagDetectionEx;
import org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples.SmartServoEx;
import org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples.driveTrains.AdvancedArcadeDriveSample;

@Disabled
@TeleOp
public class FirstOpMode extends OpMode {
    AprilTagDetectionEx aprilTag = new AprilTagDetectionEx();
    SmartServoEx thrower = new SmartServoEx();
    AdvancedArcadeDriveSample drive = new AdvancedArcadeDriveSample();
    @Override
    public void init() {
        thrower.init(hardwareMap);
        aprilTag.init(hardwareMap, thrower);
        drive.init(hardwareMap);
    }

    @Override
    public void start() {
        aprilTag.start();
    }
    @Override
    public void loop() {
        drive.tankDrive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_trigger);
        aprilTag.getMeasurements();
        aprilTag.checkDistance();
        telemetry.addData("Distance",    aprilTag.getStoredDistance());
        telemetry.addData("Target x",    aprilTag.getTx());
        telemetry.addData("Target y",    aprilTag.getTy());
        telemetry.addData("Target area", aprilTag.getTa());
        telemetry.addData("Bot Pose",    aprilTag.getBotPose());
        telemetry.addData("Yaw",         aprilTag.getYaw());
        telemetry.update();
    }
}

