
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;

import geriosb.randomstuff.common.world.inventory.YoncrushMenu;
import geriosb.randomstuff.common.world.inventory.TelekinesisUpMenu;
import geriosb.randomstuff.common.world.inventory.SilkerUpMenu;
import geriosb.randomstuff.common.world.inventory.DupeMenu;
import geriosb.randomstuff.GeriorandomstuffMod;

public class GeriorandomstuffModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, GeriorandomstuffMod.MODID);
	public static final RegistryObject<MenuType<TelekinesisUpMenu>> TELEKINESIS_UP = REGISTRY.register("telekinesis_up", () -> IForgeMenuType.create(TelekinesisUpMenu::new));
	public static final RegistryObject<MenuType<DupeMenu>> DUPE = REGISTRY.register("dupe", () -> IForgeMenuType.create(DupeMenu::new));
	public static final RegistryObject<MenuType<SilkerUpMenu>> SILKER_UP = REGISTRY.register("silker_up", () -> IForgeMenuType.create(SilkerUpMenu::new));
	public static final RegistryObject<MenuType<YoncrushMenu>> YONCRUSH = REGISTRY.register("yoncrush", () -> IForgeMenuType.create(YoncrushMenu::new));
}
