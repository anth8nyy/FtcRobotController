package org.firstinspires.ftc.teamcode.anth8nyy.pre_made.samples;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class DcMotorThrowerMechEx {
    private DcMotorEx motor;
    private boolean on = false;
    private boolean wasPressed = false;

    public void init(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotorEx.class, "thrower");
        motor.setDirection(DcMotorEx.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT); // FLOAT γιατί είναι thrower
    }

    public void update(boolean aPressed) {
        if (aPressed && !wasPressed) {
            on = !on; // toggle
            motor.setPower(on ? 1.0 : 0.0);
        }
        wasPressed = aPressed;
    }

    public boolean isOn() { return on; }
}
