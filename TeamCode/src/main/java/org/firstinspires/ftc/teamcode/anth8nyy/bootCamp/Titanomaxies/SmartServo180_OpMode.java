package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class SmartServo180_OpMode extends OpMode {
    SmartServo180Ex servo = new SmartServo180Ex();

    @Override
    public void init() {
        servo.init(hardwareMap);
    }

    @Override
    public void loop() {
        servo.update(gamepad1.a);
        telemetry.addData("Controls", "Press A = go to +180");
        telemetry.update();
    }
}


