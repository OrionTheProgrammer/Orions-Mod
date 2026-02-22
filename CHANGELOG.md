# Changelog

All notable changes to this project are documented in this file.

## [0.1.0] - 2026-02-21

### Added
- Arcane progression chain: `solid_magic` -> `magic_copper_ingot`.
- New blocks:
  - `magic_receptor`
  - `arcane_refinery`
  - `magic_copper_drill`
  - `magic_copper_inserter`
  - `magic_copper_block`
  - `magic_trio`
- Arcane refinery network behavior:
  - Shared liquids between connected refineries.
  - Shared inputs/outputs across connected clusters.
  - Optional master mode to enforce cluster-wide recipe control.
- Mk upgrade lines (Mk2-Mk5 + Mk6 Arcano) for custom and selected vanilla content.
- Bilingual localization (`bundle.properties`, `bundle_es.properties`) with numeric upgrade stats.

### Improved
- Steam v146 compatibility and runtime stability.
- README and publishing documentation for GitHub and Steam workflows.
