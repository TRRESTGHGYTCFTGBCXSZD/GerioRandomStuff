
package geriosb.randomstuff.common.blocks;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LiquidBlock;

import geriosb.randomstuff.init.GeriorandomstuffModFluids;

public class DuplicatorBlock extends LiquidBlock {
	public DuplicatorBlock() {
		super(() -> GeriorandomstuffModFluids.DUPLICATOR.get(), BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).strength(100f).noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
	}
}
