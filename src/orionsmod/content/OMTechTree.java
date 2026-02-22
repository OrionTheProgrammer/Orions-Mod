package orionsmod.content;

import mindustry.content.Items;
import mindustry.content.TechTree;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.UnlockableContent;

import static mindustry.type.ItemStack.with;

public final class OMTechTree {
    private OMTechTree() {
    }

    public static void load() {
        TechNode copperNode = findNode(Items.copper);
        if (copperNode == null) return;

        TechNode receptorNode = ensureNode(copperNode, OMBlocks.magicReceptor,
            with(Items.copper, 90, Items.lead, 70, Items.graphite, 35));

        TechNode solidMagicNode = ensureNode(receptorNode, OMItems.solidMagic,
            with(Items.copper, 130, Items.lead, 110, Items.graphite, 70));

        TechNode magicCopperNode = ensureNode(solidMagicNode, OMItems.magicCopperIngot,
            with(Items.copper, 180, Items.lead, 150, OMItems.solidMagic, 80));

        TechNode arcaneDustNode = ensureNode(magicCopperNode, OMItems.arcaneDust,
            with(Items.copper, 140, Items.lead, 120, Items.sand, 150, OMItems.solidMagic, 90));
        TechNode arcaneCristalNode = ensureNode(arcaneDustNode, OMItems.arcaneCristal,
            with(Items.silicon, 170, Items.metaglass, 140, OMItems.arcaneDust, 110, OMItems.magicCopperIngot, 50));
        TechNode arcaneSurgeAlloyNode = ensureNode(arcaneCristalNode, OMItems.arcaneSurgeAlloy,
            with(Items.titanium, 220, Items.thorium, 160, OMItems.arcaneCristal, 120, OMItems.magicCopperIngot, 80));

        TechNode magicWallNode = ensureNode(magicCopperNode, OMBlocks.magicCopperBlock);
        ensureNode(magicWallNode, OMBlocks.arcaneCopperWallLarge,
            with(Items.copper, 300, Items.lead, 220, OMItems.magicCopperIngot, 120, OMItems.arcaneDust, 90, OMItems.arcaneCristal, 50));
        TechNode arcaneRefineryNode = ensureNode(magicCopperNode, OMBlocks.arcaneRefinery, new mindustry.type.ItemStack[0]);
        TechNode magicDrillNode = ensureNode(magicCopperNode, OMBlocks.magicCopperDrill);
        TechNode logisticReceiverNode = ensureNode(arcaneDustNode, OMBlocks.logisticReceiver,
            with(Items.copper, 150, Items.lead, 130, Items.graphite, 70, OMItems.arcaneDust, 40));
        TechNode logisticSolicitorNode = ensureNode(logisticReceiverNode, OMBlocks.logisticSolicitor,
            with(Items.copper, 180, Items.lead, 160, Items.silicon, 100, Items.titanium, 70, OMItems.arcaneDust, 60));
        ensureNode(logisticSolicitorNode, OMBlocks.logisticStorage,
            with(Items.copper, 280, Items.lead, 250, Items.metaglass, 160, Items.titanium, 110, OMItems.arcaneCristal, 90, OMItems.arcaneSurgeAlloy, 35));

        TechNode airArcaneFactoryNode = ensureNode(arcaneCristalNode, OMBlocks.airArcaneFactory,
            with(Items.copper, 260, Items.lead, 220, Items.graphite, 130, Items.silicon, 160, OMItems.magicCopperIngot, 80, OMItems.arcaneSurgeAlloy, 30));
        TechNode fleaNode = ensureNode(airArcaneFactoryNode, OMUnits.flea,
            with(Items.silicon, 180, Items.graphite, 150, Items.lead, 190, OMItems.magicCopperIngot, 80));
        ensureNode(fleaNode, OMUnits.fleaBuilder,
            with(Items.silicon, 150, Items.graphite, 140, Items.lead, 160, OMItems.arcaneDust, 80));
        ensureNode(fleaNode, OMUnits.fleaMiner,
            with(Items.silicon, 150, Items.graphite, 120, Items.lead, 180, OMItems.arcaneDust, 90));
        ensureNode(fleaNode, OMUnits.fleaRepairer,
            with(Items.silicon, 170, Items.graphite, 150, Items.lead, 190, OMItems.arcaneCristal, 70));
        ensureNode(magicCopperNode, OMBlocks.magicCopperInserter);
        ensureNode(magicCopperNode, OMBlocks.magicTrio);

        TechNode bridgeUp1 = ensureNode(magicCopperNode, OMItems.bridgeRangeUpgrade1,
            with(Items.copper, 120, Items.lead, 100, OMItems.magicCopperIngot, 20));
        TechNode bridgeUp2 = ensureNode(bridgeUp1, OMItems.bridgeRangeUpgrade2,
            with(Items.copper, 180, Items.lead, 160, Items.graphite, 90, OMItems.magicCopperIngot, 35));
        TechNode bridgeUp3 = ensureNode(bridgeUp2, OMItems.bridgeRangeUpgrade3,
            with(Items.copper, 240, Items.lead, 220, Items.graphite, 140, Items.silicon, 80, OMItems.magicCopperIngot, 50));
        ensureNode(bridgeUp3, OMItems.bridgeRangeUpgrade4,
            with(Items.copper, 320, Items.lead, 280, Items.graphite, 210, Items.silicon, 130, Items.titanium, 90, OMItems.magicCopperIngot, 70));

        TechNode conduitUp1 = ensureNode(magicCopperNode, OMItems.conduitBridgeRangeUpgrade1,
            with(Items.metaglass, 80, Items.graphite, 90, OMItems.magicCopperIngot, 20));
        TechNode conduitUp2 = ensureNode(conduitUp1, OMItems.conduitBridgeRangeUpgrade2,
            with(Items.metaglass, 130, Items.graphite, 130, Items.titanium, 80, OMItems.magicCopperIngot, 35));
        TechNode conduitUp3 = ensureNode(conduitUp2, OMItems.conduitBridgeRangeUpgrade3,
            with(Items.metaglass, 170, Items.graphite, 170, Items.titanium, 120, Items.silicon, 80, OMItems.magicCopperIngot, 50));
        ensureNode(conduitUp3, OMItems.conduitBridgeRangeUpgrade4,
            with(Items.metaglass, 230, Items.graphite, 220, Items.titanium, 170, Items.silicon, 120, Items.thorium, 70, OMItems.magicCopperIngot, 70));

        TechNode conveyorUp1 = ensureNode(magicCopperNode, OMItems.conveyorSpeedUpgrade1,
            with(Items.copper, 140, Items.lead, 110, Items.graphite, 70, OMItems.magicCopperIngot, 25));
        TechNode conveyorUp2 = ensureNode(conveyorUp1, OMItems.conveyorSpeedUpgrade2,
            with(Items.copper, 220, Items.lead, 180, Items.titanium, 120, Items.silicon, 80, OMItems.magicCopperIngot, 40));
        TechNode conveyorUp3 = ensureNode(conveyorUp2, OMItems.conveyorSpeedUpgrade3,
            with(Items.copper, 300, Items.lead, 250, Items.titanium, 170, Items.silicon, 130, Items.graphite, 140, OMItems.magicCopperIngot, 55));
        ensureNode(conveyorUp3, OMItems.conveyorSpeedUpgrade4,
            with(Items.copper, 390, Items.lead, 320, Items.titanium, 230, Items.silicon, 190, Items.graphite, 190, Items.thorium, 80, OMItems.magicCopperIngot, 75));

        TechNode duoMk2 = ensureNode(magicCopperNode, OMItems.duoUpgradeMk2,
            with(Items.copper, 140, Items.graphite, 90, Items.silicon, 60, OMItems.magicCopperIngot, 25));
        TechNode duoMk3 = ensureNode(duoMk2, OMItems.duoUpgradeMk3,
            with(Items.copper, 210, Items.graphite, 130, Items.silicon, 90, OMItems.magicCopperIngot, 40));
        TechNode duoMk4 = ensureNode(duoMk3, OMItems.duoUpgradeMk4,
            with(Items.copper, 320, Items.graphite, 210, Items.silicon, 150, Items.titanium, 110, OMItems.magicCopperIngot, 60));
        TechNode duoMk5 = ensureNode(duoMk4, OMItems.duoUpgradeMk5,
            with(Items.copper, 420, Items.graphite, 280, Items.silicon, 210, Items.titanium, 170, Items.thorium, 85, OMItems.magicCopperIngot, 80));
        ensureNode(duoMk5, OMItems.arcaneAmmoUpgrade,
            with(Items.copper, 520, Items.graphite, 340, Items.silicon, 260, Items.titanium, 210, Items.thorium, 120, OMItems.magicCopperIngot, 110, OMItems.arcaneSurgeAlloy, 70));

        TechNode arcMk2 = ensureNode(magicCopperNode, OMItems.arcUpgradeMk2,
            with(Items.copper, 150, Items.silicon, 120, Items.graphite, 100, OMItems.magicCopperIngot, 30));
        TechNode arcMk3 = ensureNode(arcMk2, OMItems.arcUpgradeMk3,
            with(Items.copper, 220, Items.silicon, 180, Items.graphite, 150, Items.titanium, 90, OMItems.magicCopperIngot, 45));
        TechNode arcMk4 = ensureNode(arcMk3, OMItems.arcUpgradeMk4,
            with(Items.copper, 300, Items.silicon, 250, Items.graphite, 220, Items.titanium, 130, OMItems.magicCopperIngot, 60));
        TechNode arcMk5 = ensureNode(arcMk4, OMItems.arcUpgradeMk5,
            with(Items.copper, 390, Items.silicon, 320, Items.graphite, 280, Items.titanium, 190, Items.thorium, 90, OMItems.magicCopperIngot, 80));
        ensureNode(arcMk5, OMItems.arcArcanoUpgrade,
            with(Items.copper, 520, Items.silicon, 420, Items.graphite, 360, Items.titanium, 260, Items.thorium, 140, OMItems.magicCopperIngot, 120, OMItems.arcaneSurgeAlloy, 90));

        if (arcaneRefineryNode != null) {
            TechNode refineryMk2 = ensureNode(arcaneRefineryNode, OMItems.arcaneRefineryUpgradeMk2,
                with(Items.copper, 120, Items.lead, 90, Items.graphite, 70, Items.silicon, 40, OMItems.magicCopperIngot, 18));
            TechNode refineryMk3 = ensureNode(refineryMk2, OMItems.arcaneRefineryUpgradeMk3,
                with(Items.copper, 150, Items.lead, 120, Items.graphite, 95, Items.silicon, 60, Items.titanium, 35, OMItems.magicCopperIngot, 30));
            TechNode refineryMk4 = ensureNode(refineryMk3, OMItems.arcaneRefineryUpgradeMk4,
                with(Items.copper, 190, Items.lead, 150, Items.graphite, 130, Items.silicon, 95, Items.titanium, 70, OMItems.magicCopperIngot, 45));
            TechNode refineryMk5 = ensureNode(refineryMk4, OMItems.arcaneRefineryUpgradeMk5,
                with(Items.copper, 240, Items.lead, 200, Items.graphite, 180, Items.silicon, 130, Items.titanium, 110, Items.thorium, 50, OMItems.magicCopperIngot, 65));
            ensureNode(refineryMk5, OMItems.arcaneRefineryArcanoUpgrade,
                with(Items.copper, 320, Items.lead, 260, Items.graphite, 250, Items.silicon, 180, Items.titanium, 160, Items.thorium, 110, OMItems.magicCopperIngot, 95, OMItems.arcaneSurgeAlloy, 95));
        }

        if (magicWallNode != null) {
            TechNode wallMk2 = ensureNode(magicWallNode, OMItems.magicWallUpgradeMk2,
                with(Items.copper, 120, Items.lead, 90, OMItems.magicCopperIngot, 30));
            TechNode wallMk3 = ensureNode(wallMk2, OMItems.magicWallUpgradeMk3,
                with(Items.copper, 180, Items.lead, 140, Items.graphite, 90, OMItems.magicCopperIngot, 45));
            TechNode wallMk4 = ensureNode(wallMk3, OMItems.magicWallUpgradeMk4,
                with(Items.copper, 250, Items.lead, 190, Items.graphite, 130, Items.silicon, 90, OMItems.magicCopperIngot, 60));
            TechNode wallMk5 = ensureNode(wallMk4, OMItems.magicWallUpgradeMk5,
                with(Items.copper, 330, Items.lead, 260, Items.graphite, 180, Items.silicon, 130, Items.titanium, 90, OMItems.magicCopperIngot, 80));
            ensureNode(wallMk5, OMItems.magicWallArcanoUpgrade,
                with(Items.copper, 430, Items.lead, 340, Items.graphite, 250, Items.silicon, 180, Items.titanium, 140, Items.thorium, 70, OMItems.magicCopperIngot, 105, OMItems.arcaneSurgeAlloy, 65));
        }

        if (magicDrillNode != null) {
            TechNode drillMk2 = ensureNode(magicDrillNode, OMItems.magicDrillUpgradeMk2,
                with(Items.copper, 140, Items.lead, 110, Items.graphite, 80, OMItems.magicCopperIngot, 35));
            TechNode drillMk3 = ensureNode(drillMk2, OMItems.magicDrillUpgradeMk3,
                with(Items.copper, 210, Items.lead, 170, Items.graphite, 130, Items.silicon, 90, OMItems.magicCopperIngot, 50));
            TechNode drillMk4 = ensureNode(drillMk3, OMItems.magicDrillUpgradeMk4,
                with(Items.copper, 290, Items.lead, 240, Items.graphite, 190, Items.silicon, 140, Items.titanium, 90, OMItems.magicCopperIngot, 70));
            TechNode drillMk5 = ensureNode(drillMk4, OMItems.magicDrillUpgradeMk5,
                with(Items.copper, 380, Items.lead, 310, Items.graphite, 250, Items.silicon, 190, Items.titanium, 140, Items.thorium, 70, OMItems.magicCopperIngot, 90));
            ensureNode(drillMk5, OMItems.magicDrillArcanoUpgrade,
                with(Items.copper, 500, Items.lead, 390, Items.graphite, 320, Items.silicon, 250, Items.titanium, 200, Items.thorium, 120, OMItems.magicCopperIngot, 120, OMItems.arcaneSurgeAlloy, 80));
        }

        if (fleaNode != null) {
            TechNode fleaMk2 = ensureNode(fleaNode, OMItems.fleaUpgradeMk2,
                with(Items.silicon, 140, Items.graphite, 110, Items.lead, 140, OMItems.magicCopperIngot, 45));
            TechNode fleaMk3 = ensureNode(fleaMk2, OMItems.fleaUpgradeMk3,
                with(Items.silicon, 210, Items.graphite, 170, Items.lead, 200, Items.titanium, 90, OMItems.magicCopperIngot, 65));
            TechNode fleaMk4 = ensureNode(fleaMk3, OMItems.fleaUpgradeMk4,
                with(Items.silicon, 290, Items.graphite, 240, Items.lead, 270, Items.titanium, 140, OMItems.magicCopperIngot, 85));
            TechNode fleaMk5 = ensureNode(fleaMk4, OMItems.fleaUpgradeMk5,
                with(Items.silicon, 380, Items.graphite, 320, Items.lead, 350, Items.titanium, 210, Items.thorium, 90, OMItems.magicCopperIngot, 110));
            ensureNode(fleaMk5, OMItems.fleaArcanoUpgrade,
                with(Items.silicon, 500, Items.graphite, 420, Items.lead, 450, Items.titanium, 300, Items.thorium, 150, OMItems.magicCopperIngot, 150, OMItems.arcaneSurgeAlloy, 90));
        }

        ensureNode(magicCopperNode, OMItems.scatterArcanoUpgrade,
            with(Items.lead, 210, Items.graphite, 140, Items.silicon, 120, OMItems.magicCopperIngot, 55));

        ensureNode(magicCopperNode, OMItems.salvoArcanoUpgrade,
            with(Items.copper, 260, Items.graphite, 180, Items.silicon, 170, Items.thorium, 90, OMItems.magicCopperIngot, 75));

        ensureNode(magicCopperNode, OMItems.mechanicalDrillArcanoUpgrade,
            with(Items.copper, 180, Items.graphite, 120, Items.silicon, 95, OMItems.magicCopperIngot, 45));

        ensureNode(magicCopperNode, OMItems.graphitePressArcanoUpgrade,
            with(Items.copper, 160, Items.lead, 140, Items.graphite, 160, Items.silicon, 85, OMItems.magicCopperIngot, 50));
    }

    private static TechNode findNode(UnlockableContent content) {
        return TechTree.all.find(node -> node.content == content);
    }

    private static TechNode ensureNode(TechNode parent, UnlockableContent content) {
        TechNode existing = findNode(content);
        if (existing != null) return existing;
        return new TechNode(parent, content, content.researchRequirements());
    }

    private static TechNode ensureNode(TechNode parent, UnlockableContent content, mindustry.type.ItemStack[] requirements) {
        TechNode existing = findNode(content);
        if (existing != null) {
            existing.requirements = requirements;
            existing.setupRequirements(requirements);
            if (existing.parent != parent) {
                if (existing.parent != null) {
                    existing.parent.children.remove(existing);
                }
                existing.parent = parent;
                if (!parent.children.contains(existing, true)) {
                    parent.children.add(existing);
                }
            }
            return existing;
        }
        return new TechNode(parent, content, requirements);
    }
}
