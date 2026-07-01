// File: PovOp.java
package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.driveTrains;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.driveTrains.PovDriveSample;

@TeleOp(name = "TELOP_CG1_ATLAS")
public class Drive extends OpMode {

    private final PovDriveSample drive = new PovDriveSample();

    @Override
    public void init() {
        drive.init(hardwareMap);
        telemetry.addLine("Robot Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        drive.tankDrive(gamepad1.left_stick_y,gamepad1.right_stick_y,gamepad1.right_trigger);
    }
}
