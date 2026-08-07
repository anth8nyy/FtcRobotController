# X-Drive Drive System — Design & Bring-up

Driving software for the **X-Drive** drivetrain in **GNRT-DriveCore**, written for the FTC SDK
(Android Studio / Java) and following the **FGC Team Greece Coding Standard**.

## 1. What was built

Structured like the GNRT module: the OpMode at the package root, the subsystem in `Subsystems/`,
shared helpers and configuration in `Utils/`.

| Package | Class | Responsibility |
| --- | --- | --- |
| `Utils` | `Config` | Enum motor names + directions (`Motors`), tunable values (`DriveValues`: speeds + deadzone), IMU name. |
| `Utils` | `DriveMath` | Stateless helpers: `applyDeadzone`, `maxAbs`. |
| `Subsystems` | `XDrive` | X-Drive kinematics + motor control (robot-centric and field-centric), the `drive(gamepad, telemetry)` driver entry point, `init(HardwareMap)`. |
| *(root)* | `XDriveFieldCentricTeleOp` | The teleop ("X-Drive TeleOp"). Its loop just calls `XDrive.drive(gamepad1, telemetry)`; **A** toggles the mode. |

All code uses the team conventions: PascalCase classes, camelCase variables,
`CONSTANT_SNAKE_CASE` constants, K&R braces, 4-space indentation, lines ≤ 150 chars.

## 2. X-Drive kinematics

An X-Drive has four omni wheels at 45° at the corners of the chassis, forming an "X". Because each
wheel's rollers slip sideways, the chassis is **holonomic**: it can translate in any direction and
rotate at the same time.

**Robot-centric** control (`driveRobotCentric`): the inputs are in the robot's own frame — "forward"
is always where the nose points, regardless of field heading. No IMU is needed. Field-centric
(`driveFieldRelative`) first rotates the input vector by the robot's heading; see the field-centric
layer below.

Inputs (each in `[-1, 1]`):

- `forward` (axial) — `+` toward the nose
- `strafe` (lateral) — `+` to the right
- `rotate` (yaw) — `+` clockwise (viewed from above)

Wheel-power mixing:

```
frontLeft  = forward + strafe + rotate
frontRight = forward - strafe - rotate
backLeft   = forward - strafe + rotate
backRight  = forward + strafe - rotate
```

Sanity check of the pure motions:

| Command | FL | FR | BL | BR | Result |
| --- | --- | --- | --- | --- | --- |
| forward = 1 | +1 | +1 | +1 | +1 | drives straight forward |
| strafe = 1  | +1 | −1 | −1 | +1 | translates right |
| rotate = 1  | +1 | −1 | +1 | −1 | rotates clockwise in place |

**Normalisation.** If any `|power| > 1`, all four powers are divided by the largest magnitude
(`DriveMath.maxAbs`). This keeps every motor within `[-1, 1]` **while keeping their ratios**, so the
commanded direction is never distorted — only the overall speed is scaled down.

**Derivation note.** Projecting the chassis velocity onto each wheel's 45° drive axis produces the
same four terms up to a common `√2` factor and a per-motor sign. The sign is handled physically by
reversing the appropriate motors (`Config.Motors` directions), and the `√2` cancels in the
normalisation for a symmetric chassis — which is why the standard holonomic mixing above is exact here.

### Field-centric layer

Robot-relative is the core (`driveRobotCentric`). Field-relative (`driveFieldRelative`) adds one step
**before** the mixing: it rotates the driver's `(forward, strafe)` vector — given in the field frame
(forward = away from the driver) — by `-heading`, where `heading` is the IMU yaw. Following the FTC
sample, this is done in polar form: turn the vector into an angle + magnitude, subtract the heading,
convert back:

```
theta      = atan2(forward, strafe)
r          = hypot(strafe, forward)
theta      = normalizeRadians(theta - heading)
newForward = r * sin(theta)
newStrafe  = r * cos(theta)
```

The `rotate` term is always robot-relative and passes through unchanged. `newForward`/`newStrafe`
then feed the exact same four-motor mixing above, so there is a single source of kinematics truth.

