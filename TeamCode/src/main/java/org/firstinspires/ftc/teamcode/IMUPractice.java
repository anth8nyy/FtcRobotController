package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.mechanisms.TestBenchIMU;

@TeleOp

public class IMUPractice extends OpMode {

    TestBenchIMU bench = new TestBenchIMU();

    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        double heading = bench.getHeading(AngleUnit.DEGREES);
        telemetry.addData("Heading",heading);
        if(heading<0.5 && heading>-0.5){
            bench.setMotor(0);
        } else if (heading<0.5) {
            bench.setMotor(1);
        } else {
            bench.setMotor(-1);
        }

    }
}
