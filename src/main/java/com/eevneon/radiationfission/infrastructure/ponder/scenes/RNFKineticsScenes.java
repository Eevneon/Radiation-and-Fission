package com.eevneon.radiationfission.infrastructure.ponder.scenes;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RNFKineticsScenes {
    public static void rtg(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("rt_generator", "Generating rotational force using the RTG");
        scene.configureBasePlate(0, 0, 5);
        scene.setSceneOffsetY(-1);

        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(10);
        scene.world().showSection(util.select().fromTo(1, 1, 1, 3, 1, 3), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().layer(2), Direction.DOWN);
        scene.idle(20);

        scene.overlay().showText(80)
                .colored(PonderPalette.RED)
                .pointAt(util.vector().centerOf(1, 2, 2))
                .placeNearTarget()
                .text("At times, a compact source of SU is required");
        scene.idle(90);
        scene.overlay().showText(80)
                .colored(PonderPalette.WHITE)
                .pointAt(util.vector().centerOf(1, 2, 2))
                .placeNearTarget()
                .text("The RTG fulfills this need");
        scene.overlay().showOutline(PonderPalette.GREEN, util.select().position(1, 2, 2), util.select().position(1, 2, 2), 80);
        scene.idle(100);

        scene.overlay().showText(80)
                .attachKeyFrame()
                .colored(PonderPalette.WHITE)
                .pointAt(util.vector().centerOf(1, 2, 2))
                .placeNearTarget()
                .text("Fuel pellets can be inserted by hand");
        scene.idle(80);
        scene.overlay().showControls(util.vector().centerOf(1, 2, 2), Pointing.LEFT, 40)
                .rightClick().withItem(new ItemStack(Items.BRICK, 1)); //TODO: actual fuel pellet
        scene.world().setKineticSpeed(util.select().everywhere(), 16);

        scene.idle(80);
        scene.world().showSection(util.select().position(0, 1, 2), Direction.EAST);
        scene.idle(20);
        scene.overlay().showText(80)
                .colored(PonderPalette.WHITE)
                .pointAt(util.vector().centerOf(0, 1, 2))
                .placeNearTarget()
                .text("The RTG can be placed in any orientation");
        scene.idle(100);
        scene.world().hideSection(util.select().position(0, 1, 2), Direction.WEST);
        scene.idle(40);
        scene.overlay().showText(80)
                .attachKeyFrame()
                .colored(PonderPalette.WHITE)
                .pointAt(util.vector().blockSurface(new BlockPos(1, 2, 2), Direction.UP))
                .placeNearTarget()
                .text("The rotation direction can be configured at the top");
        scene.idle(90);
        scene.overlay().showControls(util.vector().blockSurface(new BlockPos(1, 2, 2), Direction.UP), Pointing.DOWN, 40)
                .rightClick();
        scene.world().setKineticSpeed(util.select().everywhere(), -16);
        scene.idle(50);
    }
}
