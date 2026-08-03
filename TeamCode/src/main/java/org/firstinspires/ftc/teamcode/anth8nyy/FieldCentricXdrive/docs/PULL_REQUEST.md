# Pull Request — Add Field-Centric X-Drive Drive System

> Copy this into the PR description when opening the PR against `main` in **GNRT-DriveCore**.
> Branch: `feature/xdrive-field-centric` → `main`.

## Title

```
Add Field-Centric X-Drive drive system (subsystem, config, TeleOps)
```

## Summary

Implements the initial field-centric driving software for the X-Drive drivetrain (with the
robot-centric mixing as its base mode), following the FGC Team Greece coding standard and the layout
of the sibling GNRT module (`Subsystems/` + `Utils/`, OpModes at the package root).

- `Subsystems/XDrive` — X-Drive kinematics + motor control (robot-centric and field-centric), `init(HardwareMap)`
- `Utils/Config` — enum hardware names + directions, tunable values, IMU name and hub orientation
- `Utils/DriveMath` — deadzone, normalisation and clip helpers
- `XDriveTeleOp` — robot-centric gamepad-1 teleop (`OpMode`)
- `XDriveFieldCentricTeleOp` — field-centric gamepad-1 teleop (`OpMode`, IMU)

Full design notes: `docs/FieldCentric-XDrive.md`.

## Acceptance criteria

- [x] Field-centric X-Drive implemented (ticket title) — IMU heading + reset-heading control
- [x] Robot-centric X-Drive implemented (base mode / body criterion #1)
- [x] Correct 4-motor kinematics (mixing + normalisation, derivation documented)
- [x] Follows the GNRT module architecture (package layout, enum `Config`, `init(HardwareMap)`)
- [x] Uses team interfaces / utilities / coding standard
- [x] Clean, modular, documented code
- [ ] **Tested on the real robot** — run the bring-up checklist (`docs/FieldCentric-XDrive.md` §4)
      and paste the results here before requesting review
- [ ] Code review approved before merge

## Test evidence (fill in)

- Motor directions verified: …
- Forward / strafe / turn behave correctly: …
- Slow mode + telemetry verified: …

## Reviewer notes

- Confirm the `Subsystem` interface choice, or point me at the base-repo equivalent (see design doc §5).
- Confirm the `fldm/frdm/bldm/brdm` hardware IDs match our Driver Station configuration.
