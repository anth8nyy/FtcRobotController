# Field-Centric Drive System for X-Drive — Design & Bring-up

Initial driving software for the **X-Drive** drivetrain in **GNRT-DriveCore**, written for the FTC
SDK (Android Studio / Java) and following the **FGC Team Greece Coding Standard**.

## 1. What was built

Structured like the GNRT module: OpModes at the package root, subsystems in `Subsystems/`, shared
helpers and configuration in `Utils/`.

| Package | Class | Responsibility |
| --- | --- | --- |
| `Utils` | `Config` | Enum hardware names + directions (`Motors`), tunable values (`DriveValues`), IMU name and hub orientation. |
| `Utils` | `DriveMath` | Stateless helpers: `applyDeadzone`, `maxAbs`, `clip`. |
| `Subsystems` | `XDrive` | X-Drive kinematics + motor control, both robot-centric and field-centric. `init(HardwareMap)`. |
| *(root)* | `XDriveTeleOp` | Thin robot-relative teleop (`OpMode`) → `XDrive.drive`. |
| *(root)* | `XDriveFieldCentricTeleOp` | Thin field-relative teleop (`OpMode`) → `XDrive.driveFieldRelative`. |

All code uses the team conventions: PascalCase classes, camelCase variables,
`CONSTANT_SNAKE_CASE` constants, K&R braces, 4-space indentation, lines ≤ 150 chars.

## 2. X-Drive kinematics

An X-Drive has four omni wheels at 45° at the corners of the chassis, forming an "X". Because each
wheel's rollers slip sideways, the chassis is **holonomic**: it can translate in any direction and
rotate at the same time.

**Robot-centric** control: the inputs are in the robot's own frame — "forward" is always where the
nose points, regardless of field heading. No IMU is needed. (Field-centric would first rotate the
input vector by the robot's heading; see §6.)

Inputs (each in `[-1, 1]`):

- `forward` (axial) — `+` toward the nose
- `strafe` (lateral) — `+` to the right
- `turn` (yaw) — `+` clockwise (viewed from above)

Wheel-power mixing:

```
frontLeft  = forward + strafe + turn
frontRight = forward - strafe - turn
backLeft   = forward - strafe + turn
backRight  = forward + strafe - turn
```

Sanity check of the pure motions:

| Command | FL | FR | BL | BR | Result |
| --- | --- | --- | --- | --- | --- |
| forward = 1 | +1 | +1 | +1 | +1 | drives straight forward |
| strafe = 1  | +1 | −1 | −1 | +1 | translates right |
| turn = 1    | +1 | −1 | +1 | −1 | rotates clockwise in place |

**Normalisation.** If any `|power| > 1`, all four powers are divided by the largest magnitude
(`DriveMath.maxAbs`). This clamps every motor into `[-1, 1]` **while keeping their ratios**, so the
commanded direction is never distorted — only the overall speed is scaled down.

**Derivation note.** Projecting the chassis velocity onto each wheel's 45° drive axis produces the
same four terms up to a common `√2` factor and a per-motor sign. The sign is handled physically by
reversing the appropriate motors (`configureMotors()`), and the `√2` cancels in the normalisation
for a symmetric chassis — which is why the standard holonomic mixing above is exact here.

### Field-centric layer

Robot-relative is the core (`drive`). Field-relative (`driveFieldRelative`) adds one step **before**
the mixing: it rotates the driver's `(forward, right)` vector — given in the field frame (forward =
away from the driver) — by `-heading`, where `heading` is the IMU yaw. Following the FTC sample, this
is done in polar form: turn the vector into an angle + magnitude, subtract the heading, convert back:

```
theta      = atan2(forward, right)
r          = hypot(right, forward)
theta      = normalizeRadians(theta - heading)
newForward = r * sin(theta)
newRight   = r * cos(theta)
```

The `rotate` term is always robot-relative and passes through unchanged. `newForward`/`newRight` then
feed the exact same four-motor mixing above, so there is a single source of kinematics truth.

