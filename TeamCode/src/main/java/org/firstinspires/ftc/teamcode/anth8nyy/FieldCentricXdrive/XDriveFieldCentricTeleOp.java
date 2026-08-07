package org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.anth8nyy.FieldCentricXdrive.Subsystems.XDrive;

// Driver-controlled X-Drive teleop. All the driving lives in XDrive.drive(gamepad, telemetry);
// this OpMode just makes the drivetrain and feeds it the gamepad once per loop.
//
// Controls (gamepad 1):
//   Left stick    - drive / strafe
//   Right stick X - rotate
//   A             - toggle robot-centric / field-centric
//   Options       - reset field heading to the robot's current facing
//   Right bumper  - hold for boosted speed
@TeleOp(name = "X-Drive TeleOp", group = "Main")
public class XDriveFieldCentricTeleOp extends OpMode {

    private final XDrive drive = new XDrive();

    @Override
    public void init() {
        drive.init(hardwareMap);

        telemetry.addLine("X-Drive ready. Press START.");
        telemetry.addLine("A = toggle mode,  OPTIONS = reset heading.");
        telemetry.update();
    }

    @Override
    public void loop() {
        drive.drive(gamepad1, telemetry);
    }
}
