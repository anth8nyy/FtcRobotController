package org.firstinspires.ftc.teamcode.anth8nyy.GNRT;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "GNRT")
public class GNRT extends OpMode {

    private Drive drive;
    private GamepadEx gamepad;

    @Override
    public void init() {
        drive = new Drive(hardwareMap);
        gamepad = new GamepadEx(gamepad1);
    }

    @Override
    public void loop() {
        gamepad.update();
        drive.drive(gamepad);
        telemetry.addData("left", drive.getLeftPower());
        telemetry.addData("right", drive.getRightPower());
        telemetry.update();
    }
}