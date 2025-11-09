package geriosb.randomstuff.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.gui.components.EditBox;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

import geriosb.randomstuff.GerioEnchantments;

public class SetTelekinesisProcedure {
	public static void execute(Entity entity, HashMap guistate) {
		if (entity == null || guistate == null)
			return;
		if (new Object() {
			double convert(String s) {
				try {
					return Double.parseDouble(s.trim());
				} catch (Exception e) {
				}
				return 0;
			}
		}.convert(guistate.containsKey("text:Number") ? ((EditBox) guistate.get("text:Number")).getValue() : "") > 0 && new Object() {
			double convert(String s) {
				try {
					return Double.parseDouble(s.trim());
				} catch (Exception e) {
				}
				return 0;
			}
		}.convert(guistate.containsKey("text:Number") ? ((EditBox) guistate.get("text:Number")).getValue() : "") <= 50 && Math.round(new Object() {
			double convert(String s) {
				try {
					return Double.parseDouble(s.trim());
				} catch (Exception e) {
				}
				return 0;
			}
		}.convert(guistate.containsKey("text:Number") ? ((EditBox) guistate.get("text:Number")).getValue() : "")) == new Object() {
			double convert(String s) {
				try {
					return Double.parseDouble(s.trim());
				} catch (Exception e) {
				}
				return 0;
			}
		}.convert(guistate.containsKey("text:Number") ? ((EditBox) guistate.get("text:Number")).getValue() : "")) {
			{
				Map<Enchantment, Integer> _enchantments = EnchantmentHelper
						.getEnchantments((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY));
				if (_enchantments.containsKey(GerioEnchantments.WATER_FALL_DAMAGE.get())) {
					_enchantments.remove(GerioEnchantments.WATER_FALL_DAMAGE.get());
					EnchantmentHelper.setEnchantments(_enchantments,
							(entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY));
				}
			}
			(entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY)
					.enchant(GerioEnchantments.WATER_FALL_DAMAGE.get(), (int) new Object() {
						double convert(String s) {
							try {
								return Double.parseDouble(s.trim());
							} catch (Exception e) {
							}
							return 0;
						}
					}.convert(guistate.containsKey("text:Number") ? ((EditBox) guistate.get("text:Number")).getValue() : ""));
		} else if (new Object() {
			double convert(String s) {
				try {
					return Double.parseDouble(s.trim());
				} catch (Exception e) {
				}
				return 0;
			}
		}.convert(guistate.containsKey("text:Number") ? ((EditBox) guistate.get("text:Number")).getValue() : "") == 0 && Math.round(new Object() {
			double convert(String s) {
				try {
					return Double.parseDouble(s.trim());
				} catch (Exception e) {
				}
				return 0;
			}
		}.convert(guistate.containsKey("text:Number") ? ((EditBox) guistate.get("text:Number")).getValue() : "")) == new Object() {
			double convert(String s) {
				try {
					return Double.parseDouble(s.trim());
				} catch (Exception e) {
				}
				return 0;
			}
		}.convert(guistate.containsKey("text:Number") ? ((EditBox) guistate.get("text:Number")).getValue() : "")) {
			{
				Map<Enchantment, Integer> _enchantments = EnchantmentHelper
						.getEnchantments((entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY));
				if (_enchantments.containsKey(GerioEnchantments.WATER_FALL_DAMAGE.get())) {
					_enchantments.remove(GerioEnchantments.WATER_FALL_DAMAGE.get());
					EnchantmentHelper.setEnchantments(_enchantments,
							(entity instanceof Player _plrSlotItem && _plrSlotItem.containerMenu instanceof Supplier _splr && _splr.get() instanceof Map _slt ? ((Slot) _slt.get(0)).getItem() : ItemStack.EMPTY));
				}
			}
		}
	}
}
