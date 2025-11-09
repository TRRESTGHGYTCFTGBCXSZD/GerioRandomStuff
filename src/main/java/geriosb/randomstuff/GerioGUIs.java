
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;

import geriosb.randomstuff.world.inventory.YoncrushMenu;
import geriosb.randomstuff.world.inventory.TelekinesisUpMenu;
import geriosb.randomstuff.world.inventory.SilkerUpMenu;
import geriosb.randomstuff.world.inventory.DupeMenu;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.minecraft.client.gui.screens.MenuScreens;

import geriosb.randomstuff.client.gui.*;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GerioGUIs {
	public static final DeferredRegister<MenuType<?>> REGISTERMENU = DeferredRegister.create(ForgeRegistries.MENU_TYPES, GeriorandomstuffMod.MODID);
	public static final RegistryObject<MenuType<TelekinesisUpMenu>> TELEKINESIS_UP = REGISTERMENU.register("telekinesis_up", () -> IForgeMenuType.create(TelekinesisUpMenu::new));
	public static final RegistryObject<MenuType<DupeMenu>> DUPE = REGISTERMENU.register("dupe", () -> IForgeMenuType.create(DupeMenu::new));
	public static final RegistryObject<MenuType<SilkerUpMenu>> SILKER_UP = REGISTERMENU.register("silker_up", () -> IForgeMenuType.create(SilkerUpMenu::new));
	public static final RegistryObject<MenuType<YoncrushMenu>> YONCRUSH = REGISTERMENU.register("yoncrush", () -> IForgeMenuType.create(YoncrushMenu::new));
	
	public static final DeferredRegister<CreativeModeTab> REGISTERTAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GeriorandomstuffMod.MODID);
	public static final RegistryObject<CreativeModeTab> TABNESS = REGISTERTAB.register("tabness",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.geriorandomstuff.tabness")).icon(() -> new ItemStack(GerioItems.WORST_PICKAXE.get())).displayItems((parameters, tabData) -> {
				// tools
				tabData.accept(GerioItems.WORST_PICKAXE.get());
				tabData.accept(GerioItems.WORST_PICKAXE_BROKEN.get());
				tabData.accept(GerioItems.SAMBADEJANEIRO.get());
				tabData.accept(GerioItems.SUPER_TOOL_HEAD.get());
				tabData.accept(GerioItems.PASTE.get());
				tabData.accept(GerioItems.GPS_DEVICE.get());
				// duplicators
				tabData.accept(GerioBlocks.CONGEALED_DUPLICATOR.get().asItem());
				tabData.accept(GerioItems.CONGEALED_DUPLICATOR_DIV_9.get());
				tabData.accept(GerioItems.DUPLICATOR_BUCKET.get());
				tabData.accept(GerioBlocks.LIQUID_DUPLICATOR_BLOCK.get().asItem());
				tabData.accept(GerioItems.SUPERHEATED_DUPLICATOR.get());
				// instant pipes
				tabData.accept(GerioItems.INSTANT_PIPE_BASE.get());
				tabData.accept(GerioItems.INSTANT_PIPE_BASE_BUNDLE.get());
				tabData.accept(GerioBlocks.INSTANT_ITEM_PIPE.get().asItem());
				tabData.accept(GerioBlocks.INSTANT_LIQUID_PIPE.get().asItem());
				tabData.accept(GerioBlocks.INSTANT_GAS_PIPE.get().asItem()); //mekanism exclusive
				// storage that handles 1024 types
				tabData.accept(GerioBlocks.MEGA_STORAGE.get().asItem());
				// some black magic
				tabData.accept(GerioBlocks.TELEKINESIS_SETTER.get().asItem());
				tabData.accept(GerioBlocks.SILKER.get().asItem());
				//tabData.accept(GerioItems.SUPER_SLATE.get());
				tabData.accept(GerioBlocks.YONCRUSHER.get().asItem());
				tabData.accept(GerioBlocks.REMOTE_STORAGE.get().asItem());
				tabData.accept(GerioBlocks.UNBREAKABLE_BLOCK.get().asItem());
				// something else
				tabData.accept(GerioBlocks.GUSSUNHEAD.get().asItem());
				tabData.accept(GerioBlocks.GERIO_BLOCK.get().asItem());
			}).withSearchBar().build());
	
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(TELEKINESIS_UP.get(), TelekinesisUpScreen::new);
			MenuScreens.register(DUPE.get(), DupeScreen::new);
			MenuScreens.register(SILKER_UP.get(), SilkerUpScreen::new);
			MenuScreens.register(YONCRUSH.get(), YoncrushScreen::new);
		});
	}
}
