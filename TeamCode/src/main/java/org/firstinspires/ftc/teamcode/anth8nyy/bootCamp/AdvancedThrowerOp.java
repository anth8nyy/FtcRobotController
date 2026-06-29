package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Challenge 1", group = "bootCamp")

public class AdvancedThrowerOp extends OpMode{
    AdvancedThrower thrower = new AdvancedThrower();

    @Override
    public void init() {
        thrower.init(hardwareMap);
    }

    @Override
    public void loop() {
        // A button starts the thrower
        if (gamepad1.a) {
            thrower.start();
        }

        // B button stops the thrower
        if (gamepad1.b) {
            thrower.stop();
        }

        // show current motor state on driver station
        telemetry.addData("Thrower on", thrower.on);
        telemetry.update();
    }
}
