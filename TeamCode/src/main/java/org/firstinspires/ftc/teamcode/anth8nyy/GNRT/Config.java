package org.firstinspires.ftc.teamcode.anth8nyy.GNRT;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Config {

    public enum Motors {
        LEFT_DRIVE("left_drive", DcMotorEx.Direction.REVERSE),
        RIGHT_DRIVE("right_drive", DcMotorEx.Direction.FORWARD),
        LEFT_SHOOTER_MOTOR("left_shooter_motor", DcMotorEx.Direction.FORWARD),
        RIGHT_SHOOTER_MOTOR("right_shooter_motor", DcMotorEx.Direction.REVERSE),
        BRACE_MOTOR("brace_motor", DcMotorEx.Direction.REVERSE),
        INTAKE_MOTOR("intake_motor", DcMotorEx.Direction.FORWARD);

        public final String hardwareName;
        public final DcMotorEx.Direction direction;

        Motors(String hardwareName, DcMotorEx.Direction direction) {
            this.hardwareName = hardwareName;
            this.direction = direction;
        }
    }
    public enum ServoForAprilTag {
        SERVO("servo_for_april_tag", Servo.Direction.FORWARD);
        public final String hardwareName;
        public final Servo.Direction direction;

        ServoForAprilTag(String hardwareName, Servo.Direction direction) {
            this.hardwareName = hardwareName;
            this.direction = direction;
        }
    }
    public enum ShooterValues {
        kV(0.00036),
        kS(0.06),
        kP(0.0003),
        SHOOTING_VELOCITY(2000),
        RPM_THRESHOLD(50);
        public final double value;
        ShooterValues(double value) {
            this.value = value;
        }
    }
    public static final String WEBCAM = "wabcam";
}


