
package geriosb.randomstuff.common.items.cheaters;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class GerioPipeItem extends BlockItem {

	public GerioPipeItem(Block p_i48527_1_, Properties p_i48527_2_) {
		super(p_i48527_1_, p_i48527_2_);
	}

	@SubscribeEvent
	public static void IgnoreInteractionOnPipes(PlayerInteractEvent.RightClickBlock event) {
		if (event.getItemStack()
			.getItem() instanceof GerioPipeItem)
			event.setUseBlock(Result.DENY);
	}

}