package org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Subsystems.Shooter;

@Disabled
@TeleOp
public class TuningTheShooter extends LinearOpMode{
    private Shooter shooter = new Shooter();

    @Override
    public void runOpMode() {
        shooter.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            // Dpad up/down adjusts target velocity by 100 each press
            if (gamepad1.dpad_up) {
                shooter.setTargetVelocity(shooter.getTargetVelocity() + 100);
                sleep(200); // crude debounce
            }
            if (gamepad1.dpad_down) {
                shooter.setTargetVelocity(shooter.getTargetVelocity() - 100);
                sleep(200);
            }

            shooter.update();

            telemetry.addData("Target Velocity", shooter.getTargetVelocity());
            telemetry.addData("Actual Velocity", shooter.getVelocity());
            telemetry.addData("Error", shooter.getTargetVelocity() - shooter.getVelocity());
            telemetry.addData("Ready", shooter.isReady());
            telemetry.update();
        }
    }
}
