package orionsmod.content;

import arc.Core;
import arc.graphics.Color;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.FlakBulletType;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.ShieldWall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.ItemBridge;
import mindustry.world.blocks.liquid.LiquidBridge;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;
import orionsmod.world.ArcaneRefinery;
import orionsmod.world.MagicCopperDrill;
import orionsmod.world.MagicCopperInserter;
import orionsmod.world.MagicReceptor;
import orionsmod.world.MagicTrioTurret;

import static mindustry.type.ItemStack.with;

public final class OMBlocks {
    public static Block arcaneRefinery;
    public static Block magicReceptor;
    public static Block magicCopperBlock;
    public static Block arcaneCopperWallLarge;
    public static Block magicCopperDrill;
    public static Block magicCopperInserter;
    public static Block airArcaneFactory;
    public static Block logisticReceiver;
    public static Block logisticSolicitor;
    public static Block logisticStorage;
    public static Block magicTrio;
    private static boolean vanillaDefaultsCached;
    private static boolean arcaneAmmoPatched;
    private static ConsumeItems arcMagicCopperConsume;

    private static int baseItemBridgeRange;
    private static int baseBridgeConduitRange;
    private static float baseConveyorSpeed;
    private static float baseConveyorDisplayedSpeed;
    private static float baseTitaniumConveyorSpeed;
    private static float baseTitaniumConveyorDisplayedSpeed;
    private static float baseArmoredConveyorSpeed;
    private static float baseArmoredConveyorDisplayedSpeed;
    private static float baseDuoReload;
    private static float baseDuoRange;
    private static float baseDuoInaccuracy;
    private static int baseDuoMaxAmmo;
    private static float baseArcRange;
    private static float baseArcReload;
    private static float baseArcCoolantMultiplier;
    private static boolean baseArcHasItems;
    private static int baseArcItemCapacity;
    private static float baseMechanicalDrillTime;
    private static float baseMechanicalDrillBoost;
    private static float baseGraphitePressCraftTime;
    private static int baseGraphitePressItemCapacity;
    private static float baseMagicWallShieldHealth;
    private static float baseMagicWallRegen;
    private static float baseMagicWallBreakCooldown;
    private static float baseArcaneWallLargeShieldHealth;
    private static float baseArcaneWallLargeRegen;
    private static float baseArcaneWallLargeBreakCooldown;
    private static float baseMagicDrillTime;
    private static float baseMagicDrillBoost;
    private static float baseMagicDrillRotate;
    private static float baseArcaneRefineryPowerUse;
    private static float baseAirArcaneFactoryPowerUse;
    private static float baseLogisticReceiverSpeed;
    private static int baseLogisticReceiverCapacity;
    private static float baseLogisticSolicitorSpeed;
    private static int baseLogisticStorageCapacity;
    private static final ObjectMap<UnitType, UnitSnapshot> fleaBaseStats = new ObjectMap<>();
    private static final ObjectMap<UnitType, String> fleaBaseNames = new ObjectMap<>();
    private static int baseAirArcaneFactoryItemCapacity;
    private static UnitFactory.UnitPlan airArcaneFactoryFleaPlan;
    private static UnitFactory.UnitPlan airArcaneFactoryFleaBuilderPlan;
    private static UnitFactory.UnitPlan airArcaneFactoryFleaMinerPlan;
    private static UnitFactory.UnitPlan airArcaneFactoryFleaRepairerPlan;
    private static float baseAirArcaneFactoryFleaPlanTime;
    private static float baseAirArcaneFactoryFleaBuilderPlanTime;
    private static float baseAirArcaneFactoryFleaMinerPlanTime;
    private static float baseAirArcaneFactoryFleaRepairerPlanTime;
    private static ItemStack[] arcaneRefineryReqBase;
    private static ItemStack[] arcaneRefineryReqMk2;
    private static ItemStack[] arcaneRefineryReqMk3;
    private static ItemStack[] arcaneRefineryReqMk4;
    private static ItemStack[] arcaneRefineryReqMk5;
    private static ItemStack[] arcaneRefineryReqMk6;
    private static final ObjectMap<Block, String> baseNames = new ObjectMap<>();
    private static final ObjectMap<BulletType, BulletSnapshot> bulletBases = new ObjectMap<>();

    private OMBlocks() {
    }

