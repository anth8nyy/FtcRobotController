package org.firstinspires.ftc.teamcode.practice.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestBenchColor {
    NormalizedColorSensor colorSensor;

    public enum detectedColor{
        RED,
        BLUE,
        YELLOW,
        UNKNOWN
    }

    public void init(HardwareMap hwMap){
        colorSensor = hwMap.get(NormalizedColorSensor.class,"colorSensor");
        colorSensor.setGain(8);
    }

    public detectedColor getDetectedColor(Telemetry telemetry){
        NormalizedRGBA colors = colorSensor.getNormalizedColors();//return 4 values
        float normRed,normBlue,normGreen;
        normRed = colors.red/colors.alpha;
        normGreen = colors.green/colors.alpha;
        normBlue = colors.blue/colors.alpha;

        telemetry.addData("red",normRed);
        telemetry.addData("blue",normBlue);
        telemetry.addData("green",normGreen);

        /*
        red,green,blue
        red = >.35, <.3, <.3
        yellow = >.5, >.9, <.6
        blue = <.2, <.5, >.5
         */
        if(normRed > .35 && normGreen <.3 && normBlue <.3){
            return detectedColor.RED;
        }
        else if (normRed >.5 && normGreen >.9 && normBlue <.6){
            return detectedColor.YELLOW;
        }
        else if (normRed <.2 && normGreen <.5 && normBlue>.5){
            return detectedColor.BLUE;
        }
        else {
            return detectedColor.UNKNOWN;
        }



    }
}
