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
import mindustry.world.Block;
import mindustry.world.blocks.defense.ShieldWall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.ItemBridge;
import mindustry.world.blocks.liquid.LiquidBridge;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
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
    public static Block magicCopperDrill;
    public static Block magicCopperInserter;
    public static Block magicTrio;
    private static boolean vanillaDefaultsCached;
    private static boolean arcaneAmmoPatched;
    private static boolean arcConsumesMagicCopperPatched;

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
    private static float baseMechanicalDrillTime;
    private static float baseMechanicalDrillBoost;
    private static float baseGraphitePressCraftTime;
    private static int baseGraphitePressItemCapacity;
    private static float baseMagicWallShieldHealth;
    private static float baseMagicWallRegen;
    private static float baseMagicWallBreakCooldown;
    private static float baseMagicDrillTime;
    private static float baseMagicDrillBoost;
    private static float baseMagicDrillRotate;
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
            itemCapacity = 110;
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
                () -> OMItems.arcaneRefineryUpgradeMk2 != null && OMItems.arcaneRefineryUpgradeMk2.unlockedNow()
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.silicon",
                with(Items.coal, 1, Items.sand, 2),
                Liquids.water, 0.1f,
                new ItemStack(Items.silicon, 1),
                44f,
                () -> OMItems.arcaneRefineryUpgradeMk2 != null && OMItems.arcaneRefineryUpgradeMk2.unlockedNow()
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.metaglass",
                with(Items.lead, 1, Items.sand, 2),
                Liquids.water, 0.08f,
                new ItemStack(Items.metaglass, 1),
                36f,
                () -> OMItems.arcaneRefineryUpgradeMk2 != null && OMItems.arcaneRefineryUpgradeMk2.unlockedNow()
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.titanium-slag",
                with(Items.scrap, 3),
                Liquids.slag, 0.18f,
                new ItemStack(Items.titanium, 1),
                48f,
                () -> OMItems.arcaneRefineryArcanoUpgrade != null && OMItems.arcaneRefineryArcanoUpgrade.unlockedNow()
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.silicon-slag",
                with(Items.coal, 1, Items.sand, 1),
                Liquids.slag, 0.16f,
                new ItemStack(Items.silicon, 2),
                46f,
                () -> OMItems.arcaneRefineryArcanoUpgrade != null && OMItems.arcaneRefineryArcanoUpgrade.unlockedNow()
            ));

            addRecipe(new ArcaneRefinery.RefineryRecipe(
                "arcane_refinery.metaglass-slag",
                with(Items.sand, 2),
                Liquids.slag, 0.16f,
                new ItemStack(Items.metaglass, 2),
                40f,
                () -> OMItems.arcaneRefineryArcanoUpgrade != null && OMItems.arcaneRefineryArcanoUpgrade.unlockedNow()
            ));
        }};

        magicCopperBlock = new ShieldWall("magic_copper_block") {{
            requirements(Category.defense, with(Items.copper, 10, Items.lead, 6, OMItems.magicCopperIngot, 4));
            health = 420;
            size = 1;

            shieldHealth = 140f;
            regenSpeed = 0.7f;
            breakCooldown = 5f * 60f;
            glowColor.set(0.38f, 0.65f, 1f, 1f);
            glowMag = 0.55f;
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
        if (magicCopperBlock instanceof ShieldWall) {
            ShieldWall wall = (ShieldWall) magicCopperBlock;
            baseMagicWallShieldHealth = wall.shieldHealth;
            baseMagicWallRegen = wall.regenSpeed;
            baseMagicWallBreakCooldown = wall.breakCooldown;
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

        float speedScale = 1f + conveyorSpeedLevel * 0.2f;
        applyConveyorSpeed(Blocks.conveyor, baseConveyorSpeed, baseConveyorDisplayedSpeed, speedScale);
        applyConveyorSpeed(Blocks.titaniumConveyor, baseTitaniumConveyorSpeed, baseTitaniumConveyorDisplayedSpeed, speedScale);
        applyConveyorSpeed(Blocks.armoredConveyor, baseArmoredConveyorSpeed, baseArmoredConveyorDisplayedSpeed, speedScale);
        setMkName(Blocks.conveyor, conveyorSpeedLevel);
        setMkName(Blocks.titaniumConveyor, conveyorSpeedLevel);
        setMkName(Blocks.armoredConveyor, conveyorSpeedLevel);

        if (OMItems.arcaneAmmoUpgrade != null && OMItems.arcaneAmmoUpgrade.unlockedNow()) {
            patchVanillaArcaneAmmo();
        }

        applyDuoMkBuffs(duoLevel, OMItems.arcaneAmmoUpgrade != null && OMItems.arcaneAmmoUpgrade.unlockedNow());
        applyArcaneRefineryUpgrade(arcaneRefineryLevel, OMItems.arcaneRefineryArcanoUpgrade != null && OMItems.arcaneRefineryArcanoUpgrade.unlockedNow());
        applyMagicWallUpgrade(magicWallLevel, OMItems.magicWallArcanoUpgrade != null && OMItems.magicWallArcanoUpgrade.unlockedNow());
        applyMagicDrillUpgrade(magicDrillLevel, OMItems.magicDrillArcanoUpgrade != null && OMItems.magicDrillArcanoUpgrade.unlockedNow());
        applyArcUpgrade(arcLevel, OMItems.arcArcanoUpgrade != null && OMItems.arcArcanoUpgrade.unlockedNow());
        applyScatterUniqueUpgrade();
        applySalvoUniqueUpgrade();
        applyMechanicalDrillUniqueUpgrade();
        applyGraphitePressUniqueUpgrade();
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
            if (upgrade != null && upgrade.unlockedNow()) level++;
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
            setExactName(Blocks.duo, bundle("upgrade.orions-mod.block.duo-arcano", "Duo Mk6 Arcano"));
        } else {
            setMkName(Blocks.duo, level);
        }
    }

    private static void applyArcaneRefineryUpgrade(int level, boolean arcanoUnlocked) {
        if (arcaneRefinery == null) return;
        if (!(arcaneRefinery instanceof ArcaneRefinery)) return;
        ArcaneRefinery refinery = (ArcaneRefinery) arcaneRefinery;

        if (level <= 0 && !arcanoUnlocked) {
            arcaneRefinery.requirements(Category.crafting, arcaneRefineryReqBase);
            refinery.speedMultiplier = 1f;
            refinery.outputMultiplier = 1f;
            setExactName(arcaneRefinery, baseName(arcaneRefinery));
            return;
        }

        if (arcanoUnlocked) {
            arcaneRefinery.requirements(Category.crafting, arcaneRefineryReqMk6);
            refinery.speedMultiplier = 1.9f;
            refinery.outputMultiplier = 2f;
            setExactName(arcaneRefinery, bundle("upgrade.orions-mod.block.arcane-refinery-arcano", "Arcane Refinery Mk6 Arcano"));
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
        refinery.speedMultiplier = 1f + 0.16f * level;
        refinery.outputMultiplier = 1f + 0.14f * level;
        setMkName(arcaneRefinery, level);
    }

    private static void applyMagicWallUpgrade(int level, boolean arcanoUnlocked) {
        if (!(magicCopperBlock instanceof ShieldWall)) return;
        ShieldWall wall = (ShieldWall) magicCopperBlock;
        wall.shieldHealth = baseMagicWallShieldHealth;
        wall.regenSpeed = baseMagicWallRegen;
        wall.breakCooldown = baseMagicWallBreakCooldown;

        if (level > 0) {
            wall.shieldHealth = baseMagicWallShieldHealth * (1f + 0.22f * level);
            wall.regenSpeed = baseMagicWallRegen * (1f + 0.2f * level);
            wall.breakCooldown = Math.max(2f * 60f, baseMagicWallBreakCooldown * (1f - 0.08f * level));
            setMkName(magicCopperBlock, level);
        } else {
            setExactName(magicCopperBlock, baseName(magicCopperBlock));
        }

        if (arcanoUnlocked) {
            wall.shieldHealth *= 1.45f;
            wall.regenSpeed *= 1.35f;
            setExactName(magicCopperBlock, bundle("upgrade.orions-mod.block.magic-wall-arcano", "Magic Copper Wall Mk6 Arcano"));
        }
    }

    private static void applyMagicDrillUpgrade(int level, boolean arcanoUnlocked) {
        if (!(magicCopperDrill instanceof Drill)) return;
        Drill drill = (Drill) magicCopperDrill;
        drill.drillTime = baseMagicDrillTime;
        drill.liquidBoostIntensity = baseMagicDrillBoost;
        drill.rotateSpeed = baseMagicDrillRotate;

        if (level > 0) {
            drill.drillTime = baseMagicDrillTime * (1f - 0.09f * level);
            drill.liquidBoostIntensity = baseMagicDrillBoost * (1f + 0.14f * level);
            drill.rotateSpeed = baseMagicDrillRotate * (1f + 0.1f * level);
            setMkName(magicCopperDrill, level);
        } else {
            setExactName(magicCopperDrill, baseName(magicCopperDrill));
        }

        if (arcanoUnlocked) {
            drill.drillTime *= 0.62f;
            drill.liquidBoostIntensity *= 1.4f;
            drill.rotateSpeed *= 1.25f;
            setExactName(magicCopperDrill, bundle("upgrade.orions-mod.block.magic-drill-arcano", "Magic Copper Drill Mk6 Arcano"));
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

        if (!arcConsumesMagicCopperPatched) {
            arc.hasItems = true;
            arc.itemCapacity = Math.max(arc.itemCapacity, 20);
            arc.consumeItem(OMItems.magicCopperIngot, 1);
            arcConsumesMagicCopperPatched = true;
        }

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

        setExactName(Blocks.arc, bundle("upgrade.orions-mod.block.arc-arcano", "Arc Mk6 Arcano"));
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

        boolean unlocked = OMItems.scatterArcanoUpgrade != null && OMItems.scatterArcanoUpgrade.unlockedNow();
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

        boolean unlocked = OMItems.salvoArcanoUpgrade != null && OMItems.salvoArcanoUpgrade.unlockedNow();
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

        boolean unlocked = OMItems.mechanicalDrillArcanoUpgrade != null && OMItems.mechanicalDrillArcanoUpgrade.unlockedNow();
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

        boolean unlocked = OMItems.graphitePressArcanoUpgrade != null && OMItems.graphitePressArcanoUpgrade.unlockedNow();
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

    private static String bundle(String key, String fallback) {
        try {
            return Core.bundle.get(key);
        } catch (Throwable ignored) {
            return fallback;
        }
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
}
