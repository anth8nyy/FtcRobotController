package org.firstinspires.ftc.teamcode.anth8nyy.pre_made.driveTrains;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples.driveTrains.AdvancedArcadeDriveSample;

@TeleOp(name = "Advanced POV Drive", group = "Drive")
public class AdvancedArcadeDrive extends OpMode {

    AdvancedArcadeDriveSample drive = new AdvancedArcadeDriveSample();
    @Override
    public void init() {
        drive.init(hardwareMap);
        telemetry.addLine("Ready to drive!");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Left stick Y  → forward/back
        // Left stick X  → turn
        // Right trigger → speed boost
        drive.tankDrive(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_trigger
        );

        telemetry.addData("Forward", -gamepad1.left_stick_y);
        telemetry.addData("Turn",     gamepad1.left_stick_x);
        telemetry.addData("Speed",    gamepad1.right_trigger);
        telemetry.update();
    }
}