package orionsmod.content;

import arc.Core;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.BuilderAI;
import mindustry.content.UnitTypes;
import mindustry.type.UnitType;

public final class OMUnits {
    public static UnitType flea;
    public static UnitType fleaBuilder;
    public static UnitType fleaMiner;
    public static UnitType fleaRepairer;

    private OMUnits() {
    }

    public static void load() {
        UnitType poly = UnitTypes.poly;

        flea = new UnitType("flea") {
            @Override
            public void load() {
                super.load();
                cellRegion = Core.atlas.find(name + "_cell", cellRegion);
            }
        };

        configureBaseFlea(flea, poly);
        flea.defaultCommand = UnitCommand.assistCommand;

        fleaBuilder = new UnitType("flea_builder") {
            @Override
            public void load() {
                super.load();
                region = Core.atlas.find("flea", region);
                cellRegion = Core.atlas.find("flea_cell", cellRegion);
                fullIcon = uiIcon = Core.atlas.find("flea", fullIcon);
            }
        };
        configureBaseFlea(fleaBuilder, poly);
        fleaBuilder.buildSpeed = flea.buildSpeed * 1.32f;
        fleaBuilder.mineSpeed = flea.mineSpeed * 0.86f;
        fleaBuilder.health = flea.health * 1.08f;
        fleaBuilder.itemCapacity = flea.itemCapacity + 8;
        fleaBuilder.defaultCommand = UnitCommand.rebuildCommand;

        fleaMiner = new UnitType("flea_miner") {
            @Override
            public void load() {
                super.load();
                region = Core.atlas.find("flea", region);
                cellRegion = Core.atlas.find("flea_cell", cellRegion);
                fullIcon = uiIcon = Core.atlas.find("flea", fullIcon);
            }
        };
        configureBaseFlea(fleaMiner, poly);
        fleaMiner.mineSpeed = flea.mineSpeed * 1.42f;
        fleaMiner.mineRange = flea.mineRange + 10f;
        fleaMiner.buildSpeed = flea.buildSpeed * 0.8f;
        fleaMiner.itemCapacity = flea.itemCapacity + 20;
        fleaMiner.defaultCommand = UnitCommand.mineCommand;

        fleaRepairer = new UnitType("flea_repairer") {
            @Override
            public void load() {
                super.load();
                region = Core.atlas.find("flea", region);
                cellRegion = Core.atlas.find("flea_cell", cellRegion);
                fullIcon = uiIcon = Core.atlas.find("flea", fullIcon);
            }
        };
        configureBaseFlea(fleaRepairer, poly);
        fleaRepairer.health = flea.health * 1.3f;
        fleaRepairer.speed = flea.speed * 1.06f;
        fleaRepairer.buildSpeed = flea.buildSpeed * 1.1f;
        fleaRepairer.mineSpeed = flea.mineSpeed * 0.72f;
        fleaRepairer.itemCapacity = flea.itemCapacity + 4;
        fleaRepairer.defaultCommand = UnitCommand.repairCommand;
    }

    private static void configureBaseFlea(UnitType flea, UnitType poly) {
        flea.constructor = poly.constructor;
        flea.controller = poly.controller;
        flea.aiController = BuilderAI::new;

        flea.flying = true;
        flea.lowAltitude = poly.lowAltitude;
        flea.speed = poly.speed * 1.18f;
        flea.boostMultiplier = poly.boostMultiplier;
        flea.rotateSpeed = poly.rotateSpeed * 1.08f;
        flea.baseRotateSpeed = poly.baseRotateSpeed;
        flea.drag = poly.drag;
        flea.accel = poly.accel;
        flea.hitSize = poly.hitSize;
        flea.health = poly.health * 1.35f;
        flea.armor = poly.armor;
        flea.engineOffset = poly.engineOffset;
        flea.engineSize = poly.engineSize;
        flea.engineLayer = poly.engineLayer;
        flea.shadowElevation = poly.shadowElevation;
        flea.shadowElevationScl = poly.shadowElevationScl;
        flea.outlineColor.set(poly.outlineColor);

        flea.itemCapacity = poly.itemCapacity + 20;
        flea.mineTier = Math.max(poly.mineTier, 2);
        flea.mineSpeed = poly.mineSpeed * 1.45f;
        flea.mineRange = poly.mineRange + 8f;
        flea.buildSpeed = poly.buildSpeed * 1.55f;
        flea.buildRange = poly.buildRange + 18f;
        flea.buildBeamOffset = poly.buildBeamOffset;

        flea.canHeal = true;
        flea.canAttack = false;
        flea.targetAir = false;
        flea.targetGround = false;
        flea.faceTarget = false;
        flea.rotateToBuilding = true;
        flea.drawCell = true;
        flea.drawItems = true;
        flea.researchCostMultiplier = 1.08f;
        flea.ammoType = poly.ammoType;

        flea.envRequired = poly.envRequired;
        flea.envEnabled = poly.envEnabled;
        flea.envDisabled = poly.envDisabled;

        flea.abilities.addAll(poly.abilities);
        flea.weapons.addAll(poly.weapons);
        flea.commands = new UnitCommand[]{
            UnitCommand.assistCommand,
            UnitCommand.rebuildCommand,
            UnitCommand.repairCommand,
            UnitCommand.mineCommand,
            UnitCommand.moveCommand
        };
    }
}
