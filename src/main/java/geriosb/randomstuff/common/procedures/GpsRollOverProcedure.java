package geriosb.randomstuff.common.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;

public class GpsRollOverProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z) {
		world.setBlock(BlockPos.containing(x, y, z), (world.getBlockState(BlockPos.containing(x, y, z))), 3);
		return InteractionResult.FAIL;
	}
}
