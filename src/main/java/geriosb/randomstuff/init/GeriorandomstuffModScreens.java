
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import geriosb.randomstuff.client.gui.YoncrushScreen;
import geriosb.randomstuff.client.gui.TelekinesisUpScreen;
import geriosb.randomstuff.client.gui.SilkerUpScreen;
import geriosb.randomstuff.client.gui.DupeScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GeriorandomstuffModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(GeriorandomstuffModMenus.TELEKINESIS_UP.get(), TelekinesisUpScreen::new);
			MenuScreens.register(GeriorandomstuffModMenus.DUPE.get(), DupeScreen::new);
			MenuScreens.register(GeriorandomstuffModMenus.SILKER_UP.get(), SilkerUpScreen::new);
			MenuScreens.register(GeriorandomstuffModMenus.YONCRUSH.get(), YoncrushScreen::new);
		});
	}
}
