package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class RumbleTest extends OpMode {
    double endGameStart;
    boolean isEndGame;

    @Override
    public void init() {

    }

    @Override
    public void start() {
        endGameStart = getRuntime() + 90;
    }

    @Override
    public void loop() {
        if(endGameStart>= getRuntime() && !isEndGame){
            gamepad1.rumbleBlips(3);
            isEndGame = true;
        }

    }
}
