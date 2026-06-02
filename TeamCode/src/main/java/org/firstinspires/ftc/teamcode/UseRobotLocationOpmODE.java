package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class UseRobotLocationOpmODE extends OpMode {
    RobotLocationPractice robotLocationPractice = new RobotLocationPractice(0);
    @Override
    public void init() {
        robotLocationPractice.setAngle(0);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            robotLocationPractice.turnRobot(0.1);
        }
        else if (gamepad1.b){
            robotLocationPractice.turnRobot(-0.1);
        }
        telemetry.addData("Heading", robotLocationPractice.getHeading());
        telemetry.addData("Angle",robotLocationPractice.getAngle());
        if (gamepad1.dpad_left){
            robotLocationPractice.changeX(0.1);

        }
        else if (gamepad1.dpad_right){
            robotLocationPractice.changeX(-0.1);
        }
        telemetry.addData("x", robotLocationPractice.getX());
        if (gamepad1.dpadDownWasPressed()){
            robotLocationPractice.changeY(0.1);
        }
        else if (gamepad1.dpadUpWasPressed()){
            robotLocationPractice.changeY(-0.1);
        }
    }

}
