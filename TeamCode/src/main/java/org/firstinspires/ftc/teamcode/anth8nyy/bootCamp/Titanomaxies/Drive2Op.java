package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.driveTrains.Ramp;
import org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.driveTrains.TankDriveEX;

@TeleOp(name = "TELOP_CG1_LITO")
public class Drive2Op extends OpMode {
    private final TankDriveEX drive = new TankDriveEX();
    Ramp ramp = new Ramp();
    Intake intake = new Intake();

    @Override
    public void init() {
        drive.init(hardwareMap);
        ramp.init(hardwareMap);
        intake.init(hardwareMap);
        telemetry.addLine("Robot Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y;
        double turn = -gamepad1.right_stick_x;
        drive.tankDrive(-gamepad1.left_stick_y,-gamepad1.right_stick_y,gamepad1.right_trigger);
        intake.start(gamepad1.left_bumper);
        ramp.up_down(gamepad2.y, gamepad2.a);
    }
}