package orionsmod.content;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.type.Item;

public final class OMItems {
    public static Item solidMagic;
    public static Item magicCopperIngot;
    public static Item arcaneDust;
    public static Item arcaneCristal;
    public static Item arcaneSurgeAlloy;
    public static Item bridgeRangeUpgrade1;
    public static Item bridgeRangeUpgrade2;
    public static Item bridgeRangeUpgrade3;
    public static Item bridgeRangeUpgrade4;
    public static Item conduitBridgeRangeUpgrade1;
    public static Item conduitBridgeRangeUpgrade2;
    public static Item conduitBridgeRangeUpgrade3;
    public static Item conduitBridgeRangeUpgrade4;
    public static Item conveyorSpeedUpgrade1;
    public static Item conveyorSpeedUpgrade2;
    public static Item conveyorSpeedUpgrade3;
    public static Item conveyorSpeedUpgrade4;
    public static Item arcaneAmmoUpgrade;
    public static Item arcaneRefineryUpgradeMk2;
    public static Item arcaneRefineryUpgradeMk3;
    public static Item arcaneRefineryUpgradeMk4;
    public static Item arcaneRefineryUpgradeMk5;
    public static Item arcaneRefineryArcanoUpgrade;
    public static Item duoUpgradeMk2;
    public static Item duoUpgradeMk3;
    public static Item duoUpgradeMk4;
    public static Item duoUpgradeMk5;
    public static Item arcUpgradeMk2;
    public static Item arcUpgradeMk3;
    public static Item arcUpgradeMk4;
    public static Item arcUpgradeMk5;
    public static Item arcArcanoUpgrade;
    public static Item magicWallUpgradeMk2;
    public static Item magicWallUpgradeMk3;
    public static Item magicWallUpgradeMk4;
    public static Item magicWallUpgradeMk5;
    public static Item magicWallArcanoUpgrade;
    public static Item magicDrillUpgradeMk2;
    public static Item magicDrillUpgradeMk3;
    public static Item magicDrillUpgradeMk4;
    public static Item magicDrillUpgradeMk5;
    public static Item magicDrillArcanoUpgrade;
    public static Item scatterArcanoUpgrade;
    public static Item salvoArcanoUpgrade;
    public static Item mechanicalDrillArcanoUpgrade;
    public static Item graphitePressArcanoUpgrade;
    public static Item fleaUpgradeMk2;
    public static Item fleaUpgradeMk3;
    public static Item fleaUpgradeMk4;
    public static Item fleaUpgradeMk5;
    public static Item fleaArcanoUpgrade;

    private OMItems() {
    }

    public static void load() {
        solidMagic = new Item("solid_magic", Color.valueOf("7fd4ff")) {{
            cost = 1.05f;
            hardness = 1;
        }};

        magicCopperIngot = new Item("magic_copper_ingot", Color.valueOf("f39d6b")) {{
            cost = 1.2f;
            hardness = 1;
        }};

        arcaneDust = new Item("arcane_dust", Color.valueOf("b7c8ff")) {{
            cost = 1.45f;
            hardness = 1;
        }};

        arcaneCristal = new Item("arcane_cristal", Color.valueOf("8ce8ff")) {{
            cost = 1.85f;
            hardness = 2;
        }};

        arcaneSurgeAlloy = new Item("arcane_surge_alloy", Color.valueOf("ffd287")) {{
            cost = 2.6f;
            hardness = 3;
        }};

        bridgeRangeUpgrade1 = new Item("bridge_range_upgrade_1", Color.valueOf("9cb5d6")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.itemBridge.uiIcon;
            }
        };

