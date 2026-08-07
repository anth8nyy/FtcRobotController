# GNRT-DriveCore — X-Drive

Driving software for the **X-Drive** drivetrain, built for the FTC SDK (Android Studio / Java) and
following the **FGC Team Greece Coding Standard**.

The robot moves omnidirectionally (forward/back, strafe, rotate — simultaneously) using four omni
wheels mounted at 45°. The `XDrive` subsystem supports **both** control modes, and a single teleop
switches between them live with the **A** button:

- **Field-centric** — "forward" is a fixed field direction (away from the driver), using the Control
  Hub IMU heading.
- **Robot-centric** — "forward" is where the robot's nose points.

## Layout

Laid out to match the GNRT module: OpMode at the package root, subsystem in `Subsystems/`, shared
helpers and configuration in `Utils/`.

```
TeamCode/src/main/java/org/firstinspires/ftc/teamcode/anth8nyy/FieldCentricXdrive/
├── XDriveFieldCentricTeleOp.java  # the teleop (OpMode) — "X-Drive TeleOp"
├── Subsystems/
│   └── XDrive.java                # X-Drive kinematics + motor control, init(HardwareMap)
└── Utils/
    ├── Config.java                # enum motor names + directions, speeds, deadzone, IMU name
    └── DriveMath.java             # deadzone + max-abs helpers
```

The module is self-contained: drop the `anth8nyy/FieldCentricXdrive/` tree into the `TeamCode` module
alongside `GNRT` and it compiles on its own.

## Controls (gamepad 1)

| Input | Action |
| --- | --- |
| Left stick Y | Drive forward / backward |
| Left stick X | Strafe left / right |
| Right stick X | Rotate |
| A | Toggle robot-centric / field-centric |
| Options | Reset field heading to current facing |
| Right bumper (hold) | Full speed (otherwise normal speed) |

Normal speed is `0.6` (`NORMAL_SPEED`); holding the right bumper opens it up to `1.0` (`MAX_SPEED`).
The teleop starts in **field-centric**.

## Docs

- **Design & kinematics:** [`docs/FieldCentric-XDrive.md`](docs/FieldCentric-XDrive.md)
- **Real-robot bring-up checklist:** design doc §4
- **PR template:** [`docs/PULL_REQUEST.md`](docs/PULL_REQUEST.md)

## Dependencies

- FTC Robot Controller SDK v11.2.0

No external libraries. Tunable values live in the `Config` enums (`Utils/Config.java`); edit them
there and redeploy.

The drive motors use `RUN_USING_ENCODER`, so each one needs its encoder cable connected to move.
