package orionsmod.world;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;

public class MagicCopperInserter extends Block {
    public float moveTime = 8f;

    public MagicCopperInserter(String name) {
        super(name);
        rotate = true;
        update = true;
        hasItems = true;
        solid = true;
        underBullets = true;
        itemCapacity = 1;
        unloadable = false;
        noUpdateDisabled = true;
        group = BlockGroup.transportation;
        envEnabled = Env.any;
    }

    @Override
    public boolean outputsItems() {
        return true;
    }

    @Override
    public int planRotation(int rot) {
        return Mathf.mod(super.planRotation(rot) + 1, 4);
    }

    public class MagicCopperInserterBuild extends Building {
        private Item heldItem;
        private float moveProgress;

        @Override
        public void draw() {
            Draw.rect(block.region, x, y, rotdeg() - 90f);
        }

        @Override
        public void updateTile() {
            if (heldItem == null && items.any()) {
                heldItem = items.first();
            }

            if (heldItem == null) return;

            moveProgress += delta() / moveTime;
            if (moveProgress < 1f) return;

            Building target = nearby(rotation);
            if (target == null || target.team != team || !target.acceptItem(this, heldItem)) return;

            target.handleItem(this, heldItem);
            items.remove(heldItem, 1);
            heldItem = null;
            moveProgress = 0f;
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            if (source == null || source.team != team) return false;
            if (heldItem != null || items.total() >= itemCapacity) return false;

            int from = relativeToEdge(source.tile);
            int back = Mathf.mod(rotation + 2, 4);
            return from == back;
        }

        @Override
        public void handleItem(Building source, Item item) {
            items.add(item, 1);
            heldItem = item;
            moveProgress = 0f;
        }

        @Override
        public int acceptStack(Item item, int amount, mindustry.gen.Teamc source) {
            return 0;
        }

        @Override
        public int removeStack(Item item, int amount) {
            int result = super.removeStack(item, amount);
            if (result > 0 && heldItem == item && items.total() == 0) {
                heldItem = null;
                moveProgress = 0f;
            }
            return result;
        }
    }
}
