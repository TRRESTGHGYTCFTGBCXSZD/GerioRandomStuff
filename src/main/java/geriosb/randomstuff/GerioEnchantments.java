
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.enchantment.Enchantment;

import geriosb.randomstuff.enchantment.WaterFallDamageEnchantment;

public class GerioEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, GeriorandomstuffMod.MODID);
	public static final RegistryObject<Enchantment> WATER_FALL_DAMAGE = REGISTRY.register("water_fall_damage", () -> new WaterFallDamageEnchantment());
}
