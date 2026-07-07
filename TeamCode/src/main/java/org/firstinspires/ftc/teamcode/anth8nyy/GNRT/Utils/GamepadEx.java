package org.firstinspires.ftc.teamcode.anth8nyy.GNRT.Utils;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class GamepadEx {
    private Gamepad controller; // raw FTC gamepad being wrapped

    public enum Button { // Enum for button mapping
        A, B, X, Y, DPAD_UP, DPAD_DOWN, DPAD_LEFT, DPAD_RIGHT,
        LEFT_BUMPER, RIGHT_BUMPER, LEFT_STICK_BUTTON, RIGHT_STICK_BUTTON
    }

    private final Map<Button, BooleanSupplier> buttonMap = new EnumMap<>(Button.class); // button -> live value reader

    private boolean cur_states[] = new boolean[Button.values().length]; // this cycle's states
    private boolean prev_states[] = new boolean[Button.values().length]; // last cycle's states

    public void init(Gamepad gamepad1) { // builds the button -> reader map
        this.controller = gamepad1;

        buttonMap.put(Button.A, () -> controller.a);
        buttonMap.put(Button.B, () -> controller.b);
        buttonMap.put(Button.X, () -> controller.x);
        buttonMap.put(Button.Y, () -> controller.y);
        buttonMap.put(Button.DPAD_UP, () -> controller.dpad_up);
        buttonMap.put(Button.DPAD_DOWN, () -> controller.dpad_down);
        buttonMap.put(Button.DPAD_LEFT, () -> controller.dpad_left);
        buttonMap.put(Button.DPAD_RIGHT, () -> controller.dpad_right);
        buttonMap.put(Button.LEFT_BUMPER, () -> controller.left_bumper);
        buttonMap.put(Button.RIGHT_BUMPER, () -> controller.right_bumper);
        buttonMap.put(Button.LEFT_STICK_BUTTON, () -> controller.left_stick_button);
        buttonMap.put(Button.RIGHT_STICK_BUTTON, () -> controller.right_stick_button);
    }

    public void update() { // call once per loop, before reading states
        prev_states = cur_states.clone();
        for (Button button : Button.values()) {
            cur_states[button.ordinal()] = buttonMap.get(button).getAsBoolean();
        }
    }

    // ---------------------------------- Rising Edge Detection --------------------------------- //
    public boolean justPressed(Button button) { // true only on released -> pressed transition
        return cur_states[button.ordinal()] && !prev_states[button.ordinal()];
    }

    // --------------------------------- Falling Edge Detection --------------------------------- //
    public boolean justReleased(Button button) { // true only on pressed -> released transition
        return !cur_states[button.ordinal()] && prev_states[button.ordinal()];
    }

    // ------------------------------------- Button States -------------------------------------- //
    public boolean isDown(Button button) { // true if currently held
        return cur_states[button.ordinal()];
    }

    public boolean isUp(Button button) { // true if currently not held
        return !cur_states[button.ordinal()];
    }

    // ----------------------------------------- Linear ----------------------------------------- //
    public double getLeftStickX() { // left stick X, -1.0 to 1.0
        return controller.left_stick_x;
    }

    public double getLeftStickY() { // left stick Y, sign-flipped so up is positive
        return -controller.left_stick_y; // Minus to fix the incorrect joystick sign (+ -> up)
    }

    public double getRightStickX() { // right stick X, -1.0 to 1.0
        return controller.right_stick_x;
    }

    public double getRightStickY() { // right stick Y, sign-flipped so up is positive
        return -controller.right_stick_y; // Minus to fix the incorrect joystick sign (+ -> up)
    }

    public double getLeftTrigger() { // left trigger, 0.0 to 1.0
        return controller.left_trigger;
    }

    public double getRightTrigger() { // right trigger, 0.0 to 1.0
        return controller.right_trigger;
    }

    public Gamepad getGamepad() { // raw gamepad, for calls not wrapped here (e.g. rumble)
        return controller;
    }
}
