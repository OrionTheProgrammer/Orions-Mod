package orionsmod.world;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Liquid;
import mindustry.world.consumers.ConsumeLiquidBase;
import mindustry.world.blocks.production.Drill;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;

public class MagicCopperDrill extends Drill {
    private static final int MIN_ROTATOR_FRAME = 1;
    private static final int MAX_ROTATOR_FRAME = 40;

    public Color glowColor = Color.valueOf("6fb6ff");
    public float coolantBoostScale = 3f;

    private TextureRegion topOverlayRegion;
    private TextureRegion glowOverlayRegion;
    private TextureRegion[] rotatorRegions;

    public MagicCopperDrill(String name) {
        super(name);
        drawRim = false;
        drawSpinSprite = false;
        liquidBoostIntensity = 1f;
    }

    @Override
    public void load() {
        super.load();

        topOverlayRegion = Core.atlas.find(name + "_top", topRegion);
        glowOverlayRegion = Core.atlas.find(name + "_glow");

        rotatorRegions = new TextureRegion[MAX_ROTATOR_FRAME - MIN_ROTATOR_FRAME + 1];
        for (int i = 0; i < rotatorRegions.length; i++) {
            int frame = MIN_ROTATOR_FRAME + i;
            rotatorRegions[i] = Core.atlas.find(name + "_rotator" + frame, rotatorRegion);
        }
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{region, topOverlayRegion};
    }

    @Override
    public void setStats() {
        super.setStats();

        Object consumer = findConsumer(c -> c instanceof ConsumeLiquidBase);
        if (consumer instanceof ConsumeLiquidBase) {
            ConsumeLiquidBase consBase = (ConsumeLiquidBase) consumer;
            stats.remove(Stat.booster);
            stats.add(Stat.booster,
                StatValues.speedBoosters(
                    "{0}" + StatUnit.timesSpeed.localized(),
                    consBase.amount,
                    coolantBoostScale,
                    true,
                    this::consumesLiquid
                )
            );
        }
    }

    public class MagicCopperDrillBuild extends DrillBuild {
        private float localDumpTimer;

        @Override
        public void updateTile() {
            localDumpTimer += delta();
            if (localDumpTimer >= 5f) {
                localDumpTimer = 0f;
                dump(dominantItem != null && items.has(dominantItem) ? dominantItem : null);
            }

            if (dominantItem == null) return;

            timeDrilled += warmup * delta();
            float delay = getDrillTime(dominantItem);

            if (items.total() < itemCapacity && dominantItems > 0 && efficiency > 0f) {
                float speed = coolantMultiplier() * efficiency;

                lastDrillSpeed = (speed * dominantItems * warmup) / delay;
                warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);
                progress += delta() * dominantItems * speed * warmup;

                if (Mathf.chanceDelta(updateEffectChance * warmup)) {
                    updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f));
                }
            } else {
                lastDrillSpeed = 0f;
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
                return;
            }

            if (dominantItems > 0 && progress >= delay && items.total() < itemCapacity) {
                offload(dominantItem);
                progress %= delay;

                if (wasVisible && Mathf.chanceDelta(updateEffectChance * warmup)) {
                    drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), dominantItem.color);
                }
            }
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);

            Draw.z(Layer.blockCracks);
            drawDefaultCracks();

            Draw.z(Layer.blockAfterCracks);
            Draw.rect(selectRotatorFrame(), x, y, rotationAngle());
            Draw.rect(topOverlayRegion, x, y);

            if (glowOverlayRegion.found() && warmup > 0.001f) {
                Drawf.additive(glowOverlayRegion, glowColor, warmup * (0.75f + Mathf.absin(Time.time, 4f, 0.25f)), x, y, 0f, Layer.blockAdditive);
            }

            if (dominantItem != null && drawMineItem) {
                Draw.color(dominantItem.color);
                Draw.rect(itemRegion, x, y);
                Draw.color();
            }
        }

        private float coolantMultiplier() {
            Liquid coolant = liquids.current();
            if (coolant == null || liquids.currentAmount() <= 0.0001f || optionalEfficiency <= 0.0001f) {
                return 1f;
            }
            return 1f + coolant.heatCapacity * coolantBoostScale * optionalEfficiency;
        }

        private TextureRegion selectRotatorFrame() {
            if (rotatorRegions.length == 0) return rotatorRegion;
            if (warmup <= 0.01f) {
                int idleCount = Math.min(2, rotatorRegions.length);
                // Keep idle frames frozen when the drill is not running.
                int idleFrame = ((int) (timeDrilled / 8f)) % Math.max(idleCount, 1);
                return rotatorRegions[idleFrame];
            }

            int workingStart = Math.min(2, rotatorRegions.length - 1);
            int workingCount = Math.max(rotatorRegions.length - workingStart, 1);
            float animTime = timeDrilled * (0.5f + warmup * 1.5f);
            int workingFrame = ((int) animTime) % workingCount;
            return rotatorRegions[workingStart + workingFrame];
        }

        private float rotationAngle() {
            return timeDrilled * rotateSpeed;
        }
    }
}
