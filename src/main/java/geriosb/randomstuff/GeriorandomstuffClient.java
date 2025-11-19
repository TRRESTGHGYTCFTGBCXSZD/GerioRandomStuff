package geriosb.randomstuff;

import geriosb.randomstuff.client.blocks.SuperSlateRenderer;
import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;
import geriosb.randomstuff.init.GeriorandomstuffModBlocks;
import geriosb.randomstuff.init.GeriorandomstuffModHexActions;
import geriosb.randomstuff.init.GeriorandomstuffModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import static geriosb.randomstuff.GeriorandomstuffMod.MODID;
import static geriosb.randomstuff.GeriorandomstuffMod.rl;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeriorandomstuffClient {

    @SubscribeEvent
    public static void onModelRegister(ModelEvent.RegisterAdditional evt) {
        evt.register(rl( "block/super_slate_base"));
        evt.register(rl( "block/super_slate_face"));
        evt.register(rl( "block/super_slate_face_color"));
        evt.register(rl( "block/super_slate_face_normal"));
        evt.register(rl( "block/super_slate_face_huh"));
        evt.register(rl( "block/super_slate_face_dumb"));
        evt.register(rl( "block/super_slate_face_sad"));
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        if (ModList.get().isLoaded("hexcasting")) {
            BlockEntityRenderers.register(
                    GeriorandomstuffModBlockEntities.SUPER_SLATE.get(),
                    SuperSlateRenderer::new
            );
        }
    }
}
