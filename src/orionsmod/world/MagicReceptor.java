package orionsmod.world;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.blocks.production.GenericCrafter;

public class MagicReceptor extends GenericCrafter {
    public float coolantBoostScale = 2.2f;
    public float adjacencyBoostPerNeighbor = 0.15f;
    public Color glowColor = Color.valueOf("8ad3ff");

    protected TextureRegion bottomRegion;
    protected TextureRegion coreHeatRegion;
    protected TextureRegion topHeatRegion;

    public MagicReceptor(String name) {
        super(name);
        update = true;
        hasItems = true;
        hasLiquids = true;
        buildType = MagicReceptorBuild::new;
    }

    @Override
    public void load() {
        super.load();
        bottomRegion = Core.atlas.find(name + "_bottom", region);
        coreHeatRegion = Core.atlas.find(name + "_heat");
        if (!coreHeatRegion.found()) {
            // Compatibility for the typo present in current sprite pack.
            coreHeatRegion = Core.atlas.find(name.replace("receptor", "receptro") + "_heat");
        }
        topHeatRegion = Core.atlas.find(name + "_heat_top");
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{bottomRegion, region};
    }

    public class MagicReceptorBuild extends GenericCrafterBuild {
        private float dumpTimer;

        @Override
        public void updateTile() {
            boolean canRun = enabled && hasPower() && canOutput();
            if (canRun) {
                float speed = efficiency * adjacencyBoost() * coolantBoost();
                warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
                progress += edelta() * speed;
                totalProgress += edelta() * speed;

                if (Mathf.chanceDelta(updateEffectChance * warmup)) {
                    updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f));
                }

                if (progress >= craftTime) {
                    progress %= craftTime;
                    produce();
                    craftEffect.at(x, y);
                }
            } else {
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            dumpTimer += delta();
            if (dumpTimer >= 5f) {
                dumpTimer = 0f;
                if (outputItem != null) dump(outputItem.item);
            }
        }

        @Override
        public void draw() {
            Draw.rect(bottomRegion, x, y);
            Draw.rect(region, x, y);

            if (warmup > 0.001f) {
                float glow = warmup * (0.45f + Mathf.absin(Time.time, 5f, 0.22f));
                if (coreHeatRegion.found()) {
                    Drawf.additive(coreHeatRegion, glowColor, glow, x, y, 0f, Layer.blockAdditive);
                }
                if (topHeatRegion.found()) {
                    Drawf.additive(topHeatRegion, glowColor, glow * 0.95f, x, y, 0f, Layer.blockAdditive + 0.01f);
                }
            }
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return liquid != null && liquid.coolant && super.acceptLiquid(source, liquid);
        }

        @Override
        public int getMaximumAccepted(Item item) {
            return 0;
        }

        private boolean canOutput() {
            if (outputItem == null || outputItem.item == null || outputItem.amount <= 0) return false;
            return items.get(outputItem.item) + outputItem.amount <= itemCapacity;
        }

        private void produce() {
            int amount = Math.max(1, outputItem.amount);
            for (int i = 0; i < amount; i++) {
                offload(outputItem.item);
            }
        }

        private float coolantBoost() {
            Liquid coolant = liquids.current();
            if (coolant == null || liquids.currentAmount() <= 0.001f || optionalEfficiency <= 0.001f) {
                return 1f;
            }
            return 1f + coolant.heatCapacity * coolantBoostScale * optionalEfficiency;
        }

        private float adjacencyBoost() {
            int nearbyCount = 0;
            for (int i = 0; i < 4; i++) {
                Building other = nearby(i);
                if (other != null && other.team == team && other.block == block) {
                    nearbyCount++;
                }
            }
            return 1f + nearbyCount * adjacencyBoostPerNeighbor;
        }

        private boolean hasPower() {
            return MagicReceptor.this.consPower == null || (power != null && power.status > 0.001f);
        }
    }
}
