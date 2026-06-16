package org.firstinspires.ftc.teamcode.challenges;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.challenges.mechanisms.Challenge_1;

@TeleOp
public class Challenge_1_Op extends OpMode {
    Challenge_1 drive = new Challenge_1();

    @Override
    public void init() {
        drive.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        drive.speedMultiplier(gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_trigger);
    }
}
