package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.TuningThrower;

@Disabled
@TeleOp
public class TuningThrowerOp extends OpMode {
    TuningThrower thrower = new TuningThrower();
    @Override
    public void init() {
        thrower.init(hardwareMap);
    }
    @Override
    public void loop() {
        thrower.thrower(gamepad1.yWasPressed(), gamepad1.bWasPressed(), gamepad1.dpadLeftWasPressed(), gamepad1.dpadRightWasPressed(), gamepad1.dpadUpWasPressed(), gamepad1.dpadDownWasPressed());
    }
}