`resetHeading()` (bound to **Options** in the field-centric teleop) zeroes the IMU yaw, making the
robot's current facing the new "field forward". The hub-mounting orientation
(`Config.HUB_LOGO_DIRECTION` / `Config.HUB_USB_DIRECTION`) must match the physical Control Hub mounting
or the heading — and therefore field-centric driving — will be wrong.

## 3. Design decisions

- **Subsystem / OpMode split.** `XDrive` owns all kinematics and hardware; `XDriveTeleOp` only reads
  the gamepad. This keeps the drivetrain reusable from autonomous OpModes and unit-testable.
- **`init(HardwareMap)` subsystem.** Like the GNRT subsystems, `XDrive` is created with a no-arg
  constructor and wired up in `init()` so the OpMode controls when hardware is grabbed.
- **`DriveMath` utility.** Deadzone, max-abs normalisation and clipping live in `Utils`, hardware-free
  and reusable by any future holonomic drive.
- **Enum-based `Config`.** Hardware names, motor directions and tunable numbers live in the `Config`
  enums (matching GNRT). Change a value there and redeploy — no external dashboard dependency.
- **Defensive clipping.** `setMotorPowers` clips to `[-1, 1]` so any caller (autonomous, tests) is
  safe.

## 4. Bring-up checklist (real robot)

Acceptance criterion #6 requires a test on the real robot. Do this before merging:

1. In the Driver Station config, name the four motors exactly `fldm`, `frdm`, `bldm`, `brdm`.
2. Deploy from Android Studio and select **X-Drive TeleOp (Robot-Centric)** on gamepad 1.
3. **Motor direction test** — with the robot on blocks (wheels off the ground):
   - Push the left stick fully **up**. All four wheels should drive the robot **forward**. Any wheel
     spinning the wrong way → flip its `setDirection` in `XDrive.configureMotors()`.
4. **Strafe test** — push the left stick **right**; the robot should translate right, not rotate.
5. **Turn test** — push the right stick **right**; the robot should rotate clockwise in place.
6. Confirm **slow mode** (hold right bumper) reduces speed, and that telemetry shows the four powers.

Then verify **field-centric** with **X-Drive TeleOp (Field-Centric)**:

7. Set `HUB_LOGO_DIRECTION` / `HUB_USB_DIRECTION` in `Config` to match how the Control Hub is
   mounted. Point the robot away from you and press **Options** to zero the heading.
8. With the robot pointed away, push the left stick up → robot drives away from you (same as
   robot-centric at heading 0). The telemetry **Heading** should read ~0°.
9. Rotate the robot 90° by hand (or with the right stick), then push the left stick up again — the
   robot should still drive **away from you**, not along its new nose direction. If it drives the
   wrong way, the hub orientation constants are wrong; correct them and re-test.

Record the results in the PR description.

## 5. Integrating with the Base Repository

This module mirrors the layout and conventions of the sibling **GNRT** module (`Subsystems/` +
`Utils/`, OpModes at the package root, `init(HardwareMap)` subsystems, enum-based `Config`). One
thing to reconcile against the actual Greek National Team Base Repository before merge:

- **Shared utilities** — if the base repo already provides deadzone/normalise helpers or a gamepad
  wrapper, replace the calls into `DriveMath` (or move `DriveMath` under the shared `Utils` package).

Everything else (package layout, enum `Config`, naming, formatting) already matches the GNRT module.

## 6. Extensibility

- **Field-relative drive** — implemented (`driveFieldRelative` + `XDriveFieldCentricTeleOp`), built on
  top of the robot-relative mixing so the kinematics stay in one place.
- **Closed-loop speed** — swap the `DcMotor` handles for `DcMotorEx` and switch to `RUN_USING_ENCODER`
  to add per-wheel velocity PID, without changing either OpMode.
- **Autonomous** — call `drive` / `driveFieldRelative` / `setMotorPowers` from any OpMode; no gamepad
  coupling.
