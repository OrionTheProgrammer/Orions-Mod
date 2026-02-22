package orionsmod.world;

import arc.Core;
import arc.func.Boolp;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.layout.Table;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Liquids;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.world.blocks.production.GenericCrafter;
import orionsmod.content.OMBlocks;

public class ArcaneRefinery extends GenericCrafter {
    private static final int OFF_MIN_FRAME = 1;
    private static final int OFF_MAX_FRAME = 2;
    private static final int WORK_MIN_FRAME = 3;

    private final Seq<RefineryRecipe> recipes = new Seq<>();
    private TextureRegion[] offRegions;
    private TextureRegion[] workingRegions;
    private final Color refineryLightColor = Color.valueOf("78c7ff");
    public float speedMultiplier = 1f;
    public float outputMultiplier = 1f;
    public float networkAdjacencyBonus = 0.04f;
    public float networkAdjacencyCap = 0.24f;

    public ArcaneRefinery(String name) {
        super(name);
        configurable = true;
        saveConfig = true;
        hasItems = true;
        hasLiquids = true;
        update = true;
        buildType = ArcaneRefineryBuild::new;

        config(Integer.class, (ArcaneRefineryBuild build, Integer value) -> build.selectRecipe(value == null ? 0 : value));
        config(Boolean.class, (ArcaneRefineryBuild build, Boolean value) -> build.setMasterMode(value != null && value));
    }

    public void addRecipe(RefineryRecipe recipe) {
        recipe.id = recipes.size;
        recipes.add(recipe);
    }

    @Override
    public void load() {
        super.load();

        TextureRegion fallback = Core.atlas.find(name, region);

        offRegions = new TextureRegion[OFF_MAX_FRAME - OFF_MIN_FRAME + 1];
        for (int i = 0; i < offRegions.length; i++) {
            int frame = OFF_MIN_FRAME + i;
            offRegions[i] = Core.atlas.find(name + frame, fallback);
        }

        Seq<TextureRegion> working = new Seq<>();
        for (int frame = WORK_MIN_FRAME; frame <= 200; frame++) {
            TextureRegion region = Core.atlas.find(name + frame);
            if (!region.found()) break;
            working.add(region);
        }

        if (working.isEmpty()) working.add(offRegions[0]);
        workingRegions = working.toArray(TextureRegion.class);
    }

    @Override
    public TextureRegion[] icons() {
        if (offRegions != null && offRegions.length > 0) {
            return new TextureRegion[]{offRegions[0]};
        }
        return super.icons();
    }

    @Override
    public void setBars() {
        super.setBars();
        removeBar("items");
        addBar("orions-mod-network", (ArcaneRefineryBuild build) ->
            new Bar(
                () -> {
                    try {
                        return Core.bundle.get("bar.orions-mod-network");
                    } catch (Throwable ignored) {
                        return "Arcane Network";
                    }
                },
                () -> Color.valueOf("7cc8ff"),
                build::networkBonusf
            )
        );
    }

    public static class RefineryRecipe {
        public int id;
        public String key;
        public ItemStack[] itemInputs;
        public Liquid liquidInput;
        public float liquidAmount;
        public ItemStack output;
        public float craftTime;
        public Boolp unlocked;

        public RefineryRecipe(String key, ItemStack[] itemInputs, Liquid liquidInput, float liquidAmount, ItemStack output, float craftTime, Boolp unlocked) {
            this.key = key;
            this.itemInputs = itemInputs;
            this.liquidInput = liquidInput;
            this.liquidAmount = liquidAmount;
            this.output = output;
            this.craftTime = craftTime;
            this.unlocked = unlocked;
        }

        public boolean unlocked() {
            return unlocked == null || unlocked.get();
        }
    }

    public class ArcaneRefineryBuild extends GenericCrafterBuild {
        private float craftProgress;
        private float craftWarmup;
        private float liquidShareTimer;
        private float dumpTimer;
        private float networkSyncTimer;
        private float lastNetworkBoost = 1f;
        private int selectedRecipe;
        private boolean masterMode;

        @Override
        public void updateTile() {
            Seq<ArcaneRefineryBuild> cluster = connectedCluster();
            ArcaneRefineryBuild master = enforceSingleMaster(cluster);
            if (master != null && master != this && selectedRecipe != master.selectedRecipe) {
                selectRecipe(master.selectedRecipe, false);
            }
            syncClusterRecipe(cluster, master);

            RefineryRecipe recipe = activeRecipe();
            float networkBoost = clusterLogisticsBoost(cluster);
            lastNetworkBoost = networkBoost;

            boolean canRun = recipe != null && enabled && hasElectricity() && hasInputs(recipe, cluster) && canOutput(recipe, cluster);
            if (canRun) {
                craftWarmup = Mathf.approachDelta(craftWarmup, 1f, 0.03f);
                craftProgress += edelta() * efficiency * speedMultiplier * networkBoost;

                if (Mathf.chanceDelta(updateEffectChance * craftWarmup)) {
                    updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f));
                }

