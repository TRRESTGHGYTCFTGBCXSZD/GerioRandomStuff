
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.enchantment.Enchantment;

import geriosb.randomstuff.common.enchantment.WaterFallDamageEnchantment;
import geriosb.randomstuff.GeriorandomstuffMod;

public class GeriorandomstuffModEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, GeriorandomstuffMod.MODID);
	public static final RegistryObject<Enchantment> WATER_FALL_DAMAGE = REGISTRY.register("water_fall_damage", () -> new WaterFallDamageEnchantment());
}
