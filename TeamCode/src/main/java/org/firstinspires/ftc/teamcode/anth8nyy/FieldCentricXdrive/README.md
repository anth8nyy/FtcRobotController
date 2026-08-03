# GNRT-DriveCore — Field-Centric X-Drive

Initial driving software for the **X-Drive** drivetrain, built for the FTC SDK (Android Studio /
Java) and following the **FGC Team Greece Coding Standard**.

The robot moves omnidirectionally (forward/back, strafe, rotate — simultaneously) using four omni
wheels mounted at 45°. The `XDrive` subsystem supports **both** control modes and ships with a
teleop for each:

- **Field-centric** (primary deliverable) — "forward" is a fixed field direction (away from the
  driver), using the Control Hub IMU heading.
- **Robot-centric** (base mode) — "forward" is where the robot's nose points; the underlying
  kinematics that field-centric is built on.

## Layout

Laid out to match the GNRT module: OpModes at the package root, subsystems in `Subsystems/`,
shared helpers and configuration in `Utils/`.

```
TeamCode/src/main/java/org/firstinspires/ftc/teamcode/anth8nyy/FieldCentricXdrive/
├── XDriveTeleOp.java              # robot-centric teleop (OpMode)
├── XDriveFieldCentricTeleOp.java  # field-centric teleop (OpMode, IMU)
├── Subsystems/
│   └── XDrive.java                # X-Drive kinematics + motor control, init(HardwareMap)
└── Utils/
    ├── Config.java                # enum hardware names + directions, tunable values, hub orientation
    └── DriveMath.java             # deadzone, normalisation, clip helpers
```

The module is self-contained: drop the `anth8nyy/FieldCentricXdrive/` tree into the `TeamCode` module
alongside `GNRT` and it compiles on its own.

## Controls (gamepad 1)

| Input | Robot-centric | Field-centric |
| --- | --- | --- |
| Left stick Y | Drive forward / backward | Field forward / backward |
| Left stick X | Strafe left / right | Field strafe left / right |
| Right stick X | Rotate | Rotate |
| Options | — | Reset field heading to current facing |
| Right bumper (hold) | Slow / precision mode | Slow / precision mode |

## Docs

- **Design & kinematics:** [`docs/FieldCentric-XDrive.md`](docs/FieldCentric-XDrive.md)
- **Real-robot bring-up checklist:** design doc §4
- **PR template:** [`docs/PULL_REQUEST.md`](docs/PULL_REQUEST.md)

## Dependencies

- FTC Robot Controller SDK v11.2.0

No external libraries. Tunable values live in the `Config` enums (`Utils/Config.java`); edit them
there and redeploy.