        bridgeRangeUpgrade2 = new Item("bridge_range_upgrade_2", Color.valueOf("8ea8cb")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.itemBridge.uiIcon;
            }
        };

        bridgeRangeUpgrade3 = new Item("bridge_range_upgrade_3", Color.valueOf("809bc3")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.itemBridge.uiIcon;
            }
        };

        bridgeRangeUpgrade4 = new Item("bridge_range_upgrade_4", Color.valueOf("728fbc")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.itemBridge.uiIcon;
            }
        };

        conduitBridgeRangeUpgrade1 = new Item("conduit_bridge_range_upgrade_1", Color.valueOf("95c8d5")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.bridgeConduit.uiIcon;
            }
        };

        conduitBridgeRangeUpgrade2 = new Item("conduit_bridge_range_upgrade_2", Color.valueOf("84bccb")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.bridgeConduit.uiIcon;
            }
        };

        conduitBridgeRangeUpgrade3 = new Item("conduit_bridge_range_upgrade_3", Color.valueOf("73afc0")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.bridgeConduit.uiIcon;
            }
        };

        conduitBridgeRangeUpgrade4 = new Item("conduit_bridge_range_upgrade_4", Color.valueOf("639fb3")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.bridgeConduit.uiIcon;
            }
        };

        conveyorSpeedUpgrade1 = new Item("conveyor_speed_upgrade_1", Color.valueOf("f3cb7a")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.titaniumConveyor.uiIcon;
            }
        };

        conveyorSpeedUpgrade2 = new Item("conveyor_speed_upgrade_2", Color.valueOf("e9bb5f")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.titaniumConveyor.uiIcon;
            }
        };

        conveyorSpeedUpgrade3 = new Item("conveyor_speed_upgrade_3", Color.valueOf("e4ae46")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.titaniumConveyor.uiIcon;
            }
        };

        conveyorSpeedUpgrade4 = new Item("conveyor_speed_upgrade_4", Color.valueOf("dc9f2a")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.titaniumConveyor.uiIcon;
            }
        };

        arcaneAmmoUpgrade = new Item("arcane_ammo_upgrade", Color.valueOf("6fb6ff")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.duo.uiIcon;
            }
        };

        arcaneRefineryUpgradeMk2 = new Item("arcane_refinery_upgrade_mk2", Color.valueOf("8fbad2")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.graphitePress.uiIcon;
            }
        };

        arcaneRefineryUpgradeMk3 = new Item("arcane_refinery_upgrade_mk3", Color.valueOf("7ca6ca")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.graphitePress.uiIcon;
            }
        };

        arcaneRefineryUpgradeMk4 = new Item("arcane_refinery_upgrade_mk4", Color.valueOf("6f99be")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.graphitePress.uiIcon;
            }
        };

        arcaneRefineryUpgradeMk5 = new Item("arcane_refinery_upgrade_mk5", Color.valueOf("648eb5")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.graphitePress.uiIcon;
            }
        };

        arcaneRefineryArcanoUpgrade = new Item("arcane_refinery_arcano_upgrade", Color.valueOf("6bc2ff")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.graphitePress.uiIcon;
            }
        };

        duoUpgradeMk2 = new Item("duo_upgrade_mk2", Color.valueOf("9ab0d1")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.duo.uiIcon;
            }
        };

        duoUpgradeMk3 = new Item("duo_upgrade_mk3", Color.valueOf("83a2d0")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.duo.uiIcon;
            }
        };

        duoUpgradeMk4 = new Item("duo_upgrade_mk4", Color.valueOf("6d95cf")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.duo.uiIcon;
            }
        };

        duoUpgradeMk5 = new Item("duo_upgrade_mk5", Color.valueOf("5c86c4")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.duo.uiIcon;
            }
        };

        arcUpgradeMk2 = new Item("arc_upgrade_mk2", Color.valueOf("77acd6")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.arc.uiIcon;
            }
        };

        arcUpgradeMk3 = new Item("arc_upgrade_mk3", Color.valueOf("699fca")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.arc.uiIcon;
            }
        };

        arcUpgradeMk4 = new Item("arc_upgrade_mk4", Color.valueOf("5a90bd")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.arc.uiIcon;
            }
        };

        arcUpgradeMk5 = new Item("arc_upgrade_mk5", Color.valueOf("4d84b3")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.arc.uiIcon;
            }
        };

        arcArcanoUpgrade = new Item("arc_arcano_upgrade", Color.valueOf("70b8ff")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.arc.uiIcon;
            }
        };

        magicWallUpgradeMk2 = new Item("magic_wall_upgrade_mk2", Color.valueOf("78a6d5")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.shieldedWall.uiIcon;
            }
        };

        magicWallUpgradeMk3 = new Item("magic_wall_upgrade_mk3", Color.valueOf("6b9dce")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.shieldedWall.uiIcon;
            }
        };

        magicWallUpgradeMk4 = new Item("magic_wall_upgrade_mk4", Color.valueOf("5c8fc0")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.shieldedWall.uiIcon;
            }
        };

        magicWallUpgradeMk5 = new Item("magic_wall_upgrade_mk5", Color.valueOf("4f82b5")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.shieldedWall.uiIcon;
            }
        };

        magicWallArcanoUpgrade = new Item("magic_wall_arcano_upgrade", Color.valueOf("78cbff")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.shieldedWall.uiIcon;
            }
        };

        magicDrillUpgradeMk2 = new Item("magic_drill_upgrade_mk2", Color.valueOf("88acd1")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.mechanicalDrill.uiIcon;
            }
        };

        magicDrillUpgradeMk3 = new Item("magic_drill_upgrade_mk3", Color.valueOf("7b9fc4")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.mechanicalDrill.uiIcon;
            }
        };

        magicDrillUpgradeMk4 = new Item("magic_drill_upgrade_mk4", Color.valueOf("6d90b7")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.mechanicalDrill.uiIcon;
            }
        };

        magicDrillUpgradeMk5 = new Item("magic_drill_upgrade_mk5", Color.valueOf("5f83ac")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.mechanicalDrill.uiIcon;
            }
        };

        magicDrillArcanoUpgrade = new Item("magic_drill_arcano_upgrade", Color.valueOf("7fd2ff")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.mechanicalDrill.uiIcon;
            }
        };

        scatterArcanoUpgrade = new Item("scatter_arcano_upgrade", Color.valueOf("6bc1d8")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.scatter.uiIcon;
            }
        };

        salvoArcanoUpgrade = new Item("salvo_arcano_upgrade", Color.valueOf("719dd7")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.salvo.uiIcon;
            }
        };

        mechanicalDrillArcanoUpgrade = new Item("mechanical_drill_arcano_upgrade", Color.valueOf("95b6d0")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.mechanicalDrill.uiIcon;
            }
        };

        graphitePressArcanoUpgrade = new Item("graphite_press_arcano_upgrade", Color.valueOf("9a9fbe")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = Blocks.graphitePress.uiIcon;
            }
        };

        fleaUpgradeMk2 = new Item("flea_upgrade_mk2", Color.valueOf("80b8de")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = UnitTypes.poly.uiIcon;
            }
        };

        fleaUpgradeMk3 = new Item("flea_upgrade_mk3", Color.valueOf("71a9d2")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = UnitTypes.poly.uiIcon;
            }
        };

        fleaUpgradeMk4 = new Item("flea_upgrade_mk4", Color.valueOf("629cc8")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = UnitTypes.poly.uiIcon;
            }
        };

        fleaUpgradeMk5 = new Item("flea_upgrade_mk5", Color.valueOf("558fbd")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = UnitTypes.poly.uiIcon;
            }
        };

        fleaArcanoUpgrade = new Item("flea_arcano_upgrade", Color.valueOf("6ecbff")) {
            @Override
            public void load() {
                super.load();
                uiIcon = fullIcon = UnitTypes.poly.uiIcon;
            }
        };
    }
}
