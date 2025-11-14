
package geriosb.randomstuff.common.blocks;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LiquidBlock;

import geriosb.randomstuff.init.GeriorandomstuffModFluids;

public class PlasmaBlock extends LiquidBlock {
	public PlasmaBlock() {
		super(() -> GeriorandomstuffModFluids.PLASMA.get(), BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).strength(100f).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).lightLevel(s -> 15).noCollission()
				.noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
	}
}
