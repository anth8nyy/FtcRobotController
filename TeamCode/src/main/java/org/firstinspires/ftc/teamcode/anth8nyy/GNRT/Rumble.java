package org.firstinspires.ftc.teamcode.anth8nyy.GNRT;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Rumble {
    private final ElapsedTime timer = new ElapsedTime();

    // 100 seconds = 1 minute 40 seconds
    private static final double RUMBLE_TIME_SECONDS = 120;

    // so we only rumble once, not every loop after the time passes
    private boolean rumbled = false;

    public void start() {
        timer.reset(); // call this when the OpMode starts
    }

    public void update(GamepadEx gamepad) {
        if (!rumbled && timer.seconds() >= RUMBLE_TIME_SECONDS) {
            gamepad.getGamepad().rumble(1.0, 1.0, 2000); // rumble both sides for 1 second
            rumbled = true;
        }
    }
}
