package org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Config {

    // Which REGIONAL ALLIANCE this robot is playing for this MATCH. Change this line before
    // each match (or wherever your team prefers to set it).
    public static Alliance CURRENT_ALLIANCE = Alliance.RED;

    public enum Alliance {
        RED, BLUE
    }

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
    public enum ServoForShooting {
        SERVO_LEFT("servoLeft",Servo.Direction.FORWARD),
        SERVO_RIGHT("servoRight",Servo.Direction.REVERSE);
        public final String hardwareName;
        public final Servo.Direction direction;

        ServoForShooting(String hardwareName, Servo.Direction direction) {
            this.hardwareName = hardwareName;
            this.direction = direction;
        }
    }
    public enum ShooterValues {
        kV(0.00036), //velocity feedforward gain ( Used to sustain a speed you want )
        kS(0.06), //static friction feedforward gain ( Used to overcome static friction )
        kP(0.0003), //proportional feedback gain ( used to correct the remaining error between target and actual velocity )
        SHOOTING_VELOCITY(2000),
        RPM_ERROR(50);
        public final double value;
        ShooterValues(double value) {
            this.value = value;
        }
    }
    public enum DriveMotorsValues{
        kV(1),
        kS(0.05);
        public final double value;
        DriveMotorsValues(double value) {
            this.value = value;
        }
    }

    // -------------------------------- Suppression Unit AprilTags -------------------------------- //
    // TODO: fill in with the real IDs from https://first.global/fgc/robot-kit/. Per the manual,
    public static final int RED_SUPPRESSION_FRONT_TAG_ID = 1;
    public static final int RED_SUPPRESSION_SIDE_TAG_ID = 2;
    public static final int BLUE_SUPPRESSION_FRONT_TAG_ID = 3;
    public static final int BLUE_SUPPRESSION_SIDE_TAG_ID = 4;

    public static int[] getSuppressionTagIds(Alliance alliance) {
        return alliance == Alliance.RED
                ? new int[]{RED_SUPPRESSION_FRONT_TAG_ID, RED_SUPPRESSION_SIDE_TAG_ID}
                : new int[]{BLUE_SUPPRESSION_FRONT_TAG_ID, BLUE_SUPPRESSION_SIDE_TAG_ID};
    }

    public static final String WEBCAM = "webcam";
}