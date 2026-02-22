# Steam Workshop Publishing Guide (Mindustry)

## 1. Build package
1. Build jar:
   - `gradlew.bat clean jar`
2. Output file:
   - `build/libs/orions-mod.jar`

## 2. Import in game
1. Open Mindustry.
2. Go to `Mods` -> `Import Mod` -> `Import File`.
3. Select `build/libs/orions-mod.jar`.
4. Restart game if requested.

## 3. Publish to Workshop
1. Open `Mods`.
2. Select `Orion's Mod`.
3. Click `Publish`.
4. Use:
   - Preview image: `preview.png`
   - Short description from `steam/workshop-description-es.txt`
   - Changelog from `steam/workshop-changelog-es.txt`

## 4. Metadata checklist
1. `mod.hjson` has correct `version`.
2. `minGameVersion` matches target branch (`146`).
3. Description reflects latest gameplay.
