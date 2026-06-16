package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.practice.mechanisms.TestBenchColor;
@Disabled
@TeleOp
public class ColorSensorTest extends OpMode {

    TestBenchColor bench = new TestBenchColor();
    TestBenchColor.detectedColor detectedColor;
    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        bench.getDetectedColor(telemetry);
        detectedColor = bench.getDetectedColor(telemetry);
        telemetry.addData("color detected",detectedColor);

    }
}
