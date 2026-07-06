// File: PovOp.java
package org.firstinspires.ftc.teamcode.anth8nyy.bootCamp.Titanomaxies;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {

    public DcMotor leftDrive   = null;
    public DcMotor  rightDrive  = null;
    private static final double DEFAULT_SPEED = 0.6;
    private static final double MAX_SPEED     = 1.0;


    public void init(HardwareMap hardwareMap){
        leftDrive  = hardwareMap.get(DcMotor.class, Config.l_d);
        rightDrive = hardwareMap.get(DcMotor.class, Config.r_d);

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    public void tankDrive(double stickY,double stickX ,double speedMultiplier){
        double maxPower = DEFAULT_SPEED + speedMultiplier * (MAX_SPEED - DEFAULT_SPEED);

        // Arcade mix — unchanged from original.
        double left  = stickY + stickX;
        double right = stickY - stickX;

        // Normalization — unchanged from original.
        double max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0) {
            left  /= max;
            right /= max;
        }

        // CHANGED: Old code multiplied by speedMultiplier here.
        //   Now we multiply by maxPower (the mapped value from above).
        left  *= maxPower;
        right *= maxPower;

        leftDrive.setPower(left);
        rightDrive.setPower(right);
    }
}