                if (recipe.craftTime > 0f && craftProgress >= recipe.craftTime) {
                    craftProgress %= recipe.craftTime;
                    consumeInputs(recipe, cluster);
                    produce(recipe, cluster);
                    craftEffect.at(x, y);
                }
            } else {
                craftWarmup = Mathf.approachDelta(craftWarmup, 0f, 0.03f);
            }

            liquidShareTimer += delta();
            if (liquidShareTimer >= 1f) {
                liquidShareTimer = 0f;
                dumpLiquid(Liquids.water, 1.35f);
                dumpLiquid(Liquids.slag, 1.35f);
                dumpLiquid(Liquids.cryofluid, 1.35f);
                if (recipe != null && recipe.liquidInput != null) {
                    dumpLiquid(recipe.liquidInput, 1.35f);
                }
            }

            dumpTimer += delta();
            if (dumpTimer >= 5f) {
                dumpTimer = 0f;
                if (recipe != null && recipe.output != null) {
                    redistributeClusterOutput(cluster, recipe.output.item);
                    dump(recipe.output.item);
                } else {
                    dump();
                }
            }
        }

        @Override
        public void buildConfiguration(Table table) {
            table.clearChildren();

            table.button("", Styles.togglet, () -> {
                configure(!masterMode);
                setMasterMode(!masterMode);
            }).checked(button -> masterMode)
                .update(button -> button.setText(masterMode
                    ? ui("ui.orions-mod.master-on", "Master: ON")
                    : ui("ui.orions-mod.master-off", "Master: OFF")))
                .growX();
            table.row();

            Table buttons = new Table();
            buttons.defaults().size(42f).pad(2f);

            Table recipeInfo = new Table(Styles.black6);
            recipeInfo.margin(6f);

            int col = 0;
            for (RefineryRecipe recipe : recipes) {
                if (!recipe.unlocked()) continue;

                TextureRegionDrawable icon = new TextureRegionDrawable(recipe.output.item.uiIcon);
                buttons.button(icon, Styles.clearTogglei, () -> {
                        configure(recipe.id);
                        selectRecipe(recipe.id);
                        refreshRecipeInfo(recipeInfo);
                    })
                    .tooltip(recipeName(recipe))
                    .disabled(button -> recipeLockedByMaster())
                    .checked(button -> selectedRecipe == recipe.id);

                col++;
                if (col % 4 == 0) buttons.row();
            }

            table.add(buttons);
            table.row();
            table.add(recipeInfo).growX().padTop(4f);
            refreshRecipeInfo(recipeInfo);
        }

        @Override
        public void draw() {
            Draw.rect(selectFrame(), x, y);
        }

        @Override
        public void drawLight() {
            if (!hasElectricity()) return;
            float lightStrength = 0.18f + 0.52f * craftWarmup;
            Drawf.light(x, y, 42f + 16f * craftWarmup, refineryLightColor, lightStrength);
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            if (items.total() >= itemCapacity) return false;
            RefineryRecipe recipe = activeRecipe();
            if (recipe == null || recipe.itemInputs == null) return false;

            ItemStack target = null;
            for (ItemStack stack : recipe.itemInputs) {
                if (stack.item == item) {
                    target = stack;
                    break;
                }
            }

            if (target == null) return false;
            if (recipe.itemInputs.length <= 1) return true;

            int totalRequired = 0;
            for (ItemStack stack : recipe.itemInputs) {
                totalRequired += stack.amount;
            }

            if (totalRequired <= 0) return true;

            float share = target.amount / (float) totalRequired;
            int maxForItem = Math.max(target.amount * 6, Math.round(itemCapacity * share + target.amount * 2f));
            if (items.get(item) >= maxForItem) {
                return false;
            }
            return true;
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            RefineryRecipe recipe = activeRecipe();
            if (recipe == null || recipe.liquidInput == null) return false;
            return liquid == recipe.liquidInput;
        }

        public void selectRecipe(int id) {
            selectRecipe(id, true);
        }

        private void selectRecipe(int id, boolean propagate) {
            Seq<ArcaneRefineryBuild> cluster = connectedCluster();
            ArcaneRefineryBuild master = enforceSingleMaster(cluster);
            if (master != null && master != this) return;

            selectedRecipe = Mathf.clamp(id, 0, Math.max(recipes.size - 1, 0));
            craftProgress = 0f;
            if (propagate) {
                for (ArcaneRefineryBuild other : cluster) {
                    if (other != this) {
                        other.selectRecipe(selectedRecipe, false);
                    }
                }
            }
        }

        private void setMasterMode(boolean enabled) {
            masterMode = enabled;
            Seq<ArcaneRefineryBuild> cluster = connectedCluster();
            if (enabled) {
                for (ArcaneRefineryBuild other : cluster) {
                    if (other != this) {
                        other.masterMode = false;
                    }
                }
                for (ArcaneRefineryBuild other : cluster) {
                    if (other != this && other.selectedRecipe != selectedRecipe) {
                        other.selectRecipe(selectedRecipe, false);
                    }
                }
            }
        }

        @Override
        public Object config() {
            return selectedRecipe;
        }

        @Override
        public byte version() {
            return 3;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.s(selectedRecipe);
            write.f(craftProgress);
            write.bool(masterMode);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision >= 2) {
                selectedRecipe = read.s();
                craftProgress = read.f();
            }
            if (revision >= 3) {
                masterMode = read.bool();
            } else {
                masterMode = false;
            }
            selectedRecipe = Mathf.clamp(selectedRecipe, 0, Math.max(recipes.size - 1, 0));
        }

        public float networkBonusf() {
            if (networkAdjacencyCap <= 0.0001f) return 0f;
            return Mathf.clamp((lastNetworkBoost - 1f) / networkAdjacencyCap);
        }

        private RefineryRecipe activeRecipe() {
            if (recipes.isEmpty()) return null;

            if (selectedRecipe < 0 || selectedRecipe >= recipes.size || !recipes.get(selectedRecipe).unlocked()) {
                for (int i = 0; i < recipes.size; i++) {
                    if (recipes.get(i).unlocked()) {
                        selectedRecipe = i;
                        break;
                    }
                }
            }

            if (selectedRecipe < 0 || selectedRecipe >= recipes.size) return null;
            RefineryRecipe recipe = recipes.get(selectedRecipe);
            return recipe.unlocked() ? recipe : null;
        }

        private boolean hasInputs(RefineryRecipe recipe, Seq<ArcaneRefineryBuild> cluster) {
            if (recipe.itemInputs != null) {
                for (ItemStack stack : recipe.itemInputs) {
                    int total = 0;
                    for (ArcaneRefineryBuild build : cluster) {
                        total += build.items.get(stack.item);
                    }
                    if (total < stack.amount) return false;
                }
            }

            if (recipe.liquidInput != null) {
                float total = 0f;
                for (ArcaneRefineryBuild build : cluster) {
                    total += build.liquids.get(recipe.liquidInput);
                }
                if (total < recipe.liquidAmount) return false;
            }

            return true;
        }

        private boolean canOutput(RefineryRecipe recipe, Seq<ArcaneRefineryBuild> cluster) {
            if (recipe.output == null) return false;
            int toProduce = Math.max(1, Math.round(recipe.output.amount * outputMultiplier));
            int free = 0;
            for (ArcaneRefineryBuild build : cluster) {
                free += Math.max(build.block.itemCapacity - build.items.total(), 0);
            }
            return free >= toProduce;
        }

        private void consumeInputs(RefineryRecipe recipe, Seq<ArcaneRefineryBuild> cluster) {
            if (recipe.itemInputs != null) {
                for (ItemStack stack : recipe.itemInputs) {
                    int remaining = stack.amount;
                    for (ArcaneRefineryBuild build : cluster) {
                        if (remaining <= 0) break;
                        int take = Math.min(build.items.get(stack.item), remaining);
                        if (take > 0) {
                            build.items.remove(stack.item, take);
                            remaining -= take;
                        }
                    }
                }
            }

            if (recipe.liquidInput != null && recipe.liquidAmount > 0f) {
                float remaining = recipe.liquidAmount;
                for (ArcaneRefineryBuild build : cluster) {
                    if (remaining <= 0f) break;
                    float take = Math.min(build.liquids.get(recipe.liquidInput), remaining);
                    if (take > 0f) {
                        build.liquids.remove(recipe.liquidInput, take);
                        remaining -= take;
                    }
                }
            }
        }

        private void produce(RefineryRecipe recipe, Seq<ArcaneRefineryBuild> cluster) {
            int amount = Math.max(1, Math.round(recipe.output.amount * outputMultiplier));
            for (int i = 0; i < amount; i++) {
                if (!offloadClusterItem(cluster, recipe.output.item)) break;
            }
        }

        private TextureRegion selectFrame() {
            if (!hasElectricity() || craftWarmup <= 0.01f || recipes.isEmpty()) {
                int frame = ((int) (Time.time / 14f)) % offRegions.length;
                return offRegions[frame];
            }

            RefineryRecipe recipe = activeRecipe();
            float recipeProgress = recipe == null || recipe.craftTime <= 0f ? 0f : craftProgress / recipe.craftTime;
            int frame = Mathf.clamp((int) (recipeProgress * workingRegions.length), 0, workingRegions.length - 1);
            return workingRegions[frame];
        }

        private boolean hasElectricity() {
            return ArcaneRefinery.this.consPower == null || (power != null && power.status > 0.001f);
        }

        private String recipeName(RefineryRecipe recipe) {
            try {
                return Core.bundle.get("recipe.orions-mod." + recipe.key);
            } catch (Throwable ignored) {
                return recipe.output != null && recipe.output.item != null ? recipe.output.item.localizedName : recipe.key;
            }
        }

        private Seq<ArcaneRefineryBuild> connectedCluster() {
            Seq<ArcaneRefineryBuild> cluster = new Seq<>();
            Seq<Building> queue = new Seq<>();
            IntSet visited = new IntSet();
            queue.add(this);

            while (!queue.isEmpty()) {
                Building build = queue.pop();
                if (build == null || build.team != team || build.block != ArcaneRefinery.this || !(build instanceof ArcaneRefineryBuild)) continue;
                if (!visited.add(build.pos())) continue;

                ArcaneRefineryBuild refinery = (ArcaneRefineryBuild) build;
                cluster.add(refinery);
                for (int i = 0; i < 4; i++) {
                    Building nearby = refinery.nearby(i);
                    if (nearby != null && nearby.team == team && nearby.block == ArcaneRefinery.this && !visited.contains(nearby.pos())) {
                        queue.add(nearby);
                    }
                }
            }

            return cluster;
        }

        private void syncClusterRecipe(Seq<ArcaneRefineryBuild> cluster, ArcaneRefineryBuild master) {
            networkSyncTimer += delta();
            if (networkSyncTimer < 20f) return;
            networkSyncTimer = 0f;

            if (master != null) {
                if (master == this) {
                    for (ArcaneRefineryBuild other : cluster) {
                        if (other != this && other.selectedRecipe != selectedRecipe) {
                            other.selectRecipe(selectedRecipe, false);
                        }
                    }
                } else if (selectedRecipe != master.selectedRecipe) {
                    selectRecipe(master.selectedRecipe, false);
                }
                return;
            }

            for (ArcaneRefineryBuild other : cluster) {
                if (other == this) continue;
                if (other.selectedRecipe >= 0 && other.selectedRecipe < recipes.size) {
                    RefineryRecipe recipe = recipes.get(other.selectedRecipe);
                    if (recipe.unlocked() && selectedRecipe != other.selectedRecipe) {
                        selectRecipe(other.selectedRecipe, false);
                        return;
                    }
                }
            }
        }

        private boolean offloadClusterItem(Seq<ArcaneRefineryBuild> cluster, Item item) {
            ArcaneRefineryBuild outlet = pickOutlet(cluster, item);
            if (outlet != null) {
                if (outlet.items.total() < outlet.block.itemCapacity) {
                    outlet.items.add(item, 1);
                    outlet.dump(item);
                    return true;
                }
            }

            for (ArcaneRefineryBuild build : cluster) {
                if (build.items.total() < build.block.itemCapacity) {
                    build.items.add(item, 1);
                    build.dump(item);
                    return true;
                }
            }
            return false;
        }

        private ArcaneRefineryBuild enforceSingleMaster(Seq<ArcaneRefineryBuild> cluster) {
            ArcaneRefineryBuild master = null;
            for (ArcaneRefineryBuild build : cluster) {
                if (!build.masterMode) continue;
                if (master == null || build.pos() < master.pos()) {
                    master = build;
                }
            }

            if (master != null) {
                for (ArcaneRefineryBuild build : cluster) {
                    if (build != master) {
                        build.masterMode = false;
                    }
                }
            }
            return master;
        }

        private boolean recipeLockedByMaster() {
            Seq<ArcaneRefineryBuild> cluster = connectedCluster();
            ArcaneRefineryBuild master = enforceSingleMaster(cluster);
            return master != null && master != this;
        }

        private void redistributeClusterOutput(Seq<ArcaneRefineryBuild> cluster, Item item) {
            ArcaneRefineryBuild outlet = pickOutlet(cluster, item);
            if (outlet == null) return;

            int moved = 0;
            for (ArcaneRefineryBuild build : cluster) {
                if (build == outlet) continue;
                while (build.items.get(item) > 0 && outlet.items.total() < outlet.block.itemCapacity && moved < 24) {
                    build.items.remove(item, 1);
                    outlet.items.add(item, 1);
                    moved++;
                }
                if (moved >= 24) break;
            }
            outlet.dump(item);
        }

        private ArcaneRefineryBuild pickOutlet(Seq<ArcaneRefineryBuild> cluster, Item item) {
            ArcaneRefineryBuild best = null;
            for (ArcaneRefineryBuild build : cluster) {
                if (!hasExternalOutput(build, item)) continue;
                if (best == null || build.items.get(item) < best.items.get(item)) {
                    best = build;
                }
            }
            return best;
        }

        private boolean hasExternalOutput(ArcaneRefineryBuild build, Item item) {
            for (int i = 0; i < 4; i++) {
                Building nearby = build.nearby(i);
                if (nearby == null || nearby.team != team || nearby.block == ArcaneRefinery.this) continue;
                if (nearby.acceptItem(build, item)) return true;
            }
            return false;
        }

        private void refreshRecipeInfo(Table info) {
            info.clearChildren();
            info.left().defaults().left().padRight(6f);

            RefineryRecipe recipe = activeRecipe();
            if (recipe == null) {
                info.add(ui("ui.orions-mod.no-recipe", "No recipe")).color(Color.lightGray);
                return;
            }

            if (recipeLockedByMaster()) {
                info.add(ui("ui.orions-mod.master-locked", "Recipe controlled by master refinery")).color(Color.gray).colspan(2).left();
                info.row();
            }

            info.add(recipeName(recipe)).color(Color.lightGray).colspan(2).left();
            info.row();
            int bonusPercent = Math.round((lastNetworkBoost - 1f) * 100f);
            if (bonusPercent > 0) {
                info.add(ui("ui.orions-mod.network-bonus", "Network bonus")).color(Color.lightGray);
                info.add("+" + bonusPercent + "%").color(Color.valueOf("86d7ff")).left();
                info.row();
            }

            info.add(ui("ui.orions-mod.requires", "Requires")).color(Color.lightGray);
            Table req = new Table();
            req.left().defaults().padRight(4f);
            boolean anyInput = false;

            if (recipe.itemInputs != null) {
                for (ItemStack stack : recipe.itemInputs) {
                    req.image(stack.item.uiIcon).size(18f);
                    req.add("x" + stack.amount).color(Color.lightGray).left();
                    anyInput = true;
                }
            }

            if (recipe.liquidInput != null && recipe.liquidAmount > 0f) {
                req.image(recipe.liquidInput.uiIcon).size(18f);
                req.add("x" + Strings.autoFixed(recipe.liquidAmount, 2)).color(Color.lightGray).left();
                anyInput = true;
            }

            if (!anyInput) {
                req.add(ui("ui.orions-mod.none", "None")).color(Color.gray).left();
            }

            info.add(req).growX().left();
            info.row();

            info.add(ui("ui.orions-mod.produces", "Produces")).color(Color.lightGray);
            Table out = new Table();
            out.left().defaults().padRight(4f);
            if (recipe.output != null && recipe.output.item != null) {
                out.image(recipe.output.item.uiIcon).size(18f);
                out.add("x" + recipe.output.amount).color(Color.lightGray).left();
            } else {
                out.add(ui("ui.orions-mod.none", "None")).color(Color.gray).left();
            }
            info.add(out).growX().left();
        }

        private String ui(String key, String fallback) {
            try {
                return Core.bundle.get(key);
            } catch (Throwable ignored) {
                return fallback;
            }
        }

        private float clusterLogisticsBoost(Seq<ArcaneRefineryBuild> cluster) {
            if (cluster == null || cluster.isEmpty()) return 1f;
            IntSet visited = new IntSet();
            int linked = 0;

            for (ArcaneRefineryBuild build : cluster) {
                for (int i = 0; i < 4; i++) {
                    Building near = build.nearby(i);
                    if (near == null || near.team != team) continue;
                    if (!isArcaneLogisticBlock(near)) continue;
                    if (visited.add(near.pos())) linked++;
                }
            }

            float bonus = Math.min(linked * networkAdjacencyBonus, networkAdjacencyCap);
            return 1f + bonus;
        }

        private boolean isArcaneLogisticBlock(Building building) {
            return building.block == OMBlocks.logisticReceiver
                || building.block == OMBlocks.logisticSolicitor
                || building.block == OMBlocks.logisticStorage;
        }
    }
}
