package org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Utils;

public final class DriveMath {
    public static double applyDeadzone(double value, double deadzone) {
        return Math.abs(value) < deadzone ? 0.0 : value;
    }
    public static double maxAbs(double... values) {
        double max = 0.0;
        for (double value : values) {
            max = Math.max(max, Math.abs(value));
        }
        return max;
    }
}
