package org.firstinspires.ftc.teamcode.anth8nyy.practice.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Mecanum_Robot_Orientated_Op extends OpMode {
    Mecanum_Robot_Orientated drive = new Mecanum_Robot_Orientated();
    @Override
    public void init() {
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        drive.drive(gamepad1.left_stick_y,-gamepad1.left_stick_x,-gamepad1.right_stick_x);
    }
}
