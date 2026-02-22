package orionsmod.world;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Items;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.ui.Bar;
import orionsmod.content.OMItems;

public class MagicTrioTurret extends ItemTurret {
    public String baseRegionName = "base_block_1x1";
    public String leftBarrelRegionName = "magic_trio_barrel-l";
    public String rightBarrelRegionName = "magic_trio_barrel_r";

    public float overheatLimit = 1f;
    public float recoverThreshold = 0.42f;
    public float passiveCooling = 0.0032f;
    public float liquidCoolingScale = 0.011f;
    public float coolantHeatReduction = 1.8f;
    public float minShotHeatScale = 0.18f;

    public float copperShotHeat = 0.13f;
    public float siliconShotHeat = 0.16f;
    public float magicShotHeat = 0.2f;
    public float barrelKickScale = 1.85f;

    public Color overheatBarColor = Color.valueOf("ff7a63");
    public Color overheatDrawColor = Color.valueOf("ff855f");

    protected TextureRegion baseRegion;
    protected TextureRegion leftBarrelRegion;
    protected TextureRegion rightBarrelRegion;

    public MagicTrioTurret(String name) {
        super(name);
        buildType = MagicTrioTurretBuild::new;
    }

    @Override
    public void load() {
        super.load();
        String modPrefix = minfo != null && minfo.mod != null ? (minfo.mod.name + "-") : "";

        // Try both prefixed and non-prefixed variants to support mod atlas naming.
        baseRegion = findRegion(
            new String[]{
                modPrefix + baseRegionName,
                baseRegionName,
                modPrefix + "blocks-turrets-base-" + baseRegionName,
                "blocks-turrets-base-" + baseRegionName
            },
            Core.atlas.find("block-1", region)
        );

        leftBarrelRegion = findRegion(
            new String[]{
                name + "_barrel-l",
                name + "_barrel_l",
                modPrefix + "magic_trio_barrel-l",
                modPrefix + "magic_trio_barrel_l",
                leftBarrelRegionName,
                "magic_trio_barrel_l"
            },
            region
        );

        rightBarrelRegion = findRegion(
            new String[]{
                name + "_barrel-r",
                name + "_barrel_r",
                modPrefix + "magic_trio_barrel-r",
                modPrefix + "magic_trio_barrel_r",
                "magic_trio_barrel-r",
                rightBarrelRegionName
            },
            leftBarrelRegion
        );
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{baseRegion, region};
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("orions-mod-overheat", (MagicTrioTurretBuild build) ->
            new Bar(
                () -> Core.bundle.format("bar.orions-mod-overheat", (int) (build.overheatf() * 100f)),
                () -> overheatBarColor,
                build::overheatf
            )
        );
    }

    public class MagicTrioTurretBuild extends ItemTurretBuild {
        public float overheat;
        public boolean overheated;
        private Item firingItem;
        private float leftKick;
        private float rightKick;

        @Override
        public void updateTile() {
            super.updateTile();
            updateOverheat();
            updateBarrelKick();

            // QoL: allow aiming rotation while player-controls this turret even if not firing.
            if (isControlled()) {
                rotation = Angles.moveToward(rotation, angleTo(unit.aimX(), unit.aimY()), rotateSpeed * delta());
            }
        }

        @Override
        protected void updateShooting() {
            if (overheated) return;
            super.updateShooting();
        }

        @Override
        protected void shoot(mindustry.entities.bullet.BulletType type) {
            if (ammo.peek() instanceof ItemEntry) {
                ItemEntry entry = (ItemEntry) ammo.peek();
                firingItem = entry.item;
            } else {
                firingItem = null;
            }

            super.shoot(type);
            addShotHeat(firingItem);
        }

