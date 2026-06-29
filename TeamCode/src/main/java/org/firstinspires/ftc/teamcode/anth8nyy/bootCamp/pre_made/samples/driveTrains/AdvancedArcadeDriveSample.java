package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.pre_made.samples.driveTrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class AdvancedArcadeDriveSample {

        // ----------------------------- Feedforward Constants ----------------------------- //
        // ADDED: Three feedforward constants replace the old raw speedMultiplier scaling.
        //
        // KS (Static gain): A small fixed value added whenever the motor moves.
        //   Without this, the motor won't respond at all to low inputs because
        //   static friction resists the first bit of movement. KS overcomes that.
        //   Left and right are separate because real motors are never perfectly matched.
        //
        // KV (Velocity gain): Multiplies the input linearly.
        //   Ideally KV = 1.0, meaning "input maps 1:1 to output."
        //   If your motor is weaker than expected, lower KV slightly (e.g. 0.95).
        //   Again, left/right are separate for per-side tuning.
        private static final double LEFT_KS  = 0.05;
        private static final double RIGHT_KS = 0.05;
        private static final double LEFT_KV  = 1.00;
        private static final double RIGHT_KV = 1.00;

        // KS_THETA: Same idea as KS but specifically for turning.
        //   When you turn, both wheels push against each other, creating extra friction.
        //   This adds a small boost to the turning side (and subtracts from the other)
        //   so the robot doesn't feel "stuck" when initiating a rotation.
        private static final double KS_THETA = 0.03;

        // ----------------------------- Power Constants ----------------------------------- //
        // ADDED: Instead of the old code's raw speedMultiplier (which would allow 0 power),
        //   we now define a floor (DEFAULT_SPEED) and ceiling (MAX_SPEED).
        //   The trigger maps between these two values, so the robot is always
        //   at least somewhat responsive even with no trigger pressed.
        private static final double DEFAULT_SPEED = 0.6;
        private static final double MAX_SPEED     = 1.0;

        // ----------------------------- Slew Rate (Anti-Jerk) ---------------------------- //
        // ADDED: The slew rate caps how fast power is allowed to change per second.
        //   The old code applied power instantly — fine on paper, but in practice
        //   a sudden jump from 0 to full power spins the wheels and jerks the robot.
        //   2.5 means power can change at most 2.5 units/sec (0→full in ~0.4 seconds).
        //   Lower = smoother but more sluggish. Higher = more responsive but more jerk.
        private static final double SLEW_RATE = 2.5;

        // ----------------------------- Hardware ----------------------------------------- //
        // CHANGED: DcMotor → DcMotorEx.
        //   DcMotorEx is the extended motor class. It supports everything DcMotor does,
        //   but also exposes velocity/encoder data needed for future closed-loop control.
        //   Feedforward works fine with DcMotor, but DcMotorEx future-proofs the code.
        public DcMotorEx leftDrive  = null;
        public DcMotorEx rightDrive = null;

        // ADDED: These store the motor power from the previous loop tick.
        //   The slew limiter compares the new target power against these
        //   to decide how much it's allowed to change this tick.
        private double prevLeft  = 0.0;
        private double prevRight = 0.0;

        // ADDED: Tracks real elapsed time between update() calls.
        //   Used by the slew limiter so acceleration is consistent
        //   regardless of how fast or slow the control loop runs.
        private final ElapsedTime timer = new ElapsedTime();

        // ----------------------------- Init --------------------------------------------- //
        public void init(HardwareMap hardwareMap) {
            leftDrive  = hardwareMap.get(DcMotorEx.class, "left_drive");
            rightDrive = hardwareMap.get(DcMotorEx.class, "right_drive");

            leftDrive.setDirection(DcMotor.Direction.REVERSE);
            rightDrive.setDirection(DcMotor.Direction.FORWARD);

            // ADDED: BRAKE mode makes the motor actively resist motion at zero power.
            //   The old code defaulted to FLOAT (coast), which causes the robot to
            //   drift after releasing the stick — bad for precise positioning.
            leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // ADDED: Reset the timer so the first dt reading isn't garbage.
            timer.reset();
        }

        // ----------------------------- Feedforward -------------------------------------- //
        // ADDED: This method applies the KS + KV formula to a raw power value.
        //   Formula: output = sign(input) * KS + input * KV
        //
        //   sign(input) returns +1 or -1 depending on direction, so KS always
        //   pushes in the same direction the motor is trying to go.
        //
        //   The dead-band check (< 1e-6) prevents KS from fighting static friction
        //   when the stick is at rest — without it, the robot would creep at zero input.
        private double feedforward(double input, double ks, double kv) {
            if (Math.abs(input) < 1e-6) return 0.0;
            return Math.signum(input) * ks + input * kv;
        }

        // ----------------------------- Slew Rate Limiter -------------------------------- //
        // ADDED: Limits how much power can change in one loop tick.
        //   maxDelta = SLEW_RATE * dt means the limit scales with real time —
        //   a slow loop allows a bigger step than a fast loop, so the robot
        //   always takes the same wall-clock time to reach full power.
        //
        //   Math.max(-maxDelta, Math.min(maxDelta, target - previous))
        //   clamps the change to [-maxDelta, +maxDelta], then adds it to previous.
        private double slew(double target, double previous, double dt) {
            double maxDelta = SLEW_RATE * dt;
            return previous + Math.max(-maxDelta, Math.min(maxDelta, target - previous));
        }

        // ----------------------------- Main Drive Method -------------------------------- //
        public void tankDrive(double stickY, double stickX, double speedMultiplier) {

            // ADDED: Read elapsed time since last call, then immediately reset.
            //   This gives us dt in seconds — used by the slew limiter below.
            double dt = timer.seconds();
            timer.reset();

            // CHANGED: Old code did  left * speedMultiplier  at the end, allowing 0 power.
            //   Now we map speedMultiplier (0→1) into the range [DEFAULT_SPEED, MAX_SPEED]
            //   so the robot always has a usable minimum speed with no trigger pressed.
            double maxPower = DEFAULT_SPEED + speedMultiplier * (MAX_SPEED - DEFAULT_SPEED);

            // Arcade mix — unchanged from original.
            double left  = stickY + stickX;
            double right = stickY - stickX;

            // Normalization — unchanged from original.
            double max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0) {
                left  /= max;
                right /= max;
            }

            // CHANGED: Old code multiplied by speedMultiplier here.
            //   Now we multiply by maxPower (the mapped value from above).
            left  *= maxPower;
            right *= maxPower;

            // ADDED: KS_THETA — small static boost applied to the turning axis.
            //   sign(stickX) gives +1 when turning right, -1 when turning left.
            //   Adding to left and subtracting from right pushes both sides
            //   in the direction needed to rotate, overcoming rotational friction.
            double thetaBoost = KS_THETA * Math.signum(stickX);
            left  += thetaBoost;
            right -= thetaBoost;

            // ADDED: Slew limiter applied before feedforward.
            //   Order matters — we want to limit the mechanical demand on the motor,
            //   not the feedforward-adjusted output.
            left  = slew(left,  prevLeft,  dt);
            right = slew(right, prevRight, dt);

            // ADDED: Save this tick's power as "previous" for next tick's slew calculation.
            prevLeft  = left;
            prevRight = right;

            // CHANGED: Old code called setPower(left * speedMultiplier) directly.
            //   Now we pass through feedforward() first, which adds KS and applies KV
            //   so the motor actually receives enough power to move at low inputs.
            leftDrive.setPower(feedforward(left,  LEFT_KS,  LEFT_KV));
            rightDrive.setPower(feedforward(right, RIGHT_KS, RIGHT_KV));
        }
}
