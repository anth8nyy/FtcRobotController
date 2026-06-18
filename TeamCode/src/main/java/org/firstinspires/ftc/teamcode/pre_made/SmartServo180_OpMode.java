package org.firstinspires.ftc.teamcode.pre_made;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pre_made.samples.SmartServo180Ex;

public class SmartServo180_OpMode extends OpMode {
    SmartServo180Ex servo = new SmartServo180Ex();

    @Override
    public void init() {
        servo.init(hardwareMap);
    }

    @Override
    public void loop() {
        servo.update(gamepad1.a);

        telemetry.addData("A pressed", gamepad1.a);
        telemetry.addData("Controls", "Hold A = go to -180  Release = go to 180");
        telemetry.update();
    }
}


