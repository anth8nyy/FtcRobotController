package org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.Config;
import org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils.GamepadEx;

public class Drive {

    // ----------------------------------------- Hardware ----------------------------------------- //
    public DcMotorEx leftDrive;
    public DcMotorEx rightDrive;

    // --------------------------------------- Ramping State -------------------------------------- //
    private double currentLeftPower = 0.0;
    private double currentRightPower = 0.0;
    private final ElapsedTime loopTimer = new ElapsedTime();

    // ------------------------------------- Tuning Constants ------------------------------------- //
    private static final double RAMP_TIME_SECONDS = 0.20; // time to go from 0 to 100
    private static final double MAX_ACCELERATION_PER_SECOND = 1.0 / RAMP_TIME_SECONDS;

    public static final double MAX_SPEED = 1.0;
    public static final double DEFAULT_SPEED = 0.6;

    private static final double LEFT_KS = Config.DriveMotorsValues.kS.value;
    private static final double LEFT_KV = Config.DriveMotorsValues.kV.value;
    private static final double RIGHT_KS = Config.DriveMotorsValues.kS.value;
    private static final double RIGHT_KV = Config.DriveMotorsValues.kV.value;

    // -------------------------------------- Initialization -------------------------------------- //

    public void init(HardwareMap hardwareMap) {
        leftDrive = hardwareMap.get(DcMotorEx.class, Config.Motors.LEFT_DRIVE.hardwareName);
        rightDrive = hardwareMap.get(DcMotorEx.class, Config.Motors.RIGHT_DRIVE.hardwareName);

        leftDrive.setDirection(Config.Motors.LEFT_DRIVE.direction);
        rightDrive.setDirection(Config.Motors.RIGHT_DRIVE.direction);

        // Reset encoders to zero before switching to a mode that uses them.
        leftDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // Use encoder feedback internally to keep speed consistent for a given power.
        leftDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        // Actively resist movement when power is 0, instead of coasting.
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        loopTimer.reset();
    }
    // -------------------------------------- Gamepad Input -------------------------------------- //

    public void povDrive(GamepadEx gamepad) {
        double forward = gamepad.getLeftStickY();
        double turn = gamepad.getRightStickX();
        boolean boostHeld = gamepad.isDown(GamepadEx.Button.RIGHT_BUMPER);
        double speedMultiplier = boostHeld ? 1.0 : 0.0;

        drive(forward, turn, speedMultiplier);
    }

    // -------------------------------------- Main Drive Logic -------------------------------------- //

    public void drive(double forward, double turn, double speedMultiplier) {

        double leftTargetPower = speedRestriction(forward + turn, -1.0, 1.0);
        double rightTargetPower = speedRestriction(forward - turn, -1.0, 1.0);


        double deltaTime = loopTimer.seconds();
        loopTimer.reset();

        currentLeftPower = rampTowardTarget(currentLeftPower, leftTargetPower, deltaTime);
        currentRightPower = rampTowardTarget(currentRightPower, rightTargetPower, deltaTime);

        double speed = DEFAULT_SPEED + speedMultiplier * (MAX_SPEED - DEFAULT_SPEED);
        double leftPower = currentLeftPower * speed;
        double rightPower = currentRightPower * speed;

        double leftOutput = applyFeedforward(leftPower, LEFT_KS, LEFT_KV);
        double rightOutput = applyFeedforward(rightPower, RIGHT_KS, RIGHT_KV);

        leftDrive.setPower(leftOutput);
        rightDrive.setPower(rightOutput);
    }

    // ------------------------------------------- Helpers ------------------------------------------- //

    // Restricts a value to stay within [min, max].
    private double speedRestriction(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    // Moves `current` toward `target`, but never changes it faster than the allowed rate.
    private double rampTowardTarget(double current, double target, double deltaTime) {

        if (deltaTime > 0.1) {
            deltaTime = 0.1;
        }

        double maxChangeThisLoop = MAX_ACCELERATION_PER_SECOND * deltaTime;

        if (current < target) {
            return Math.min(current + maxChangeThisLoop, target);
        } else {
            return Math.max(current - maxChangeThisLoop, target);
        }
    }
    // Adds static friction compensation (kS) and scales by velocity gain (kV).
    private double applyFeedforward(double power, double kS, double kV) {
        return kS * Math.signum(power) + kV * power;
    }
}