package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Challenge_1 extends OpMode {
    PovDriveFieldOr drive = new PovDriveFieldOr();
    @Override
    public void init() {
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        drive.driveFieldRelative(
                -gamepad1.left_stick_y,   // forward (negated — FTC sticks are inverted on Y)
                gamepad1.left_stick_x,    // right (strafe input, unused by tank drive but kept for the math)
                gamepad1.right_stick_x,   // rotate
                gamepad1.right_trigger    // speed multiplier
        );
    }
}
