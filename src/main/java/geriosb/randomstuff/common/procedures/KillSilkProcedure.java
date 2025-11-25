package geriosb.randomstuff.common.procedures;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;
import java.util.Map;

public class KillSilkProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			Map<Enchantment, Integer> _enchantments = EnchantmentHelper
					.getEnchantments((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY));
			if (_enchantments.containsKey(Enchantments.SILK_TOUCH)) {
				_enchantments.remove(Enchantments.SILK_TOUCH);
				EnchantmentHelper.setEnchantments(_enchantments, (entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY));
			}
		}
	}
}
