package org.firstinspires.ftc.teamcode.anth8nyy;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class Test extends OpMode {
    DcMotorEx motor;
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class,"motor");

    }


    @Override
    public void loop() {
        motor.setVelocity(3000);
        telemetry.addData("Velocity",motor.getVelocity());
    }
}

