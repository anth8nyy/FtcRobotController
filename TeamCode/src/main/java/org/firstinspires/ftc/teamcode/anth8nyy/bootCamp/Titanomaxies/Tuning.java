package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

@TeleOp(name = "Thrower PID Tuning", group = "Titanomaxies")
public class Tuning extends OpMode {

    // ── after tuning, copy these values into ThrowerOp ──
    public static double tunedP   = 18.9;
    public static double tunedD   = 0.0;
    public static double tunedVel = 500;
    // ─────────────────────────────────────────────────────

    private static final double STEP_FINE   = 0.1;
    private static final double STEP_COARSE = 1.0;

    private DcMotorEx thrower;
    private boolean tuningP = true;
    private boolean running = false;

    private boolean lastDpadUp    = false;
    private boolean lastDpadDown  = false;
    private boolean lastDpadLeft  = false;
    private boolean lastDpadRight = false;
    private boolean lastA         = false;
    private boolean lastB         = false;

    @Override
    public void init() {
        thrower = hardwareMap.get(DcMotorEx.class, "thrower");
        thrower.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        thrower.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        applyPID();
        telemetry.addLine("Initialized — press A to start/stop, B to switch P/D");
        telemetry.update();
    }

    @Override
    public void loop() {
        // A = start/stop
        if (gamepad1.a && !lastA) {
            running = !running;
            thrower.setVelocity(running ? tunedVel : 0);
        }

        // B = switch between P and D
        if (gamepad1.b && !lastB) {
            tuningP = !tuningP;
        }

        // dpad up — coarse increase
        if (gamepad1.dpad_up && !lastDpadUp) {
            if (tuningP) tunedP += STEP_COARSE;
            else         tunedD += STEP_COARSE;
            applyPID();
        }

        // dpad down — coarse decrease
        if (gamepad1.dpad_down && !lastDpadDown) {
            if (tuningP) tunedP -= STEP_COARSE;
            else         tunedD -= STEP_COARSE;
            applyPID();
        }

        // dpad right — fine increase
        if (gamepad1.dpad_right && !lastDpadRight) {
            if (tuningP) tunedP += STEP_FINE;
            else         tunedD += STEP_FINE;
            applyPID();
        }

        // dpad left — fine decrease
        if (gamepad1.dpad_left && !lastDpadLeft) {
            if (tuningP) tunedP -= STEP_FINE;
            else         tunedD -= STEP_FINE;
            applyPID();
        }

        // clamp so nothing goes negative
        tunedP = Math.max(0, tunedP);
        tunedD = Math.max(0, tunedD);

        // save button states
        lastDpadUp    = gamepad1.dpad_up;
        lastDpadDown  = gamepad1.dpad_down;
        lastDpadLeft  = gamepad1.dpad_left;
        lastDpadRight = gamepad1.dpad_right;
        lastA         = gamepad1.a;
        lastB         = gamepad1.b;

        telemetry.addData("Status",          running ? "RUNNING" : "STOPPED");
        telemetry.addData("Tuning",          tuningP ? ">>> P <<<" : ">>> D <<<");
        telemetry.addData("P",               tunedP);
        telemetry.addData("D",               tunedD);
        telemetry.addData("Target Velocity", tunedVel);
        telemetry.addData("Actual Velocity", thrower.getVelocity());
        telemetry.addData("Error",           tunedVel - thrower.getVelocity());
        telemetry.addLine("--- Controls ---");
        telemetry.addData("A",               "start / stop");
        telemetry.addData("B",               "switch P / D");
        telemetry.addData("dpad up/down",    "+/- 1.0");
        telemetry.addData("dpad left/right", "+/- 0.1");
        telemetry.update();
    }

    private void applyPID() {
        thrower.setPIDCoefficients(
                DcMotorEx.RunMode.RUN_USING_ENCODER,
                new PIDCoefficients(tunedP, 0, tunedD)
        );
    }
}
