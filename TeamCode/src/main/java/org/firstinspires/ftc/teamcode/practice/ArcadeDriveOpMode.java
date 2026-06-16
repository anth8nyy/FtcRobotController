package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.practice.mechanisms.ArcadeDriveClass;
@Disabled
@TeleOp
public class ArcadeDriveOpMode extends OpMode {
    ArcadeDriveClass drive = new ArcadeDriveClass();
    double throttle, spin;
    @Override
    public void init() {
        drive.init(hardwareMap);

    }

    @Override
    public void loop() {
        throttle = -gamepad1.left_stick_y;
        spin = gamepad1.left_stick_x;

        drive.drive(throttle,spin);

    }
}