    public static void load() {
        arcaneRefineryReqBase = with(Items.copper, 70, Items.lead, 50, Items.graphite, 20);
        arcaneRefineryReqMk2 = with(Items.copper, 130, Items.lead, 100, Items.graphite, 70, Items.silicon, 35);
        arcaneRefineryReqMk3 = with(Items.copper, 170, Items.lead, 130, Items.graphite, 95, Items.silicon, 60, Items.titanium, 40);
        arcaneRefineryReqMk4 = with(Items.copper, 210, Items.lead, 170, Items.graphite, 130, Items.silicon, 90, Items.titanium, 75, Items.metaglass, 60);
        arcaneRefineryReqMk5 = with(Items.copper, 250, Items.lead, 210, Items.graphite, 170, Items.silicon, 120, Items.titanium, 120, Items.thorium, 65);
        arcaneRefineryReqMk6 = with(Items.copper, 320, Items.lead, 260, Items.graphite, 230, Items.silicon, 170, Items.titanium, 170, Items.thorium, 120, Items.plastanium, 70);

        magicReceptor = new MagicReceptor("magic_receptor") {{
            requirements(Category.production, with(Items.copper, 75, Items.lead, 60, Items.graphite, 30));
            size = 3;
            health = 260;
            itemCapacity = 25;
            liquidCapacity = 20f;
            craftTime = 90f;
            outputItem = new ItemStack(OMItems.solidMagic, 1);
            updateEffect = Fx.steam;
            updateEffectChance = 0.04f;
            craftEffect = Fx.mineImpactWave;
            consumePower(0.75f);
            consumeCoolant(0.08f).boost();
        }};

        arcaneRefinery = new ArcaneRefinery("arcane_refinery") {{
            requirements(Category.crafting, arcaneRefineryReqBase);
            buildCostMultiplier = 0.44f;
            size = 2;
            health = 300;
            itemCapacity = 170;
            liquidCapacity = 60f;
            craftTime = 55f;
            craftEffect = Fx.pulverizeMedium;
            updateEffect = Fx.smeltsmoke;
            updateEffectChance = 0.12f;
            consumePower(1.35f);

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.magic-copper",
                with(Items.copper, 2, Items.lead, 1, OMItems.solidMagic, 1),
                null, 0f,
                new ItemStack(OMItems.magicCopperIngot, 1),
                24f,
                null
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.graphite",
                with(Items.coal, 2),
                Liquids.water, 0.08f,
                new ItemStack(Items.graphite, 1),
                28f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryUpgradeMk2)
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.silicon",
                with(Items.coal, 1, Items.sand, 2),
                Liquids.water, 0.1f,
                new ItemStack(Items.silicon, 1),
                44f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryUpgradeMk2)
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.metaglass",
                with(Items.lead, 1, Items.sand, 2),
                Liquids.water, 0.08f,
                new ItemStack(Items.metaglass, 1),
                36f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryUpgradeMk2)
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.arcane-dust",
                with(Items.sand, 2, OMItems.solidMagic, 1),
                Liquids.water, 0.08f,
                new ItemStack(OMItems.arcaneDust, 2),
                32f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryUpgradeMk2)
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.arcane-dust-cheap",
                with(Items.sand, 3, OMItems.solidMagic, 1),
                null, 0f,
                new ItemStack(OMItems.arcaneDust, 1),
                48f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryUpgradeMk2)
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.arcane-cristal",
                with(OMItems.arcaneDust, 2, Items.silicon, 1),
                Liquids.cryofluid, 0.08f,
                new ItemStack(OMItems.arcaneCristal, 1),
                46f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryUpgradeMk3)
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.arcane-cristal-cheap",
                with(OMItems.arcaneDust, 3, Items.silicon, 1),
                null, 0f,
                new ItemStack(OMItems.arcaneCristal, 1),
                66f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryUpgradeMk3)
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.titanium-slag",
                with(Items.scrap, 3),
                Liquids.slag, 0.18f,
                new ItemStack(Items.titanium, 1),
                48f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryArcanoUpgrade)
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.silicon-slag",
                with(Items.coal, 1, Items.sand, 1),
                Liquids.slag, 0.16f,
                new ItemStack(Items.silicon, 2),
                46f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryArcanoUpgrade)
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.metaglass-slag",
                with(Items.sand, 2),
                Liquids.slag, 0.16f,
                new ItemStack(Items.metaglass, 2),
                40f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryArcanoUpgrade)
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.arcane-surge-alloy",
                with(OMItems.arcaneCristal, 2, Items.titanium, 2, Items.thorium, 1, OMItems.magicCopperIngot, 1),
                Liquids.slag, 0.2f,
                new ItemStack(OMItems.arcaneSurgeAlloy, 1),
                62f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryArcanoUpgrade)
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.arcane-surge-alloy-cheap",
                with(OMItems.arcaneCristal, 2, Items.titanium, 2, OMItems.magicCopperIngot, 1),
                null, 0f,
                new ItemStack(OMItems.arcaneSurgeAlloy, 1),
                86f,
                () -> isUpgradeUnlocked(OMItems.arcaneRefineryArcanoUpgrade)
            ));
        }};
        if (arcaneRefinery != null && arcaneRefinery.consPower != null) {
            baseArcaneRefineryPowerUse = arcaneRefinery.consPower.usage;
        }

        magicCopperBlock = new ShieldWall("magic_copper_block") {{
            requirements(Category.defense, with(Items.copper, 10, Items.lead, 6, OMItems.magicCopperIngot, 4));
            health = 420;
            size = 1;

            shieldHealth = 125f;
            regenSpeed = 0.95f;
            breakCooldown = 4.8f * 60f;
            glowColor.set(0.38f, 0.65f, 1f, 1f);
            glowMag = 0.55f;
            glowScl = 10f;
        }
            @Override
            public void load() {
                super.load();

                if (region == null || !region.found()) {
                    region = Core.atlas.find("magic_copper_wall", region);
                }

                if (glowRegion == null || !glowRegion.found()) {
                    if (Blocks.shieldedWall instanceof ShieldWall) {
                        ShieldWall vanilla = (ShieldWall) Blocks.shieldedWall;
                        if (vanilla.glowRegion != null && vanilla.glowRegion.found()) {
                            glowRegion = vanilla.glowRegion;
                            return;
                        }
                    }

                    glowRegion = Core.atlas.find("shielded-wall-glow", region);
                }
            }
        };
        if (magicCopperBlock instanceof ShieldWall) {
            ShieldWall wall = (ShieldWall) magicCopperBlock;
            baseMagicWallShieldHealth = wall.shieldHealth;
            baseMagicWallRegen = wall.regenSpeed;
            baseMagicWallBreakCooldown = wall.breakCooldown;
        }

        arcaneCopperWallLarge = new ShieldWall("arcane_copper_wall_large") {{
            requirements(Category.defense, with(Items.copper, 24, Items.lead, 18, OMItems.magicCopperIngot, 10, OMItems.arcaneDust, 6, OMItems.arcaneCristal, 4));
            health = 1740;
            size = 2;

            shieldHealth = 640f;
            regenSpeed = 0.42f;
            breakCooldown = 3.6f * 60f;
            glowColor.set(0.38f, 0.65f, 1f, 1f);
            glowMag = 0.58f;
            glowScl = 10f;
        }
            @Override
            public void load() {
                super.load();
                if (glowRegion == null || !glowRegion.found()) {
                    if (Blocks.shieldedWall instanceof ShieldWall) {
                        ShieldWall vanilla = (ShieldWall) Blocks.shieldedWall;
                        if (vanilla.glowRegion != null && vanilla.glowRegion.found()) {
                            glowRegion = vanilla.glowRegion;
                            return;
                        }
                    }
                    glowRegion = Core.atlas.find("shielded-wall-glow", region);
                }
            }
        };
        if (arcaneCopperWallLarge instanceof ShieldWall) {
            ShieldWall wall = (ShieldWall) arcaneCopperWallLarge;
            baseArcaneWallLargeShieldHealth = wall.shieldHealth;
            baseArcaneWallLargeRegen = wall.regenSpeed;
            baseArcaneWallLargeBreakCooldown = wall.breakCooldown;
        }

        magicCopperDrill = new MagicCopperDrill("magic_copper_drill") {{
            requirements(Category.production, with(Items.copper, 120, Items.lead, 95, Items.graphite, 45, OMItems.magicCopperIngot, 30));
            size = 2;
            health = 320;
            tier = 3;
            drillTime = 170f;
            rotateSpeed = 4f;
            itemCapacity = 20;
            consumePower(0.8f);
            consumeCoolant(0.15f).boost();
        }};
        if (magicCopperDrill instanceof Drill) {
            Drill drill = (Drill) magicCopperDrill;
            baseMagicDrillTime = drill.drillTime;
            baseMagicDrillBoost = drill.liquidBoostIntensity;
            baseMagicDrillRotate = drill.rotateSpeed;
        }

        magicCopperInserter = new MagicCopperInserter("magic_copper_inserter") {{
            requirements(Category.distribution, with(Items.copper, 12, Items.lead, 10, OMItems.magicCopperIngot, 2));
            health = 90;
            moveTime = 6f;
        }};

        logisticReceiver = new mindustry.world.blocks.distribution.Router("logistic_receiver") {{
            requirements(Category.distribution, with(Items.copper, 22, Items.lead, 20, Items.graphite, 10, OMItems.arcaneDust, 6));
            health = 150;
            itemCapacity = 18;
        }
            @Override
            public void load() {
                super.load();
                if (region == null || !region.found()) {
                    region = Blocks.router.region;
                    fullIcon = uiIcon = region;
                }
            }
        };
        if (logisticReceiver instanceof mindustry.world.blocks.distribution.Router) {
            mindustry.world.blocks.distribution.Router router = (mindustry.world.blocks.distribution.Router) logisticReceiver;
            baseLogisticReceiverSpeed = router.speed;
            baseLogisticReceiverCapacity = router.itemCapacity;
        }

        logisticSolicitor = new Unloader("logistic_solicitor") {{
            requirements(Category.distribution, with(Items.copper, 36, Items.lead, 32, Items.silicon, 18, Items.titanium, 12, OMItems.arcaneDust, 10));
            health = 220;
            speed = 2f;
        }
            @Override
            public void load() {
                super.load();
                if (region == null || !region.found()) {
                    region = Blocks.unloader.region;
                    fullIcon = uiIcon = region;
                }
            }
        };
        if (logisticSolicitor instanceof Unloader) {
            baseLogisticSolicitorSpeed = ((Unloader) logisticSolicitor).speed;
        }

        logisticStorage = new StorageBlock("logistic_storage") {{
            requirements(Category.distribution, with(Items.copper, 90, Items.lead, 70, Items.titanium, 35, Items.metaglass, 50, OMItems.arcaneCristal, 16));
            size = 2;
            health = 720;
            itemCapacity = 420;
            scaledHealth = 65f;
            coreMerge = false;
            buildType = LogisticStorageBuild::new;
        }
            @Override
            public void load() {
                super.load();
                if (region == null || !region.found()) {
                    region = Blocks.container.region;
                    fullIcon = uiIcon = region;
                }
            }

            public class LogisticStorageBuild extends StorageBuild {
                private int dynamicCapacity() {
                    int adjacent = 0;
                    for (int i = 0; i < 4; i++) {
                        mindustry.gen.Building nearby = nearby(i);
                        if (nearby == null || nearby.team != team) continue;
                        if (nearby.block == logisticStorage) adjacent++;
                    }
                    return itemCapacity + Math.min(adjacent * 80, 320);
                }

                @Override
                public boolean acceptItem(mindustry.gen.Building source, Item item) {
                    return items.total() < dynamicCapacity();
                }

                @Override
                public int getMaximumAccepted(Item item) {
                    return Math.max(dynamicCapacity() - items.total(), 0);
                }
            }
        };
        if (logisticStorage != null) {
            baseLogisticStorageCapacity = logisticStorage.itemCapacity;
        }

        airArcaneFactory = new UnitFactory("air_arcane_factory") {{
            requirements(Category.units, with(Items.copper, 170, Items.lead, 140, Items.graphite, 90, Items.silicon, 120, OMItems.magicCopperIngot, 65, OMItems.arcaneCristal, 16, OMItems.arcaneSurgeAlloy, 12));
            size = 3;
            health = 760;
            itemCapacity = 150;
            consumePower(2.4f);

            plans.add(new UnitFactory.UnitPlan(
                OMUnits.flea,
                60f * 24f,
                with(Items.silicon, 45, Items.graphite, 35, Items.lead, 40, OMItems.magicCopperIngot, 20, OMItems.arcaneDust, 10)
            ));
            plans.add(new UnitFactory.UnitPlan(
                OMUnits.fleaBuilder,
                60f * 22f,
                with(Items.silicon, 40, Items.graphite, 40, Items.lead, 42, OMItems.magicCopperIngot, 18, OMItems.arcaneDust, 12)
            ));
            plans.add(new UnitFactory.UnitPlan(
                OMUnits.fleaMiner,
                60f * 23f,
                with(Items.silicon, 38, Items.graphite, 32, Items.lead, 44, OMItems.magicCopperIngot, 18, OMItems.arcaneDust, 14)
            ));
            plans.add(new UnitFactory.UnitPlan(
                OMUnits.fleaRepairer,
                60f * 24f,
                with(Items.silicon, 44, Items.graphite, 38, Items.lead, 46, OMItems.magicCopperIngot, 19, OMItems.arcaneDust, 12, OMItems.arcaneCristal, 6)
            ));
        }};
        if (airArcaneFactory instanceof UnitFactory) {
            UnitFactory factory = (UnitFactory) airArcaneFactory;
            baseAirArcaneFactoryItemCapacity = factory.itemCapacity;
            if (factory.consPower != null) {
                baseAirArcaneFactoryPowerUse = factory.consPower.usage;
            }
            if (factory.plans != null && factory.plans.any()) {
                for (UnitFactory.UnitPlan plan : factory.plans) {
                    if (plan.unit == OMUnits.flea) {
                        airArcaneFactoryFleaPlan = plan;
                        baseAirArcaneFactoryFleaPlanTime = plan.time;
                    } else if (plan.unit == OMUnits.fleaBuilder) {
                        airArcaneFactoryFleaBuilderPlan = plan;
                        baseAirArcaneFactoryFleaBuilderPlanTime = plan.time;
                    } else if (plan.unit == OMUnits.fleaMiner) {
                        airArcaneFactoryFleaMinerPlan = plan;
                        baseAirArcaneFactoryFleaMinerPlanTime = plan.time;
                    } else if (plan.unit == OMUnits.fleaRepairer) {
                        airArcaneFactoryFleaRepairerPlan = plan;
                        baseAirArcaneFactoryFleaRepairerPlanTime = plan.time;
                    }
                }
            }
        }

        cacheFleaBase(OMUnits.flea);
        cacheFleaBase(OMUnits.fleaBuilder);
        cacheFleaBase(OMUnits.fleaMiner);
        cacheFleaBase(OMUnits.fleaRepairer);

        magicTrio = new MagicTrioTurret("magic_trio") {{
            requirements(Category.turret, with(Items.copper, 130, Items.lead, 115, Items.graphite, 70, Items.silicon, 35, OMItems.magicCopperIngot, 30));
            size = 1;
            health = 360;
            range = 128f;
            reload = 9f;
            recoil = 1.1f;
            recoils = 2;
            shoot = new ShootAlternate(3.2f);
            shootY = 4f;
            inaccuracy = 1.8f;
            rotateSpeed = 9f;
            shootCone = 20f;
            maxAmmo = 80;
            ammoUseEffect = Fx.casing1;
            coolantMultiplier = 2.2f;
            coolant = consumeCoolant(0.25f);
            consumePower(1.45f);

            Seq<Object> ammoEntries = new Seq<>();
            Vars.content.items().each(item -> {
                ammoEntries.add(item);
                ammoEntries.add(createGenericTrioAmmo(item));
            });

            // Custom ammo behavior overrides.
            ammoEntries.add(Items.copper);
            ammoEntries.add(createCopperTrioAmmo());
            ammoEntries.add(Items.silicon);
            ammoEntries.add(createSiliconTrioAmmo());
            ammoEntries.add(OMItems.magicCopperIngot);
            ammoEntries.add(createMagicIngotTrioAmmo());
            ammo(ammoEntries.toArray(Object.class));

            limitRange();
        }};

        primeBaseNames();
        applyVanillaUpgrades();
    }

    private static BasicBulletType createGenericTrioAmmo(Item item) {
        return new BasicBulletType(3.2f, 12f, "bullet") {{
            width = 7f;
            height = 9f;
            lifetime = 64f;
            ammoMultiplier = 2f;
            hitColor = item.color;
            backColor = item.color.cpy().mul(0.75f);
            frontColor = item.color;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            hitEffect = Fx.hitBulletColor;
            despawnEffect = Fx.hitBulletColor;
        }};
    }

    private static BasicBulletType createCopperTrioAmmo() {
        return new BasicBulletType(3.3f, 16f, "bullet") {{
            width = 7f;
            height = 9f;
            lifetime = 64f;
            ammoMultiplier = 2.5f;
            hitColor = Items.copper.color;
            backColor = Items.copper.color.cpy().mul(0.7f);
            frontColor = Items.copper.color;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            hitEffect = Fx.hitBulletColor;
            despawnEffect = Fx.hitBulletColor;
        }};
    }

    private static BasicBulletType createSiliconTrioAmmo() {
        return new BasicBulletType(3.5f, 15f, "bullet") {{
            width = 7f;
            height = 10f;
            lifetime = 68f;
            ammoMultiplier = 3f;
            reloadMultiplier = 1.25f;
            homingPower = 0.09f;
            homingRange = 80f;
            pierce = true;
            pierceCap = 3;
            trailWidth = 1.35f;
            trailLength = 10;
            trailColor = Items.silicon.color;
            hitColor = Items.silicon.color;
            backColor = Items.silicon.color.cpy().mul(0.75f);
            frontColor = Items.silicon.color;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            hitEffect = Fx.plasticExplosion;
            despawnEffect = Fx.plasticExplosion;
        }};
    }

    private static BasicBulletType createMagicIngotTrioAmmo() {
        return new BasicBulletType(3.9f, 24f, "bullet") {{
            width = 9f;
            height = 12f;
            lifetime = 72f;
            ammoMultiplier = 1.5f;
            reloadMultiplier = 0.9f;
            splashDamage = 42f;
            splashDamageRadius = 24f;
            lightning = 4;
            lightningLength = 8;
            lightningLengthRand = 4;
            lightningDamage = 19f;
            status = StatusEffects.shocked;
            statusDuration = 90f;
            trailWidth = 1.6f;
            trailLength = 13;
            trailColor = Color.valueOf("6fb6ff");
            hitColor = Color.valueOf("7ec8ff");
            backColor = OMItems.magicCopperIngot.color.cpy().mul(0.6f).lerp(Color.valueOf("5ea7ff"), 0.45f);
            frontColor = OMItems.magicCopperIngot.color.cpy().lerp(Color.white, 0.28f);
            shootEffect = Fx.shootBig;
            smokeEffect = Fx.shootBigSmoke2;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
        }};
    }

    public static void applyVanillaUpgrades() {
        cacheVanillaDefaults();

        int bridgeRangeLevel = upgradeLevel(OMItems.bridgeRangeUpgrade1, OMItems.bridgeRangeUpgrade2, OMItems.bridgeRangeUpgrade3, OMItems.bridgeRangeUpgrade4);
        int conduitRangeLevel = upgradeLevel(OMItems.conduitBridgeRangeUpgrade1, OMItems.conduitBridgeRangeUpgrade2, OMItems.conduitBridgeRangeUpgrade3, OMItems.conduitBridgeRangeUpgrade4);
        int conveyorSpeedLevel = upgradeLevel(OMItems.conveyorSpeedUpgrade1, OMItems.conveyorSpeedUpgrade2, OMItems.conveyorSpeedUpgrade3, OMItems.conveyorSpeedUpgrade4);
        int duoLevel = upgradeLevel(OMItems.duoUpgradeMk2, OMItems.duoUpgradeMk3, OMItems.duoUpgradeMk4, OMItems.duoUpgradeMk5);
        int arcLevel = upgradeLevel(OMItems.arcUpgradeMk2, OMItems.arcUpgradeMk3, OMItems.arcUpgradeMk4, OMItems.arcUpgradeMk5);
        int arcaneRefineryLevel = upgradeLevel(OMItems.arcaneRefineryUpgradeMk2, OMItems.arcaneRefineryUpgradeMk3, OMItems.arcaneRefineryUpgradeMk4, OMItems.arcaneRefineryUpgradeMk5);
        int magicWallLevel = upgradeLevel(OMItems.magicWallUpgradeMk2, OMItems.magicWallUpgradeMk3, OMItems.magicWallUpgradeMk4, OMItems.magicWallUpgradeMk5);
        int magicDrillLevel = upgradeLevel(OMItems.magicDrillUpgradeMk2, OMItems.magicDrillUpgradeMk3, OMItems.magicDrillUpgradeMk4, OMItems.magicDrillUpgradeMk5);
        int fleaLevel = upgradeLevel(OMItems.fleaUpgradeMk2, OMItems.fleaUpgradeMk3, OMItems.fleaUpgradeMk4, OMItems.fleaUpgradeMk5);
        boolean duoArcano = isUpgradeUnlocked(OMItems.arcaneAmmoUpgrade);
        boolean refineryArcano = isUpgradeUnlocked(OMItems.arcaneRefineryArcanoUpgrade);
        boolean wallArcano = isUpgradeUnlocked(OMItems.magicWallArcanoUpgrade);
        boolean drillArcano = isUpgradeUnlocked(OMItems.magicDrillArcanoUpgrade);
        boolean arcArcano = isUpgradeUnlocked(OMItems.arcArcanoUpgrade);
        boolean fleaArcano = isUpgradeUnlocked(OMItems.fleaArcanoUpgrade);

        if (Blocks.itemBridge instanceof ItemBridge) {
            ItemBridge bridge = (ItemBridge) Blocks.itemBridge;
            bridge.range = baseItemBridgeRange + bridgeRangeLevel;
            setMkName(Blocks.itemBridge, bridgeRangeLevel);
        }

        if (Blocks.bridgeConduit instanceof LiquidBridge) {
            LiquidBridge bridge = (LiquidBridge) Blocks.bridgeConduit;
            bridge.range = baseBridgeConduitRange + conduitRangeLevel;
            setMkName(Blocks.bridgeConduit, conduitRangeLevel);
        }

        float speedScale = 1f + conveyorSpeedLevel * 0.35f;
        applyConveyorSpeed(Blocks.conveyor, baseConveyorSpeed, baseConveyorDisplayedSpeed, speedScale);
        applyConveyorSpeed(Blocks.titaniumConveyor, baseTitaniumConveyorSpeed, baseTitaniumConveyorDisplayedSpeed, speedScale);
        applyConveyorSpeed(Blocks.armoredConveyor, baseArmoredConveyorSpeed, baseArmoredConveyorDisplayedSpeed, speedScale);
        setMkName(Blocks.conveyor, conveyorSpeedLevel);
        setMkName(Blocks.titaniumConveyor, conveyorSpeedLevel);
        setMkName(Blocks.armoredConveyor, conveyorSpeedLevel);

        if (duoArcano) {
            patchVanillaArcaneAmmo();
        }

        applyDuoMkBuffs(duoLevel, duoArcano);
        applyArcaneRefineryUpgrade(arcaneRefineryLevel, refineryArcano);
        applyMagicWallUpgrade(magicWallLevel, wallArcano);
        applyMagicDrillUpgrade(magicDrillLevel, drillArcano);
        applyArcUpgrade(arcLevel, arcArcano);
        applyLogisticsOverdrive(conveyorSpeedLevel);
        applyFleaUpgrade(fleaLevel, fleaArcano);
        applyAirArcaneFactoryUpgrade(fleaLevel, fleaArcano);
        applyScatterUniqueUpgrade();
        applySalvoUniqueUpgrade();
        applyMechanicalDrillUniqueUpgrade();
        applyGraphitePressUniqueUpgrade();
        refreshRuntimeDetails(conveyorSpeedLevel, arcaneRefineryLevel, refineryArcano, magicWallLevel, wallArcano, magicDrillLevel, drillArcano, fleaLevel, fleaArcano);
    }

    private static void cacheVanillaDefaults() {
        if (vanillaDefaultsCached) return;
        vanillaDefaultsCached = true;

        if (Blocks.itemBridge instanceof ItemBridge) {
            baseItemBridgeRange = ((ItemBridge) Blocks.itemBridge).range;
        }
        if (Blocks.bridgeConduit instanceof LiquidBridge) {
            baseBridgeConduitRange = ((LiquidBridge) Blocks.bridgeConduit).range;
        }

        cacheConveyorDefaults(
            Blocks.conveyor,
            (speed, displayedSpeed) -> {
                baseConveyorSpeed = speed;
                baseConveyorDisplayedSpeed = displayedSpeed;
            }
        );
        cacheConveyorDefaults(
            Blocks.titaniumConveyor,
            (speed, displayedSpeed) -> {
                baseTitaniumConveyorSpeed = speed;
                baseTitaniumConveyorDisplayedSpeed = displayedSpeed;
            }
        );
        cacheConveyorDefaults(
            Blocks.armoredConveyor,
            (speed, displayedSpeed) -> {
                baseArmoredConveyorSpeed = speed;
                baseArmoredConveyorDisplayedSpeed = displayedSpeed;
            }
        );

        if (Blocks.duo instanceof ItemTurret) {
            ItemTurret duo = (ItemTurret) Blocks.duo;
            baseDuoReload = duo.reload;
            baseDuoRange = duo.range;
            baseDuoInaccuracy = duo.inaccuracy;
            baseDuoMaxAmmo = duo.maxAmmo;
        }

        if (Blocks.arc instanceof PowerTurret) {
            PowerTurret arc = (PowerTurret) Blocks.arc;
            baseArcRange = arc.range;
            baseArcReload = arc.reload;
            baseArcCoolantMultiplier = arc.coolantMultiplier;
            baseArcHasItems = arc.hasItems;
            baseArcItemCapacity = arc.itemCapacity;
            snapshotBullet(arc.shootType);
        }

        if (Blocks.mechanicalDrill instanceof Drill) {
            Drill drill = (Drill) Blocks.mechanicalDrill;
            baseMechanicalDrillTime = drill.drillTime;
            baseMechanicalDrillBoost = drill.liquidBoostIntensity;
        }

        if (Blocks.graphitePress instanceof GenericCrafter) {
            GenericCrafter press = (GenericCrafter) Blocks.graphitePress;
            baseGraphitePressCraftTime = press.craftTime;
            baseGraphitePressItemCapacity = press.itemCapacity;
        }

        cacheFleaBase(OMUnits.flea);
        cacheFleaBase(OMUnits.fleaBuilder);
        cacheFleaBase(OMUnits.fleaMiner);
        cacheFleaBase(OMUnits.fleaRepairer);

        if (airArcaneFactory instanceof UnitFactory && baseAirArcaneFactoryItemCapacity <= 0) {
            UnitFactory factory = (UnitFactory) airArcaneFactory;
            baseAirArcaneFactoryItemCapacity = factory.itemCapacity;
            if (baseAirArcaneFactoryPowerUse <= 0f && factory.consPower != null) {
                baseAirArcaneFactoryPowerUse = factory.consPower.usage;
            }
            if (factory.plans != null && factory.plans.any()) {
                for (UnitFactory.UnitPlan plan : factory.plans) {
                    if (plan.unit == OMUnits.flea) {
                        airArcaneFactoryFleaPlan = plan;
                        baseAirArcaneFactoryFleaPlanTime = plan.time;
                    } else if (plan.unit == OMUnits.fleaBuilder) {
                        airArcaneFactoryFleaBuilderPlan = plan;
                        baseAirArcaneFactoryFleaBuilderPlanTime = plan.time;
                    } else if (plan.unit == OMUnits.fleaMiner) {
                        airArcaneFactoryFleaMinerPlan = plan;
                        baseAirArcaneFactoryFleaMinerPlanTime = plan.time;
                    } else if (plan.unit == OMUnits.fleaRepairer) {
                        airArcaneFactoryFleaRepairerPlan = plan;
                        baseAirArcaneFactoryFleaRepairerPlanTime = plan.time;
                    }
                }
            }
        }

        if (baseArcaneRefineryPowerUse <= 0f && arcaneRefinery != null && arcaneRefinery.consPower != null) {
            baseArcaneRefineryPowerUse = arcaneRefinery.consPower.usage;
        }
    }

    private static void cacheConveyorDefaults(Block block, ConveyorDefaultsConsumer consumer) {
        if (!(block instanceof Conveyor)) return;
        Conveyor conveyor = (Conveyor) block;
        consumer.accept(conveyor.speed, conveyor.displayedSpeed);
    }

    private static void applyConveyorSpeed(Block block, float baseSpeed, float baseDisplayedSpeed, float scale) {
        if (!(block instanceof Conveyor)) return;
        Conveyor conveyor = (Conveyor) block;
        conveyor.speed = baseSpeed * scale;
        conveyor.displayedSpeed = baseDisplayedSpeed * scale;
    }

    private static int upgradeLevel(Item... upgrades) {
        int level = 0;
        for (Item upgrade : upgrades) {
            if (isUpgradeUnlocked(upgrade)) level++;
        }
        return level;
    }

    private static void applyDuoMkBuffs(int level, boolean arcanoUnlocked) {
        if (!(Blocks.duo instanceof ItemTurret)) return;
        ItemTurret duo = (ItemTurret) Blocks.duo;

        duo.reload = baseDuoReload;
        duo.range = baseDuoRange;
        duo.inaccuracy = baseDuoInaccuracy;
        duo.maxAmmo = baseDuoMaxAmmo;
        restoreTurretAmmo(duo);

        if (level <= 0) {
            setExactName(Blocks.duo, baseName(Blocks.duo));
            return;
        }

        duo.reload = Math.max(2.6f, baseDuoReload * (1f - 0.11f * level));
        duo.range = baseDuoRange * (1f + 0.07f * level);
        duo.inaccuracy = Math.max(0f, baseDuoInaccuracy - 0.3f * level);
        duo.maxAmmo = baseDuoMaxAmmo + 10 * level;

        float damageScale = 1f + 0.13f * level;
        scaleTurretAmmo(duo, damageScale, 1f);
        if (arcanoUnlocked) {
            duo.range *= 1.12f;
            duo.reload *= 0.88f;
            scaleTurretAmmo(duo, 1.18f, 1.2f);
            setExactName(Blocks.duo, bundle("upgrade.orions-mod.block.duo-arcano", "Duo Arcano"));
        } else {
            setMkName(Blocks.duo, level);
        }
    }

    private static void applyArcaneRefineryUpgrade(int level, boolean arcanoUnlocked) {
        if (arcaneRefinery == null) return;
        if (!(arcaneRefinery instanceof ArcaneRefinery)) return;
        ArcaneRefinery refinery = (ArcaneRefinery) arcaneRefinery;
        if (arcaneRefinery.consPower != null && baseArcaneRefineryPowerUse > 0f) {
            arcaneRefinery.consPower.usage = baseArcaneRefineryPowerUse;
        }

        if (level <= 0 && !arcanoUnlocked) {
            arcaneRefinery.requirements(Category.crafting, arcaneRefineryReqBase);
            refinery.speedMultiplier = 1f;
            refinery.outputMultiplier = 1f;
            setExactName(arcaneRefinery, baseName(arcaneRefinery));
            return;
        }

        if (arcanoUnlocked) {
            arcaneRefinery.requirements(Category.crafting, arcaneRefineryReqMk6);
            refinery.speedMultiplier = 3.1f;
            refinery.outputMultiplier = 2.3f;
            if (arcaneRefinery.consPower != null && baseArcaneRefineryPowerUse > 0f) {
                arcaneRefinery.consPower.usage = baseArcaneRefineryPowerUse * 2.05f;
            }
            setExactName(arcaneRefinery, bundle("upgrade.orions-mod.block.arcane-refinery-arcano", "Arcane Refinery Arcano"));
            return;
        }

        if (level == 1) {
            arcaneRefinery.requirements(Category.crafting, arcaneRefineryReqMk2);
        } else if (level == 2) {
            arcaneRefinery.requirements(Category.crafting, arcaneRefineryReqMk3);
        } else if (level == 3) {
            arcaneRefinery.requirements(Category.crafting, arcaneRefineryReqMk4);
        } else {
            arcaneRefinery.requirements(Category.crafting, arcaneRefineryReqMk5);
        }
        refinery.speedMultiplier = 1f + 0.72f * level;
        refinery.outputMultiplier = 1f + 0.28f * level;
        if (arcaneRefinery.consPower != null && baseArcaneRefineryPowerUse > 0f) {
            arcaneRefinery.consPower.usage = baseArcaneRefineryPowerUse * (1f + 0.18f * level);
        }
        setMkName(arcaneRefinery, level);
    }

    private static void applyMagicWallUpgrade(int level, boolean arcanoUnlocked) {
        if (!(magicCopperBlock instanceof ShieldWall)) return;
        ShieldWall wall = (ShieldWall) magicCopperBlock;
        wall.shieldHealth = baseMagicWallShieldHealth;
        wall.regenSpeed = baseMagicWallRegen;
        wall.breakCooldown = baseMagicWallBreakCooldown;
        ShieldWall largeWall = arcaneCopperWallLarge instanceof ShieldWall ? (ShieldWall) arcaneCopperWallLarge : null;
        if (largeWall != null) {
            largeWall.shieldHealth = baseArcaneWallLargeShieldHealth;
            largeWall.regenSpeed = baseArcaneWallLargeRegen;
            largeWall.breakCooldown = baseArcaneWallLargeBreakCooldown;
        }

        if (level > 0) {
            wall.shieldHealth = baseMagicWallShieldHealth * (1f + 0.22f * level);
            wall.regenSpeed = baseMagicWallRegen * (1f + 0.2f * level);
            wall.breakCooldown = Math.max(2f * 60f, baseMagicWallBreakCooldown * (1f - 0.08f * level));
            if (largeWall != null) {
                largeWall.shieldHealth = baseArcaneWallLargeShieldHealth * (1f + 0.22f * level);
                largeWall.regenSpeed = baseArcaneWallLargeRegen * (1f + 0.2f * level);
                largeWall.breakCooldown = Math.max(2f * 60f, baseArcaneWallLargeBreakCooldown * (1f - 0.08f * level));
            }
            setMkName(magicCopperBlock, level);
            setMkName(arcaneCopperWallLarge, level);
        } else {
            setExactName(magicCopperBlock, baseName(magicCopperBlock));
            setExactName(arcaneCopperWallLarge, baseName(arcaneCopperWallLarge));
        }

        if (arcanoUnlocked) {
            wall.shieldHealth *= 1.45f;
            wall.regenSpeed *= 1.35f;
            if (largeWall != null) {
                largeWall.shieldHealth *= 1.45f;
                largeWall.regenSpeed *= 1.35f;
            }
            setExactName(magicCopperBlock, bundle("upgrade.orions-mod.block.magic-wall-arcano", "Arcane Copper Wall Arcano"));
            setExactName(arcaneCopperWallLarge, bundle("upgrade.orions-mod.block.arcane-wall-large-arcano", "Arcane Copper Wall Large Arcano"));
        }
    }

    private static void applyMagicDrillUpgrade(int level, boolean arcanoUnlocked) {
        if (!(magicCopperDrill instanceof Drill)) return;
        Drill drill = (Drill) magicCopperDrill;
        drill.drillTime = baseMagicDrillTime;
        drill.liquidBoostIntensity = baseMagicDrillBoost;
        drill.rotateSpeed = baseMagicDrillRotate;

        if (level > 0) {
            drill.drillTime = baseMagicDrillTime * Math.max(0.3f, 1f - 0.14f * level);
            drill.liquidBoostIntensity = baseMagicDrillBoost * (1f + 0.24f * level);
            drill.rotateSpeed = baseMagicDrillRotate * (1f + 0.2f * level);
            setMkName(magicCopperDrill, level);
        } else {
            setExactName(magicCopperDrill, baseName(magicCopperDrill));
        }

        if (arcanoUnlocked) {
            drill.drillTime *= 0.48f;
            drill.liquidBoostIntensity *= 1.65f;
            drill.rotateSpeed *= 1.35f;
            setExactName(magicCopperDrill, bundle("upgrade.orions-mod.block.magic-drill-arcano", "Magic Copper Drill Arcano"));
        }
    }

    private static void applyFleaUpgrade(int level, boolean arcanoUnlocked) {
        applySingleFleaUpgrade(OMUnits.flea, level, arcanoUnlocked, "upgrade.orions-mod.unit.flea-arcano", "Flea Arcano");
        applySingleFleaUpgrade(OMUnits.fleaBuilder, level, arcanoUnlocked, "upgrade.orions-mod.unit.flea-builder-arcano", "Flea Builder Arcano");
        applySingleFleaUpgrade(OMUnits.fleaMiner, level, arcanoUnlocked, "upgrade.orions-mod.unit.flea-miner-arcano", "Flea Miner Arcano");
        applySingleFleaUpgrade(OMUnits.fleaRepairer, level, arcanoUnlocked, "upgrade.orions-mod.unit.flea-repairer-arcano", "Flea Repairer Arcano");
    }

    private static void applySingleFleaUpgrade(UnitType flea, int level, boolean arcanoUnlocked, String arcanoKey, String arcanoFallback) {
        if (flea == null) return;
        UnitSnapshot base = fleaBaseStats.get(flea);
        String baseName = fleaBaseNames.get(flea);
        if (base == null || baseName == null) return;

        flea.speed = base.speed;
        flea.health = base.health;
        flea.mineSpeed = base.mineSpeed;
        flea.buildSpeed = base.buildSpeed;
        flea.itemCapacity = base.itemCapacity;
        flea.buildRange = base.buildRange;
        flea.mineRange = base.mineRange;
        setExactUnitName(flea, baseName);

        if (level > 0) {
            flea.speed = base.speed * (1f + 0.08f * level);
            flea.health = base.health * (1f + 0.16f * level);
            flea.mineSpeed = base.mineSpeed * (1f + 0.18f * level);
            flea.buildSpeed = base.buildSpeed * (1f + 0.22f * level);
            flea.itemCapacity = base.itemCapacity + 8 * level;
            flea.buildRange = base.buildRange + 5f * level;
            flea.mineRange = base.mineRange + 4f * level;
            setMkUnitName(flea, baseName, level);
        }

        if (arcanoUnlocked) {
            flea.speed *= 1.22f;
            flea.health *= 1.3f;
            flea.mineSpeed *= 1.35f;
            flea.buildSpeed *= 1.4f;
            flea.itemCapacity += 24;
            flea.buildRange += 10f;
            flea.mineRange += 8f;
            setExactUnitName(flea, bundle(arcanoKey, arcanoFallback));
        }
    }

    private static void applyLogisticsOverdrive(int conveyorLevel) {
        if (logisticReceiver instanceof mindustry.world.blocks.distribution.Router) {
            mindustry.world.blocks.distribution.Router router = (mindustry.world.blocks.distribution.Router) logisticReceiver;
            router.speed = baseLogisticReceiverSpeed * (1f + 0.1f * conveyorLevel);
            router.itemCapacity = baseLogisticReceiverCapacity + 3 * conveyorLevel;
        }

        if (logisticSolicitor instanceof Unloader) {
            Unloader unloader = (Unloader) logisticSolicitor;
            unloader.speed = baseLogisticSolicitorSpeed * (1f + 0.14f * conveyorLevel);
        }

        if (logisticStorage != null) {
            logisticStorage.itemCapacity = baseLogisticStorageCapacity + 35 * conveyorLevel;
        }
    }

    private static void refreshRuntimeDetails(int conveyorLevel, int refineryLevel, boolean refineryArcano, int wallLevel, boolean wallArcano, int drillLevel, boolean drillArcano, int fleaLevel, boolean fleaArcano) {
        if (arcaneRefinery != null) {
            float throughput = 0.0417f * ((ArcaneRefinery) arcaneRefinery).speedMultiplier * ((ArcaneRefinery) arcaneRefinery).outputMultiplier;
            float power = arcaneRefinery.consPower == null ? 0f : arcaneRefinery.consPower.usage;
            arcaneRefinery.details = "Role: modular transmutation line.\nFormula: speed x output x network(<=+24%).\nPower: " + fmt(power) + "/s.\nExpected base throughput: " + fmt(throughput) + " items/s (magic copper recipe).";
        }

        if (magicCopperBlock instanceof ShieldWall) {
            ShieldWall wall = (ShieldWall) magicCopperBlock;
            magicCopperBlock.details = "Role: fast-regen shield wall.\nFormula: shield/regen scale by Mk level.\nCurrent shield: " + fmt(wall.shieldHealth) + ", regen: " + fmt(wall.regenSpeed) + "/s.\nTier: Mk" + (wallLevel + 1) + (wallArcano ? " + Arcano" : "");
        }

        if (arcaneCopperWallLarge instanceof ShieldWall) {
            ShieldWall wall = (ShieldWall) arcaneCopperWallLarge;
            arcaneCopperWallLarge.details = "Role: heavy arcane shield anchor.\nFormula: larger shield pool, lower base regen.\nCurrent shield: " + fmt(wall.shieldHealth) + ", regen: " + fmt(wall.regenSpeed) + "/s.\nTier: Mk" + (wallLevel + 1) + (wallArcano ? " + Arcano" : "");
        }

        if (magicCopperDrill instanceof Drill) {
            Drill drill = (Drill) magicCopperDrill;
            magicCopperDrill.details = "Role: arcane extraction drill.\nFormula: drillTime down, coolant boost up with Mk.\nCurrent drillTime: " + fmt(drill.drillTime) + ", coolant boost: x" + fmt(drill.liquidBoostIntensity) + ".\nTier: Mk" + (drillLevel + 1) + (drillArcano ? " + Arcano" : "");
        }

        if (airArcaneFactory instanceof UnitFactory) {
            UnitFactory factory = (UnitFactory) airArcaneFactory;
            float power = factory.consPower == null ? 0f : factory.consPower.usage;
            airArcaneFactory.details = "Role: support drone assembly hub.\nProfiles: Builder / Miner / Repairer.\nPower: " + fmt(power) + "/s, capacity: " + factory.itemCapacity + ".\nTier: Mk" + (fleaLevel + 1) + (fleaArcano ? " + Arcano" : "");
        }

        if (logisticReceiver instanceof mindustry.world.blocks.distribution.Router) {
            mindustry.world.blocks.distribution.Router router = (mindustry.world.blocks.distribution.Router) logisticReceiver;
            logisticReceiver.details = "Role: buffered intake/router.\nFormula: speed and buffer scale with conveyor upgrades.\nCurrent speed: " + fmt(router.speed) + ", capacity: " + router.itemCapacity + ".\nOverdrive level: " + conveyorLevel + ".";
        }

        if (logisticSolicitor instanceof Unloader) {
            Unloader unloader = (Unloader) logisticSolicitor;
            logisticSolicitor.details = "Role: priority pull node.\nFormula: extraction cadence scales with conveyor upgrades.\nCurrent speed: " + fmt(unloader.speed) + ".\nOverdrive level: " + conveyorLevel + ".";
        }

        if (logisticStorage != null) {
            logisticStorage.details = "Role: adjacency-scaling warehouse.\nFormula: +" + 80 + " capacity per adjacent storage (cap +320).\nCurrent base capacity: " + logisticStorage.itemCapacity + ".\nOverdrive level: " + conveyorLevel + ".";
        }

        refreshFleaDetails(OMUnits.flea, fleaLevel, fleaArcano, "Base support profile. Balanced mining/build/repair.");
        refreshFleaDetails(OMUnits.fleaBuilder, fleaLevel, fleaArcano, "Builder profile. Highest construction throughput.");
        refreshFleaDetails(OMUnits.fleaMiner, fleaLevel, fleaArcano, "Miner profile. Highest extraction and carry.");
        refreshFleaDetails(OMUnits.fleaRepairer, fleaLevel, fleaArcano, "Repair profile. Highest survivability for frontline maintenance.");
    }

    private static void refreshFleaDetails(UnitType unit, int level, boolean arcano, String role) {
        if (unit == null) return;
        unit.details = "Role: " + role + "\nSpeed: " + fmt(unit.speed) + ", HP: " + fmt(unit.health) + ", build: " + fmt(unit.buildSpeed) + ", mine: " + fmt(unit.mineSpeed) + ".\nCapacity: " + unit.itemCapacity + ", build range: " + fmt(unit.buildRange) + ", mine range: " + fmt(unit.mineRange) + ".\nTier: Mk" + (level + 1) + (arcano ? " + Arcano" : "");
    }

    private static String fmt(float value) {
        return arc.util.Strings.autoFixed(value, 2);
    }

    private static void applyAirArcaneFactoryUpgrade(int level, boolean arcanoUnlocked) {
        if (!(airArcaneFactory instanceof UnitFactory)) return;
        UnitFactory factory = (UnitFactory) airArcaneFactory;
        if (factory.consPower != null && baseAirArcaneFactoryPowerUse > 0f) {
            factory.consPower.usage = baseAirArcaneFactoryPowerUse;
        }

        factory.itemCapacity = baseAirArcaneFactoryItemCapacity;
        if (airArcaneFactoryFleaPlan != null) {
            airArcaneFactoryFleaPlan.time = baseAirArcaneFactoryFleaPlanTime;
        }
        if (airArcaneFactoryFleaBuilderPlan != null) {
            airArcaneFactoryFleaBuilderPlan.time = baseAirArcaneFactoryFleaBuilderPlanTime;
        }
        if (airArcaneFactoryFleaMinerPlan != null) {
            airArcaneFactoryFleaMinerPlan.time = baseAirArcaneFactoryFleaMinerPlanTime;
        }
        if (airArcaneFactoryFleaRepairerPlan != null) {
            airArcaneFactoryFleaRepairerPlan.time = baseAirArcaneFactoryFleaRepairerPlanTime;
        }
        setExactName(airArcaneFactory, baseName(airArcaneFactory));

        if (level > 0) {
            factory.itemCapacity = baseAirArcaneFactoryItemCapacity + 16 * level;
            if (factory.consPower != null && baseAirArcaneFactoryPowerUse > 0f) {
                factory.consPower.usage = baseAirArcaneFactoryPowerUse * (1f + 0.14f * level);
            }
            if (airArcaneFactoryFleaPlan != null) {
                float timeScale = Math.max(0.45f, 1f - 0.12f * level);
                airArcaneFactoryFleaPlan.time = baseAirArcaneFactoryFleaPlanTime * timeScale;
            }
            if (airArcaneFactoryFleaBuilderPlan != null) {
                float timeScale = Math.max(0.45f, 1f - 0.12f * level);
                airArcaneFactoryFleaBuilderPlan.time = baseAirArcaneFactoryFleaBuilderPlanTime * timeScale;
            }
            if (airArcaneFactoryFleaMinerPlan != null) {
                float timeScale = Math.max(0.45f, 1f - 0.12f * level);
                airArcaneFactoryFleaMinerPlan.time = baseAirArcaneFactoryFleaMinerPlanTime * timeScale;
            }
            if (airArcaneFactoryFleaRepairerPlan != null) {
                float timeScale = Math.max(0.45f, 1f - 0.12f * level);
                airArcaneFactoryFleaRepairerPlan.time = baseAirArcaneFactoryFleaRepairerPlanTime * timeScale;
            }
            setMkName(airArcaneFactory, level);
        }

        if (arcanoUnlocked) {
            factory.itemCapacity += 32;
            if (factory.consPower != null && baseAirArcaneFactoryPowerUse > 0f) {
                factory.consPower.usage *= 1.35f;
            }
            if (airArcaneFactoryFleaPlan != null) {
                airArcaneFactoryFleaPlan.time *= 0.65f;
            }
            if (airArcaneFactoryFleaBuilderPlan != null) {
                airArcaneFactoryFleaBuilderPlan.time *= 0.65f;
            }
            if (airArcaneFactoryFleaMinerPlan != null) {
                airArcaneFactoryFleaMinerPlan.time *= 0.65f;
            }
            if (airArcaneFactoryFleaRepairerPlan != null) {
                airArcaneFactoryFleaRepairerPlan.time *= 0.65f;
            }
            setExactName(airArcaneFactory, bundle("upgrade.orions-mod.block.air-arcane-factory-arcano", "Air Arcane Factory Arcana"));
        }
    }

    private static void applyArcUpgrade(int level, boolean arcanoUnlocked) {
        if (!(Blocks.arc instanceof PowerTurret)) return;
        PowerTurret arc = (PowerTurret) Blocks.arc;
        BulletType bullet = arc.shootType;
        restoreBullet(bullet);

        arc.range = baseArcRange;
        arc.reload = baseArcReload;
        arc.coolantMultiplier = baseArcCoolantMultiplier;
        arc.hasItems = baseArcHasItems;
        arc.itemCapacity = baseArcItemCapacity;
        removeArcMagicCopperConsume(arc);

        if (level > 0) {
            arc.range = baseArcRange * (1f + 0.08f * level);
            arc.reload = baseArcReload * (1f - 0.08f * level);
            arc.coolantMultiplier = baseArcCoolantMultiplier * (1f + 0.16f * level);

            BulletSnapshot base = snapshotBullet(bullet);
            bullet.damage = base.damage * (1f + 0.1f * level);
            bullet.lightningLength = Math.max(1, Math.round(base.lightningLength * (1f + 0.1f * level)));
            bullet.lightningLengthRand = Math.max(0, Math.round(base.lightningLengthRand * (1f + 0.1f * level)));

            setMkName(Blocks.arc, level);
        } else {
            setExactName(Blocks.arc, baseName(Blocks.arc));
        }

        if (!arcanoUnlocked) return;

        arc.hasItems = true;
        arc.itemCapacity = Math.max(arc.itemCapacity, 20);
        addArcMagicCopperConsume(arc);

        BulletSnapshot base = snapshotBullet(bullet);
        float mkDamageScale = 1f + 0.1f * level;
        arc.range *= 1.5f;
        arc.coolantMultiplier *= 1.65f;
        arc.reload *= 0.9f;
        bullet.damage = base.damage * mkDamageScale * 1.35f;
        bullet.lightningLength = Math.max(1, Math.round(base.lightningLength * mkDamageScale * 1.5f));
        bullet.lightningLengthRand = Math.max(0, Math.round(base.lightningLengthRand * mkDamageScale * 1.5f));
        float baseLightning = base.lightningDamage <= 0f ? base.damage * 0.7f : base.lightningDamage;
        bullet.lightningDamage = baseLightning * mkDamageScale * 1.35f;

        setExactName(Blocks.arc, bundle("upgrade.orions-mod.block.arc-arcano", "Arc Arcano"));
    }

    private static void applyScatterUniqueUpgrade() {
        if (!(Blocks.scatter instanceof ItemTurret)) return;
        ItemTurret scatter = (ItemTurret) Blocks.scatter;

        float baseRange = cacheBaseFloat(Blocks.scatter, "range", scatter.range);
        float baseReload = cacheBaseFloat(Blocks.scatter, "reload", scatter.reload);

        scatter.range = baseRange;
        scatter.reload = baseReload;
        restoreTurretAmmo(scatter);
        setExactName(Blocks.scatter, baseName(Blocks.scatter));

        boolean unlocked = isUpgradeUnlocked(OMItems.scatterArcanoUpgrade);
        if (!unlocked) return;

        scatter.range = baseRange * 1.22f;
        scatter.reload = baseReload * 0.88f;
        scaleTurretAmmo(scatter, 1.2f, 1.25f);
        setExactName(Blocks.scatter, bundle("upgrade.orions-mod.block.scatter-arcano", "Scatter Arcano"));
    }

    private static void applySalvoUniqueUpgrade() {
        if (!(Blocks.salvo instanceof ItemTurret)) return;
        ItemTurret salvo = (ItemTurret) Blocks.salvo;

        float baseRange = cacheBaseFloat(Blocks.salvo, "range", salvo.range);
        float baseReload = cacheBaseFloat(Blocks.salvo, "reload", salvo.reload);
        float baseInaccuracy = cacheBaseFloat(Blocks.salvo, "inaccuracy", salvo.inaccuracy);

        salvo.range = baseRange;
        salvo.reload = baseReload;
        salvo.inaccuracy = baseInaccuracy;
        restoreTurretAmmo(salvo);
        setExactName(Blocks.salvo, baseName(Blocks.salvo));

        boolean unlocked = isUpgradeUnlocked(OMItems.salvoArcanoUpgrade);
        if (!unlocked) return;

        salvo.range = baseRange * 1.2f;
        salvo.reload = baseReload * 0.82f;
        salvo.inaccuracy = Math.max(0f, baseInaccuracy - 0.8f);
        scaleTurretAmmo(salvo, 1.18f, 1.2f);
        setExactName(Blocks.salvo, bundle("upgrade.orions-mod.block.salvo-arcano", "Salvo Arcano"));
    }

    private static void applyMechanicalDrillUniqueUpgrade() {
        if (!(Blocks.mechanicalDrill instanceof Drill)) return;
        Drill drill = (Drill) Blocks.mechanicalDrill;

        drill.drillTime = baseMechanicalDrillTime;
        drill.liquidBoostIntensity = baseMechanicalDrillBoost;
        setExactName(Blocks.mechanicalDrill, baseName(Blocks.mechanicalDrill));

        boolean unlocked = isUpgradeUnlocked(OMItems.mechanicalDrillArcanoUpgrade);
        if (!unlocked) return;

        drill.drillTime = baseMechanicalDrillTime * 0.68f;
        drill.liquidBoostIntensity = baseMechanicalDrillBoost * 1.55f;
        setExactName(Blocks.mechanicalDrill, bundle("upgrade.orions-mod.block.mechanical-drill-arcano", "Mechanical Drill Arcano"));
    }

    private static void applyGraphitePressUniqueUpgrade() {
        if (!(Blocks.graphitePress instanceof GenericCrafter)) return;
        GenericCrafter press = (GenericCrafter) Blocks.graphitePress;

        press.craftTime = baseGraphitePressCraftTime;
        press.itemCapacity = baseGraphitePressItemCapacity;
        setExactName(Blocks.graphitePress, baseName(Blocks.graphitePress));

        boolean unlocked = isUpgradeUnlocked(OMItems.graphitePressArcanoUpgrade);
        if (!unlocked) return;

        press.craftTime = baseGraphitePressCraftTime * 0.72f;
        press.itemCapacity = baseGraphitePressItemCapacity + 12;
        setExactName(Blocks.graphitePress, bundle("upgrade.orions-mod.block.graphite-arcano", "Arcane Graphite Press"));
    }

    private static void patchVanillaArcaneAmmo() {
        if (arcaneAmmoPatched) return;
        arcaneAmmoPatched = true;

        addArcaneAmmoToTurret(Blocks.duo, createVanillaArcaneAmmo(3.6f, 22f, 12f, 14f, 2));
        addArcaneAmmoToTurret(Blocks.scatter, createVanillaArcaneFlakAmmo());
        addArcaneAmmoToTurret(Blocks.salvo, createVanillaArcaneAmmo(3.9f, 28f, 20f, 18f, 3));
    }

    private static void addArcaneAmmoToTurret(Block block, BulletType ammo) {
        if (!(block instanceof ItemTurret)) return;
        ItemTurret turret = (ItemTurret) block;
        if (turret.ammoTypes.containsKey(OMItems.magicCopperIngot)) return;
        snapshotBullet(ammo);
        turret.ammoTypes.put(OMItems.magicCopperIngot, ammo);
    }

    private static void restoreTurretAmmo(ItemTurret turret) {
        turret.ammoTypes.each((item, bullet) -> restoreBullet(bullet));
    }

    private static void scaleTurretAmmo(ItemTurret turret, float damageScale, float arcScale) {
        turret.ammoTypes.each((item, bullet) -> {
            BulletSnapshot base = snapshotBullet(bullet);
            bullet.damage = base.damage * damageScale;
            bullet.splashDamage = base.splashDamage * damageScale;
            if (base.lightningDamage > 0f) {
                bullet.lightningDamage = base.lightningDamage * damageScale;
            }
            bullet.lightningLength = Math.max(0, Math.round(base.lightningLength * arcScale));
            bullet.lightningLengthRand = Math.max(0, Math.round(base.lightningLengthRand * arcScale));
        });
    }

    private static BulletSnapshot snapshotBullet(BulletType bullet) {
        if (bullet == null) return BulletSnapshot.empty;
        BulletSnapshot snapshot = bulletBases.get(bullet);
        if (snapshot != null) return snapshot;

        snapshot = new BulletSnapshot();
        snapshot.damage = bullet.damage;
        snapshot.splashDamage = bullet.splashDamage;
        snapshot.lightningDamage = bullet.lightningDamage;
        snapshot.lightningLength = bullet.lightningLength;
        snapshot.lightningLengthRand = bullet.lightningLengthRand;
        bulletBases.put(bullet, snapshot);
        return snapshot;
    }

    private static void restoreBullet(BulletType bullet) {
        BulletSnapshot base = snapshotBullet(bullet);
        if (bullet == null) return;
        bullet.damage = base.damage;
        bullet.splashDamage = base.splashDamage;
        bullet.lightningDamage = base.lightningDamage;
        bullet.lightningLength = base.lightningLength;
        bullet.lightningLengthRand = base.lightningLengthRand;
    }

    private static String baseName(Block block) {
        String base = baseNames.get(block);
        if (base != null) return base;
        base = block.localizedName;
        baseNames.put(block, base);
        return base;
    }

    private static void setMkName(Block block, int level) {
        if (level <= 0) {
            setExactName(block, baseName(block));
            return;
        }
        setExactName(block, baseName(block) + " Mk" + (level + 1));
    }

    private static void setExactName(Block block, String newName) {
        block.localizedName = newName;
    }

    private static void setMkUnitName(UnitType unit, String base, int level) {
        if (level <= 0) {
            setExactUnitName(unit, base);
            return;
        }
        setExactUnitName(unit, base + " Mk" + (level + 1));
    }

    private static void setExactUnitName(UnitType unit, String name) {
        unit.localizedName = name;
    }

    private static String bundle(String key, String fallback) {
        try {
            return Core.bundle.get(key);
        } catch (Throwable ignored) {
            return fallback;
        }
    }

    private static void cacheFleaBase(UnitType unit) {
        if (unit == null || fleaBaseStats.containsKey(unit)) return;
        UnitSnapshot snapshot = new UnitSnapshot();
        snapshot.speed = unit.speed;
        snapshot.health = unit.health;
        snapshot.mineSpeed = unit.mineSpeed;
        snapshot.buildSpeed = unit.buildSpeed;
        snapshot.itemCapacity = unit.itemCapacity;
        snapshot.buildRange = unit.buildRange;
        snapshot.mineRange = unit.mineRange;
        fleaBaseStats.put(unit, snapshot);
        fleaBaseNames.put(unit, unit.localizedName);
    }

    private static boolean isUpgradeUnlocked(Item upgrade) {
        return upgrade != null && upgrade.unlocked();
    }

    private static void primeBaseNames() {
        primeBaseName(Blocks.itemBridge);
        primeBaseName(Blocks.bridgeConduit);
        primeBaseName(Blocks.conveyor);
        primeBaseName(Blocks.titaniumConveyor);
        primeBaseName(Blocks.armoredConveyor);
        primeBaseName(Blocks.duo);
        primeBaseName(Blocks.arc);
        primeBaseName(Blocks.scatter);
        primeBaseName(Blocks.salvo);
        primeBaseName(Blocks.mechanicalDrill);
        primeBaseName(Blocks.graphitePress);
        primeBaseName(arcaneRefinery);
        primeBaseName(magicCopperBlock);
        primeBaseName(arcaneCopperWallLarge);
        primeBaseName(magicCopperDrill);
        primeBaseName(airArcaneFactory);
        primeBaseName(logisticReceiver);
        primeBaseName(logisticSolicitor);
        primeBaseName(logisticStorage);
    }

    private static void primeBaseName(Block block) {
        if (block == null || baseNames.containsKey(block)) return;
        baseNames.put(block, block.localizedName);
    }

    private static void addArcMagicCopperConsume(PowerTurret arc) {
        if (arcMagicCopperConsume != null && arc.hasConsumer(arcMagicCopperConsume)) return;
        arcMagicCopperConsume = arc.consumeItem(OMItems.magicCopperIngot, 1);
    }

    private static void removeArcMagicCopperConsume(PowerTurret arc) {
        if (arcMagicCopperConsume != null && arc.hasConsumer(arcMagicCopperConsume)) {
            arc.removeConsumer(arcMagicCopperConsume);
        }

        Consume candidate = arc.findConsumer(c -> c instanceof ConsumeItems && containsMagicCopperConsume((ConsumeItems) c));
        while (candidate != null) {
            arc.removeConsumer(candidate);
            candidate = arc.findConsumer(c -> c instanceof ConsumeItems && containsMagicCopperConsume((ConsumeItems) c));
        }
    }

    private static boolean containsMagicCopperConsume(ConsumeItems consume) {
        if (consume == null || consume.items == null) return false;
        for (ItemStack stack : consume.items) {
            if (stack.item == OMItems.magicCopperIngot) return true;
        }
        return false;
    }

    private static final ObjectMap<String, Float> baseFloatValues = new ObjectMap<>();

    private static float cacheBaseFloat(Block block, String suffix, float value) {
        String key = block.name + "/" + suffix;
        Float cached = baseFloatValues.get(key);
        if (cached != null) return cached;
        baseFloatValues.put(key, value);
        return value;
    }

    private static BulletType createVanillaArcaneAmmo(float speed, float damage, float splashDamage, float splashRadius, int lightningBolts) {
        return new BasicBulletType(speed, damage, "bullet") {{
            width = 8f;
            height = 11f;
            lifetime = 68f;
            ammoMultiplier = 2f;
            reloadMultiplier = 1.12f;
            this.splashDamage = splashDamage;
            this.splashDamageRadius = splashRadius;
            lightning = lightningBolts;
            lightningLength = 6;
            lightningLengthRand = 4;
            lightningDamage = damage * 0.5f;
            status = StatusEffects.shocked;
            statusDuration = 75f;
            trailWidth = 1.4f;
            trailLength = 11;
            trailColor = Color.valueOf("6fb6ff");
            hitColor = Color.valueOf("7ec8ff");
            backColor = OMItems.magicCopperIngot.color.cpy().mul(0.65f).lerp(Color.valueOf("5ea7ff"), 0.35f);
            frontColor = OMItems.magicCopperIngot.color.cpy().lerp(Color.white, 0.24f);
            shootEffect = Fx.shootBig;
            smokeEffect = Fx.shootBigSmoke2;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
        }};
    }

    private static BulletType createVanillaArcaneFlakAmmo() {
        return new FlakBulletType(4f, 11f) {{
            lifetime = 56f;
            ammoMultiplier = 2f;
            splashDamage = 36f;
            splashDamageRadius = 26f;
            lightning = 2;
            lightningLength = 6;
            lightningDamage = 15f;
            status = StatusEffects.shocked;
            statusDuration = 70f;
            hitColor = Color.valueOf("7ec8ff");
            backColor = OMItems.magicCopperIngot.color.cpy().mul(0.7f);
            frontColor = OMItems.magicCopperIngot.color.cpy().lerp(Color.white, 0.2f);
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            hitEffect = Fx.flakExplosion;
            despawnEffect = Fx.flakExplosion;
        }};
    }

    private interface ConveyorDefaultsConsumer {
        void accept(float speed, float displayedSpeed);
    }

    private static class BulletSnapshot {
        static final BulletSnapshot empty = new BulletSnapshot();
        float damage;
        float splashDamage;
        float lightningDamage;
        int lightningLength;
        int lightningLengthRand;
    }

    private static class UnitSnapshot {
        float speed;
        float health;
        float mineSpeed;
        float buildSpeed;
        int itemCapacity;
        float buildRange;
        float mineRange;
    }
}
