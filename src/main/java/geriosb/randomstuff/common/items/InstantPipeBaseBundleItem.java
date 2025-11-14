
package geriosb.randomstuff.common.items;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;

import java.util.List;

public class InstantPipeBaseBundleItem extends Item {
	public InstantPipeBaseBundleItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(Component.literal("Instantly moves all ... Nothing."));
		list.add(Component.literal("Bro, make your stuff!"));
		list.add(Component.literal("This item holds 56 Pipes."));
	}
}
