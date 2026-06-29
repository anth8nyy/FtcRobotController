package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.AprilTagDetEx;

import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.DcMotorThrowerMechEx;
import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.driveTrains.AdvancedArcadeDriveSample;

@Disabled
@TeleOp
public class FirstOpMode extends OpMode {
    AprilTagDetEx aprilTag = new AprilTagDetEx();
    DcMotorThrowerMechEx thrower = new DcMotorThrowerMechEx();
    AdvancedArcadeDriveSample drive = new AdvancedArcadeDriveSample();

    @Override
    public void init() {
        thrower.init(hardwareMap);
        aprilTag.init(hardwareMap, thrower);
        drive.init(hardwareMap);
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

