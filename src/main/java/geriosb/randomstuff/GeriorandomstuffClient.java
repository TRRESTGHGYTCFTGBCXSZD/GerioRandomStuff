package geriosb.randomstuff;

import geriosb.randomstuff.client.blocks.SuperSlateRenderer;
import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static geriosb.randomstuff.GeriorandomstuffMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeriorandomstuffClient {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        BlockEntityRenderers.register(
                GeriorandomstuffModBlockEntities.SUPER_SLATE.get(),
                SuperSlateRenderer::new
        );
    }
}
