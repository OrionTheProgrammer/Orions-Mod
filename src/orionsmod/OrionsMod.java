package orionsmod;

import arc.Events;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import orionsmod.content.OMBlocks;
import orionsmod.content.OMItems;
import orionsmod.content.OMTechTree;
import orionsmod.content.OMUnits;

public class OrionsMod extends Mod {
    public OrionsMod() {
    }

    @Override
    public void init() {
        Events.on(EventType.WorldLoadEvent.class, event -> {
            OMTechTree.load();
            OMBlocks.applyVanillaUpgrades();
            syncLegacyUnlocks();
        });

        Events.on(EventType.UnlockEvent.class, event -> {
            OMTechTree.load();
            OMBlocks.applyVanillaUpgrades();
            syncLegacyUnlocks();
        });
    }

    @Override
    public void loadContent() {
        OMItems.load();
        OMUnits.load();
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

        if (OMUnits.flea != null && OMUnits.flea.unlockedNow()) {
            if (OMUnits.fleaBuilder != null && !OMUnits.fleaBuilder.unlockedNow()) {
                OMUnits.fleaBuilder.unlock();
            }
            if (OMUnits.fleaMiner != null && !OMUnits.fleaMiner.unlockedNow()) {
                OMUnits.fleaMiner.unlock();
            }
            if (OMUnits.fleaRepairer != null && !OMUnits.fleaRepairer.unlockedNow()) {
                OMUnits.fleaRepairer.unlock();
            }
        }

    }
}
