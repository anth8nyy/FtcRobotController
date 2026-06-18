package org.firstinspires.ftc.teamcode.anth8nyy.practice;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class GamePadPractise extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        //runs 50x* a second
        double speedForward = -gamepad1.left_stick_y/2.0;
        double difference = gamepad1.left_stick_x - gamepad1.right_stick_x;
        double sum = gamepad1.right_trigger + gamepad1.left_trigger;
        telemetry.addData("left x", gamepad1.left_stick_x);
        telemetry.addData("left y", speedForward);
        telemetry.addData("a", gamepad1.a);
        telemetry.addData("b", gamepad1.b);
        telemetry.addData("right x", gamepad1.right_stick_x);
        telemetry.addData("right y", gamepad1.right_stick_y);
        telemetry.addData("difference x", difference);
        telemetry.addData("sum rear triggers",sum);
    }
}