        @Override
        public void draw() {
            Draw.rect(baseRegion, x, y);

            Draw.z(Layer.turret - 0.5f);
            Drawf.shadow(region, x + recoilOffset.x - elevation, y + recoilOffset.y - elevation, drawrot());

            Draw.z(Layer.turret);
            Draw.rect(region, x + recoilOffset.x, y + recoilOffset.y, drawrot());

            drawBarrel(leftBarrelRegion, barrelRecoil(0), leftKick);
            drawBarrel(rightBarrelRegion, barrelRecoil(1), rightKick);

            if (overheated) {
                Drawf.additive(region, overheatDrawColor, 0.35f + Mathf.absin(6f, 0.15f), x + recoilOffset.x, y + recoilOffset.y, drawrot(), Layer.turretHeat);
            }
        }

        public float overheatf() {
            return Mathf.clamp(overheat / overheatLimit);
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return liquid.coolant && super.acceptLiquid(source, liquid);
        }

        @Override
        protected void handleBullet(mindustry.gen.Bullet bullet, float offsetX, float offsetY, float angleOffset) {
            if (offsetX < -0.001f) {
                leftKick = 1f;
            } else if (offsetX > 0.001f) {
                rightKick = 1f;
            } else if ((barrelCounter & 1) == 0) {
                leftKick = 1f;
            } else {
                rightKick = 1f;
            }
        }

        private void drawBarrel(TextureRegion barrel, float recoilValue, float localKick) {
            float kick = Math.max(recoilValue, localKick);
            float recoilDistance = Mathf.pow(kick, recoilPow) * recoil * barrelKickScale;
            float bx = x + recoilOffset.x + Angles.trnsx(rotation - 90f, 0f, -recoilDistance);
            float by = y + recoilOffset.y + Angles.trnsy(rotation - 90f, 0f, -recoilDistance);
            Draw.rect(barrel, bx, by, drawrot());
        }

        private void updateBarrelKick() {
            float recoilReturn = 1f / Math.max(recoilTime * 0.55f, 1f);
            leftKick = Mathf.approachDelta(leftKick, 0f, recoilReturn);
            rightKick = Mathf.approachDelta(rightKick, 0f, recoilReturn);
        }

        private float barrelRecoil(int index) {
            if (curRecoils != null && index >= 0 && index < curRecoils.length) {
                return curRecoils[index];
            }
            return curRecoil;
        }

        private void updateOverheat() {
            float cooling = passiveCooling;
            Liquid liquid = coolantLiquid();
            if (liquid != null) {
                cooling += liquid.heatCapacity * liquidCoolingScale * optionalEfficiency;
            }

            overheat = Mathf.approachDelta(overheat, 0f, cooling);

            if (overheated && overheat <= overheatLimit * recoverThreshold) {
                overheated = false;
            }
        }

        private Liquid coolantLiquid() {
            Liquid liquid = liquids.current();
            if (liquid == null || liquids.currentAmount() <= 0.001f || optionalEfficiency <= 0.001f) {
                return null;
            }
            return liquid;
        }

        private void addShotHeat(Item item) {
            float shotHeat = copperShotHeat;
            if (item == Items.silicon) shotHeat = siliconShotHeat;
            if (item == OMItems.magicCopperIngot) shotHeat = magicShotHeat;

            Liquid liquid = coolantLiquid();
            float multiplier = 1f;
            if (liquid != null) {
                multiplier = Mathf.clamp(
                    1f - liquid.heatCapacity * coolantHeatReduction * optionalEfficiency,
                    minShotHeatScale,
                    1f
                );
            }

            overheat = Math.min(overheat + shotHeat * multiplier, overheatLimit * 1.35f);
            if (overheat >= overheatLimit) {
                overheated = true;
            }
        }

        @Override
        public byte version() {
            return 3;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(overheat);
            write.bool(overheated);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision >= 3) {
                overheat = read.f();
                overheated = read.bool();
            } else {
                overheat = 0f;
                overheated = false;
            }
        }
    }

    private TextureRegion findRegion(String[] names, TextureRegion fallback) {
        for (String candidate : names) {
            TextureRegion found = Core.atlas.find(candidate);
            if (found != null && found.found()) return found;
        }
        return fallback;
    }
}
