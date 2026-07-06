package org.firstinspires.ftc.teamcode.anth8nyy.GNRT;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drive {
    public DcMotorEx leftDrive;
    public DcMotorEx rightDrive;

    private static final double RAMP_TIME_SECONDS = 0.20; // time to go 0 -> full power
    private static final double MAX_ACCELERATION_PER_SECOND = 1.0 / RAMP_TIME_SECONDS;

    private double currentLeftPower = 0.0;  // ramped power applied last loop, left side
    private double currentRightPower = 0.0; // ramped power applied last loop, right side

    public static final double MAX_SPEED = 1.0;
    public static final double DEFAULT_SPEED = 0.6;

    private final ElapsedTime loopTimer = new ElapsedTime(); // measures real deltaTime between loops

    public void init(HardwareMap hardwareMap) {
        leftDrive = hardwareMap.get(DcMotorEx.class, Config.Motors.LEFT_DRIVE.hardwareName);
        rightDrive = hardwareMap.get(DcMotorEx.class, Config.Motors.RIGHT_DRIVE.hardwareName);

        leftDrive.setDirection(Config.Motors.LEFT_DRIVE.direction);
        rightDrive.setDirection(Config.Motors.RIGHT_DRIVE.direction);

        leftDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        leftDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        loopTimer.reset();
    }

    public void drive(double forward, double turn, double speedMultiplier) {
        double leftTarget = maxPower(forward + turn, -1.0, 1.0);
        double rightTarget = maxPower(forward - turn, -1.0, 1.0);

        double deltaTime = loopTimer.seconds();
        loopTimer.reset();

        currentLeftPower = adjustPower(currentLeftPower, leftTarget, MAX_ACCELERATION_PER_SECOND, deltaTime);
        currentRightPower = adjustPower(currentRightPower, rightTarget, MAX_ACCELERATION_PER_SECOND, deltaTime);

        double maxPower = DEFAULT_SPEED + speedMultiplier * (MAX_SPEED - DEFAULT_SPEED);

        leftDrive.setPower(currentLeftPower * maxPower);
        rightDrive.setPower(currentRightPower * maxPower);
    }

    public void povDrive(GamepadEx gamepad) {
        double forward = gamepad.getLeftStickY();
        double turn = gamepad.getRightStickX();
        double speedMultiplier = gamepad.isDown(GamepadEx.Button.RIGHT_BUMPER) ? 1.0 : 0.0;

        drive(forward, turn, speedMultiplier);
    }

    private double maxPower(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    private double adjustPower(double current, double target, double accelerationPerSecond, double deltaTime) {
        if (deltaTime > 0.1) {
            deltaTime = 0.1;
        }

        double maxChange = accelerationPerSecond * deltaTime;

        if (current < target) {
            return Math.min(current + maxChange, target);
        } else {
            return Math.max(current - maxChange, target);
        }
    }
}