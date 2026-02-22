# Orion's Mod

Arcane expansion mod for Mindustry focused on tech progression, factory automation, and Mk upgrades for custom and vanilla blocks.

## Project Status

- Mod type: Java content mod
- Game target: Mindustry Steam build `v146`
- Mod id: `orions-mod`
- Current version: `0.1.0`
- Languages: English and Spanish

## Main Features

- New resource chain: `solid_magic -> magic_copper_ingot`
- New blocks:
  - `magic_receptor`
  - `arcane_refinery`
  - `magic_copper_drill`
  - `magic_copper_inserter`
  - `magic_copper_block`
  - `magic_trio`
- Mk progression system: `Mk2 -> Mk5 -> Mk6 Arcano`
- Upgrade lines for selected vanilla blocks
- Arcane refinery cluster behavior:
  - shared liquids and inputs
  - shared output routing
  - optional master recipe control

## Build

Requirements:
- JDK 17+

Commands:
- Windows: `gradlew.bat clean jar`
- Linux/macOS: `./gradlew clean jar`

Output:
- `build/libs/orions-mod.jar`

## Install in Mindustry (Steam)

1. Open Mindustry
2. Go to `Mods -> Import Mod -> Import File`
3. Select `build/libs/orions-mod.jar`
4. Restart game if requested

## Publishing

- GitHub release guide: `docs/publishing/github-release.md`
- Steam Workshop guide: `docs/publishing/steam-workshop.md`
- Steam text templates:
  - `steam/workshop-description-es.txt`
  - `steam/workshop-changelog-es.txt`

## Repository Layout

- `src/orionsmod/`: Java source
- `sprites/`: mod textures
- `bundles/`: localization files
- `docs/`: technical and publishing docs
- `docs/references/v146/`: local API reference files used during development
- `.github/workflows/build.yml`: CI build pipeline
- `mod.hjson`: Mindustry mod metadata

## Versioning and Metadata

- Changelog: `CHANGELOG.md`
- License: `LICENSE`
- Contribution rules: `CONTRIBUTING.md`

## Notes

If you report a bug, include:
1. Mindustry build version
2. Mod version
3. Reproduction steps
4. Full crash log (if available)
