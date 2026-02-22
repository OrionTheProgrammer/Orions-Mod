# Orion's Mod

Expansión arcana para Mindustry enfocada en progresión tecnológica, automatización de producción y mejoras Mk para bloques propios y vanilla.

## Resumen

- Tipo de mod: Java content mod (bloques, ítems, árbol tecnológico, mejoras).
- Versión objetivo de Mindustry: `v146` (Steam build estable).
- Nombre técnico: `orions-mod`.
- Versión actual del mod: `0.1.0`.
- Idiomas: español e inglés (`bundles/bundle_es.properties`, `bundles/bundle.properties`).

## Qué añade el mod

- Nueva cadena de recursos: `Magia Sólida -> Cobre Mágico`.
- Bloques nuevos:
- `magic_receptor` (recolección ambiental de magia, bonus por adyacencia).
- `arcane_refinery` (recetas seleccionables, trabajo en red, modo maestro opcional).
- `magic_copper_drill` (taladro arcano con gran escalado por refrigerante).
- `magic_copper_inserter` (inserción dirigida sin contacto directo de cinta).
- `magic_copper_block` (muro con escudo azul regenerativo).
- `magic_trio` (torreta dual energética con sobrecalentamiento).
- Sistema de mejoras `Mk2 -> Mk5 -> Mk6 Arcano`.
- Mejoras aplicadas también a bloques vanilla (puentes, conductos puente, cintas, torretas y producción).

## Mecánicas destacadas

- Refinerías Arcanas conectadas comparten insumos, líquidos y salida.
- Modo maestro de refinería para controlar receta de todo el clúster.
- Estadísticas de potenciación visibles en descripciones de mejoras.
- Integración arcana de munición para torretas seleccionadas.

## Requisitos

- Java JDK 17 o superior (para compilar).
- Mindustry Steam `v146` (para jugar con este build).

## Compilar

1. Clona este repositorio.
2. Abre una terminal en la carpeta del proyecto.
3. Ejecuta:
4. Windows: `gradlew.bat clean jar`
5. Linux/macOS: `./gradlew clean jar`
6. El archivo generado quedará en `build/libs/orions-mod.jar`.

## Instalar en Mindustry (Steam)

1. Abre Mindustry.
2. Ve a `Mods -> Import Mod -> Import File`.
3. Selecciona `build/libs/orions-mod.jar`.
4. Reinicia el juego cuando lo solicite.

## Configuración de versión de juego

- Por defecto el proyecto compila contra `v146`.
- Si necesitas compilar para otra rama, usa `-PmindustryVersion=<version>`.
- Ejemplo: `gradlew.bat clean jar -PmindustryVersion=v155.4`.
- Si cambias de rama, recuerda ajustar también `minGameVersion` en `mod.hjson`.

## Estructura del proyecto

- `src/orionsmod/OrionsMod.java`: entrada principal del mod.
- `src/orionsmod/content/`: carga de ítems, bloques y árbol tecnológico.
- `src/orionsmod/world/`: lógica personalizada de bloques.
- `sprites/`: atlas de sprites del mod.
- `bundles/`: localización EN/ES.
- `mod.hjson`: metadatos del mod para Mindustry.

## Estado

- Build local validado: `BUILD SUCCESSFUL` con Gradle Wrapper.
- JAR empaquetado con código, sprites, bundles y metadatos.

## Nota

Si encuentras un bug, incluye en el reporte:

1. Build de Mindustry.
2. Versión del mod.
3. Pasos para reproducir.
4. Crash log completo (si existe).
