package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class rambleTest extends OpMode {
    RumbleFast rumble = new RumbleFast();

    @Override
    public void init() {

    }

    @Override
    public void start() {
        rumble.start();
    }

    @Override
    public void loop() {
        rumble.update(gamepad1);
    }
}
