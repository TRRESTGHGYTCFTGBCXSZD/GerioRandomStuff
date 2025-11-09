package geriosb.randomstuff.procedures;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import geriosb.randomstuff.GerioItems;

public class WorstPickaxeDestroyCheckProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
			if (entity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(GerioItems.WORST_PICKAXE_BROKEN.get());
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
			if (entity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(GerioItems.WORST_PICKAXE_BROKEN.get());
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
		}
	}
}
