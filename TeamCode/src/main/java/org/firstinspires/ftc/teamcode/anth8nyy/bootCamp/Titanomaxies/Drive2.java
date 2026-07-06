package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drive2 {
        private static final double RAMP_TIME_SECONDS = 0.20;
        private static final double MAX_ACCELERATION_PER_SECOND = 1.0 / RAMP_TIME_SECONDS;

        private static final double START_ACCEL_FACTOR = 0.30; // αρχή αργή
        private static final double END_ACCEL_FACTOR = 1.75;   // μετά πιο γρήγορη
        private static final double HYPERBOLIC_STEEPNESS = 6.0; // πόσο απότομη είναι η καμπύλη tanh()

        // TUNE THIS to your motor's real max encoder speed in ticks/sec
        // (encoder ticks-per-rev * max free-spin RPM / 60). E.g. a goBILDA 312 RPM motor
        // with 537.7 ticks/rev is ~2800 ticks/sec. Wrong value = curve shaped wrong.
        private static final double MAX_TICKS_PER_SECOND = 2800.0;

        private static double DefaultSpeed = 0.3;
        private static double MaxSpeed = 0.80;

        // DcMotorEx instead of DcMotor so we can read encoder velocity (ticks/sec), not just position
        private DcMotorEx rightMotor;
        private DcMotorEx leftMotor;

        // Tracks the current ramped power for each side between loop iterations
        private double currentRightPower = 0.0;
        private double currentLeftPower = 0.0;

        // Used to measure real deltaTime between loop iterations for the ramp
        private final ElapsedTime loopTimer = new ElapsedTime();

        public void init(HardwareMap hardwareMap) {
            rightMotor = hardwareMap.get(DcMotorEx.class, Config.r_d);
            leftMotor = hardwareMap.get(DcMotorEx.class, Config.l_d);

            // One side is physically mounted backwards on most FTC drivetrains.
            // Flip this if the robot drives backwards/spins the wrong way.
            leftMotor.setDirection(DcMotor.Direction.REVERSE);
            rightMotor.setDirection(DcMotor.Direction.FORWARD);

            // Reset encoders to zero at start
            rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // RUN_USING_ENCODER: the hub's internal PID uses the encoder to hold the
            // requested power level steady (compensates for battery sag, friction, etc.)
            rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            loopTimer.reset();
        }

        public void drive(double forward, double turn, double speedMultiplier) {
            double rightTarget = clamp(forward - turn, -1.0, 1.0);
            double leftTarget = clamp(forward + turn, -1.0, 1.0);

            double deltaTime = loopTimer.seconds();
            loopTimer.reset();

            // Real, measured speed from the encoders right now, normalized to -1..1
            // so it's comparable to the -1..1 power/target scale.
            double rightVelocityNormalized = clamp(rightMotor.getVelocity() / MAX_TICKS_PER_SECOND, -1.0, 1.0);
            double leftVelocityNormalized = clamp(leftMotor.getVelocity() / MAX_TICKS_PER_SECOND, -1.0, 1.0);

            currentRightPower = adjustPower(currentRightPower, rightTarget, MAX_ACCELERATION_PER_SECOND, deltaTime, rightVelocityNormalized);
            currentLeftPower = adjustPower(currentLeftPower, leftTarget, MAX_ACCELERATION_PER_SECOND, deltaTime, leftVelocityNormalized);

            double maxPower = DefaultSpeed + speedMultiplier * (MaxSpeed - DefaultSpeed);

            rightMotor.setPower(currentRightPower * maxPower);
            leftMotor.setPower(currentLeftPower * maxPower);
        }

        private double clamp(double value, double min, double max) {
            return Math.max(min, Math.min(max, value));
        }

        private double adjustPower(double current, double target, double accelerationPerSecond, double deltaTime, double actualVelocityNormalized) {

            // Αν το Control Hub κολλήσει για λίγο, δεν αφήνουμε μεγάλο jump
            if (deltaTime > 0.1) {
                deltaTime = 0.1;
            }

            double maxChange = accelerationPerSecond * deltaTime;

            // Εδώ μπαίνει η υπερβολική (tanh) καμπύλη, βασισμένη στην πραγματική ταχύτητα του encoder
            double accelFactor = hyperbolicFactor(actualVelocityNormalized, target);

            double totalChange = maxChange * accelFactor;

            if (current < target) {
                return Math.min(current + totalChange, target);
            } else {
                return Math.max(current - totalChange, target);
            }
        }

        private double hyperbolicFactor(double actualVelocityNormalized, double target) {

            // Αν το target είναι σχεδόν 0, μη κάνεις περίεργη καμπύλη
            // Άσε το robot να κόψει κανονικά
            if (Math.abs(target) < 0.05) {
                return 1.0;
            }

            // progress = πόσο κοντά είναι η ΠΡΑΓΜΑΤΙΚΗ ταχύτητα (από τον encoder) στο target
            // στην αρχή είναι κοντά στο 0, κοντά στο τέλος είναι κοντά στο 1
            double progress = Math.abs(actualVelocityNormalized) / Math.abs(target);

            // προστασία για να μη βγει έξω από 0 έως 1
            progress = clamp(progress, 0.0, 1.0);

            // Υπερβολική καμπύλη (tanh): απότομη ανάβαση στην αρχή, ομαλό κάθισμα κοντά στο target.
            // tanh(0) = 0 και tanh(steepness) = max, οπότε διαιρούμε για να κανονικοποιήσουμε σε 0..1
            double shaped = Math.tanh(HYPERBOLIC_STEEPNESS * progress) / Math.tanh(HYPERBOLIC_STEEPNESS);

            // Ξεκινάει από START_ACCEL_FACTOR και φτάνει μέχρι END_ACCEL_FACTOR
            return START_ACCEL_FACTOR + (END_ACCEL_FACTOR - START_ACCEL_FACTOR) * shaped;
        }
    }

