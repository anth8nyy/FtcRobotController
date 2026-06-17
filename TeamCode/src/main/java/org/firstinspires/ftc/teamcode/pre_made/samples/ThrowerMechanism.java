package org.firstinspires.ftc.teamcode.pre_made.samples;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ThrowerMechanism {
    CRServo leftServo = null;
    CRServo rightServo = null;

    public void init(HardwareMap hardwareMap) {
        leftServo  = hardwareMap.get(CRServo.class, "leftServo");
        rightServo = hardwareMap.get(CRServo.class, "rightServo");
        rightServo.setDirection(CRServo.Direction.REVERSE);
    }

    // must return void, not named after the class
    public void shoot() {
        leftServo.setPower(1.0);
        rightServo.setPower(1.0);
    }

    public void stop() {
        leftServo.setPower(0);
        rightServo.setPower(0);
    }
}