`resetHeading()` (bound to **Options**) zeroes the IMU yaw, making the robot's current facing the new
"field forward". The hub-mounting orientation is set in `XDrive.init`
(`RevHubOrientationOnRobot(LogoFacingDirection.UP, UsbFacingDirection.LEFT)`) and must match the
physical Control Hub mounting or the heading — and therefore field-centric driving — will be wrong.

## 3. Design decisions

- **Everything in the subsystem.** `XDrive.drive(gamepad, telemetry)` reads the gamepad, toggles the
  mode, drives, and shows telemetry — so the OpMode's whole loop is a single call. All kinematics and
  hardware live in `XDrive`, keeping it reusable from autonomous OpModes.
- **`init(HardwareMap)` subsystem.** Like the GNRT subsystems, `XDrive` is created with a no-arg
  constructor and wired up in `init()` so the OpMode controls when hardware is grabbed.
- **A-button toggle (rising edge).** A `previousA` flag makes the mode flip once per press instead of
  many times per second while A is held.
- **`DriveMath` utility.** Deadzone and max-abs normalisation live in `Utils`, hardware-free and
  reusable by any future holonomic drive.
- **Enum-based `Config`.** Motor names, directions and tunable numbers live in the `Config` enums
  (matching GNRT). Change a value there and redeploy — no external dashboard dependency.

## 4. Bring-up checklist (real robot)

Test on the real robot before merging:

1. In the Driver Station config, name the four motors exactly `frontLeftDrive`, `frontRightDrive`,
   `backLeftDrive`, `backRightDrive`, and make sure each has its encoder cable connected
   (`RUN_USING_ENCODER`).
2. Deploy from Android Studio and select **X-Drive TeleOp** on gamepad 1. Press **A** if needed so the
   telemetry shows **ROBOT-CENTRIC** for the direction test.
3. **Motor direction test** — with the robot on blocks (wheels off the ground):
   - Push the left stick fully **up**. All four wheels should drive the robot **forward**. Any wheel
     spinning the wrong way → flip that motor's `REVERSE`/`FORWARD` in `Config.Motors`.
4. **Strafe test** — push the left stick **right**; the robot should translate right, not rotate.
5. **Rotate test** — push the right stick **right**; the robot should rotate clockwise in place.
6. Confirm **boost** (hold right bumper) increases speed to `MAX_SPEED`, and that telemetry shows
   Mode / Speed / Heading.

Then verify **field-centric** (press **A** so telemetry shows FIELD-CENTRIC):

7. Confirm the hub orientation in `XDrive.init` (`UP` / `LEFT`) matches how the Control Hub is
   mounted. Point the robot away from you and press **Options** to zero the heading.
8. With the robot pointed away, push the left stick up → robot drives away from you (same as
   robot-centric at heading 0). The telemetry **Heading** should read ~0°.
9. Rotate the robot 90° by hand (or with the right stick), then push the left stick up again — the
   robot should still drive **away from you**, not along its new nose direction. If it drives the
   wrong way, the hub orientation is wrong; correct it in `XDrive.init` and re-test.

Record the results in the PR description.

## 5. Integrating with the Base Repository

This module mirrors the layout and conventions of the sibling **GNRT** module (`Subsystems/` +
`Utils/`, OpMode at the package root, `init(HardwareMap)` subsystem, enum-based `Config`). One thing
to reconcile against the actual Greek National Team Base Repository before merge:

- **Shared utilities** — if the base repo already provides deadzone/normalise helpers or a gamepad
  wrapper, replace the calls into `DriveMath` (or move `DriveMath` under the shared `Utils` package).

Everything else (package layout, enum `Config`, naming, formatting) already matches the GNRT module.

## 6. Extensibility

- **Field-relative drive** — implemented (`driveFieldRelative`), built on top of the robot-relative
  mixing so the kinematics stay in one place.
- **Closed-loop speed** — the motors already run on `RUN_USING_ENCODER`; swap the `DcMotor` handles
  for `DcMotorEx` to add per-wheel velocity PID, without changing the OpMode.
- **Autonomous** — call `driveRobotCentric` / `driveFieldRelative` / `setMotorPowers` from any OpMode;
  no gamepad coupling.
