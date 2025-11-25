package geriosb.randomstuff.client.integrations;

import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.client.ScryingLensOverlayRegistry;
import geriosb.randomstuff.GeriorandomstuffMod;
import geriosb.randomstuff.init.GeriorandomstuffModBlocks;
import geriosb.randomstuff.common.blocks.integrations.SuperSlateBlockEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.ItemStack;
import at.petrak.hexcasting.common.lib.HexItems;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = GeriorandomstuffMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ScryingLensDisplay {
    @SubscribeEvent
    public static void onCommonSetup(FMLClientSetupEvent event) {
		if (ModList.get().isLoaded("hexcasting")) {
        ScryingLensOverlayRegistry.addDisplayer(GeriorandomstuffModBlocks.SUPER_SLATE.get(),
            (lines, state, pos, observer, world, direction) -> {
                if (world.getBlockEntity(pos) instanceof SuperSlateBlockEntity tile) {
                    var iotaTag = tile.getIotaTag();
                    if (iotaTag != null) {
                        var display = IotaType.getDisplay(iotaTag);
                        lines.add(new Pair<>(new ItemStack(HexItems.SLATE), display));
                    }
                }
            });
		}
    }
}