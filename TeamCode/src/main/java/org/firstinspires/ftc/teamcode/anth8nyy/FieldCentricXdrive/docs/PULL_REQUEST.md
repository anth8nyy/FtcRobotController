# Pull Request — Add X-Drive Drive System

> Copy this into the PR description when opening the PR against `main` in **GNRT-DriveCore**.
> Branch: `feature/xdrive-field-centric` → `main`.

## Title

```
Add X-Drive drive system (subsystem, config, toggle TeleOp)
```

## Summary

Implements the driving software for the X-Drive drivetrain — robot-centric and field-centric, with a
single teleop that toggles between them on the **A** button — following the FGC Team Greece coding
standard and the layout of the sibling GNRT module (`Subsystems/` + `Utils/`, OpMode at the package
root).

- `Subsystems/XDrive` — X-Drive kinematics + motor control (robot-centric and field-centric), the
  `drive(gamepad, telemetry)` driver entry point, `init(HardwareMap)`
- `Utils/Config` — enum motor names + directions, tunable values (`NORMAL_SPEED`, `MAX_SPEED`,
  `INPUT_DEADZONE`), IMU name
- `Utils/DriveMath` — `applyDeadzone` + `maxAbs` helpers
- `XDriveFieldCentricTeleOp` — the gamepad-1 teleop ("X-Drive TeleOp"); **A** toggles the mode

Full design notes: `docs/FieldCentric-XDrive.md`.

## Acceptance criteria

- [x] Field-centric X-Drive implemented — IMU heading + reset-heading control
- [x] Robot-centric X-Drive implemented (base mode) with A-button toggle
- [x] Correct 4-motor kinematics (mixing + normalisation, derivation documented)
- [x] Follows the GNRT module architecture (package layout, enum `Config`, `init(HardwareMap)`)
- [x] Clean, modular, documented code
- [ ] **Tested on the real robot** — run the bring-up checklist (`docs/FieldCentric-XDrive.md` §4)
      and paste the results here before requesting review
- [ ] Code review approved before merge

## Test evidence (fill in)

- Motor directions verified: …
- Forward / strafe / rotate behave correctly: …
- A-toggle (robot ↔ field-centric), boost (right bumper), and telemetry verified: …

## Reviewer notes

- Confirm the `frontLeftDrive` / `frontRightDrive` / `backLeftDrive` / `backRightDrive` hardware names
  match our Driver Station configuration.
- Confirm the hub orientation in `XDrive.init` (`UP` / `LEFT`) matches our Control Hub mounting.
- Drive motors use `RUN_USING_ENCODER` — confirm all four encoders are wired.
