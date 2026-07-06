package org.firstinspires.ftc.teamcode.anth8nyy.GNRT;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp(name = "GNRT")
public class GNRT extends OpMode {

    private Drive drive;
    private Intake intake;
    private Shooter shooter;
    private Brace_Climber braceClimber;
    private Rumble rumble;
    private GamepadEx gamepad;

    @Override
    public void init() {
        drive.init(hardwareMap);
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        braceClimber.init(hardwareMap);
        gamepad.init(gamepad1);
    }

    @Override
    public void start() {
        rumble.start();
    }

    @Override
    public void loop() {
        gamepad.update();
        drive.povDrive(gamepad);
        shooter.gamepadUpdate(gamepad);
        rumble.update(gamepad);
    }
}