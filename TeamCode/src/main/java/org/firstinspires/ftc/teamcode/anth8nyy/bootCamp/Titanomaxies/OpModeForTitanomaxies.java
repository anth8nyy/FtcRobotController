package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp(name = "TELOP_CG1_HLEKTRA")
public class OpModeForTitanomaxies extends OpMode {
    Drive drive     = new Drive();
    Intake intake   = new Intake();

    Rumble rumble = new Rumble();
    Shooter shooter = new Shooter();
    @Override
    public void init() {
        drive.init(hardwareMap);
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        telemetry.addLine("Robot Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        rumble.start();
    }

    @Override
    public void loop() {
        drive.tankDrive(gamepad1.left_stick_y, -gamepad1.right_stick_x, gamepad1.right_trigger);
        intake.start(gamepad1.left_bumper, gamepad1.right_bumper);
        rumble.update(gamepad1);
        shooter.update( gamepad1.a);

        telemetry.addData("Intake Power",    intake.getPower());
        telemetry.addData("Brace Motor Velocity", shooter.getPower());
        telemetry.update();
    }
}