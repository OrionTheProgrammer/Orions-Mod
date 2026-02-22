# Contributing

## Requirements
- JDK 17+
- Mindustry target: `v146`
- Gradle Wrapper included in this repository

## Build
- Windows: `gradlew.bat clean jar`
- Linux/macOS: `./gradlew clean jar`

Output: `build/libs/orions-mod.jar`

## Style
- Keep compatibility with Mindustry `v146` APIs.
- Avoid direct access to protected/internal fields from Mindustry classes.
- Keep localization keys in both:
  - `bundles/bundle.properties`
  - `bundles/bundle_es.properties`

## Pull Request checklist
1. Build passes locally.
2. No crash on startup in Mindustry Steam build 146.
3. New items/blocks have EN + ES names and descriptions.
4. Sprite naming matches atlas region names used in code.
5. Changelog updated when behavior changes.
