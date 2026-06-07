package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.TestBench;
import org.firstinspires.ftc.teamcode.mechanisms.TestBenchDistance;

@Disabled
@TeleOp
public class DistanceTest extends OpMode {
    TestBenchDistance bench = new TestBenchDistance();
    @Override
    public void init() {
        bench.init(hardwareMap);

    }

    @Override
    public void loop() {
        double distance = bench.getDistance();
        if(distance<10){
            telemetry.addLine("Too close");
        }
        telemetry.addData("Distance",bench.getDistance());

    }
}
