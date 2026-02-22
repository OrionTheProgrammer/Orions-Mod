package orionsmod;

import arc.Events;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import orionsmod.content.OMBlocks;
import orionsmod.content.OMItems;
import orionsmod.content.OMTechTree;

public class OrionsMod extends Mod {
    public OrionsMod() {
    }

    @Override
    public void init() {
        Events.on(EventType.WorldLoadEvent.class, event -> {
            OMBlocks.applyVanillaUpgrades();
            syncLegacyUnlocks();
        });
    }

    @Override
    public void loadContent() {
        OMItems.load();
        OMBlocks.load();
        OMTechTree.load();
        OMBlocks.applyVanillaUpgrades();
        syncLegacyUnlocks();
    }

    private void syncLegacyUnlocks() {
        if (OMItems.magicCopperIngot != null && OMItems.magicCopperIngot.unlockedNow()
            && OMBlocks.arcaneRefinery != null && !OMBlocks.arcaneRefinery.unlockedNow()) {
            OMBlocks.arcaneRefinery.unlock();
        }
    }
}
