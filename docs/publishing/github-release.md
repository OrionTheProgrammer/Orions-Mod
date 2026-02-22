# GitHub Release Guide

## 1. Pre-release validation
1. Update `mod.hjson` version.
2. Update `CHANGELOG.md`.
3. Run build:
   - `gradlew.bat clean jar` (Windows)
   - `./gradlew clean jar` (Linux/macOS)
4. Confirm output exists: `build/libs/orions-mod.jar`.

## 2. Tag and release
1. Create git tag (example): `v0.1.0`.
2. Create GitHub Release with the same tag.
3. Attach file: `build/libs/orions-mod.jar`.
4. Copy key notes from `CHANGELOG.md`.

## 3. Recommended release body
- Target Mindustry version (`v146`).
- Main gameplay additions.
- Known limitations (if any).
