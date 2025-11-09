package geriosb.randomstuff.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.level.BlockEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import geriosb.randomstuff.GerioBlocks;

@Mod.EventBusSubscriber
public class UnbreakableBlockRecreateProcedure {
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
	}

	public static void execute(LevelAccessor world, double x, double y, double z) {
		execute(null, world, x, y, z);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == GerioBlocks.UNBREAKABLE_BLOCK.get()) {
			if (event != null && event.isCancelable()) {
				event.setCanceled(true);
			}
			world.setBlock(BlockPos.containing(x, y, z), GerioBlocks.UNBREAKABLE_BLOCK.get().defaultBlockState(), 3);
		}
	}
}
