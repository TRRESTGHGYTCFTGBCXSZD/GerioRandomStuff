//A Complete Rewrite!?

package geriosb.randomstuff;

import geriosb.randomstuff.common.AllBlocks;
import geriosb.randomstuff.common.AllCreativeTabs;
import geriosb.randomstuff.common.AllItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import net.minecraft.core.registries.Registries;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.MinecraftForge;

@Mod("geriorandomstuff")
public class GerioMod {
	public static final Logger LOGGER = LogManager.getLogger(GerioMod.class);
	public static final String MODID = "geriorandomstuff";

	public GerioMod(){
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        AllBlocks.registerBlocks(bus);
        AllItems.registerItems(bus);
        AllCreativeTabs.REGISTRY.register(bus);
	}

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MODID, path);
    }
}
