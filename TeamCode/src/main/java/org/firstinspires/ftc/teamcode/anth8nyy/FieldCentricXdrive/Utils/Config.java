package org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Utils;


import com.qualcomm.robotcore.hardware.DcMotorEx;

/**
 * Central configuration for the X-Drive drivetrain.
 *     FRONT
 *   fldm \   / frdm
 *          X
 *   bldm /   \ brdm
 *     BACK

 */
public class Config {
    public enum Motors {
        FRONT_LEFT("frontLeftDrive", DcMotorEx.Direction.REVERSE),
        FRONT_RIGHT("frontRightDrive", DcMotorEx.Direction.FORWARD),
        BACK_LEFT("backLeftDrive", DcMotorEx.Direction.REVERSE),
        BACK_RIGHT("backRightDrive", DcMotorEx.Direction.FORWARD);

        public final String hardwareName;
        public final DcMotorEx.Direction direction;

        Motors(String hardwareName, DcMotorEx.Direction direction) {
            this.hardwareName = hardwareName;
            this.direction = direction;
        }
    }
    public enum DriveValues {
        NORMAL_SPEED(0.6),      // overall max speed when the right bumper is NOT held
        MAX_SPEED(1.0),         // overall max speed while the right bumper IS held
        INPUT_DEADZONE(0.05);   // stick magnitude below this is treated as zero (drift rejection)

        public final double value;

        DriveValues(double value) {
            this.value = value;
        }
    }

    // ---------------------------------------------- IMU ---------------------------------------------- //

    // The Control Hub's built-in IMU. "imu" matches the device present on every REV Control Hub.
    public static final String IMU = "imu";
}
