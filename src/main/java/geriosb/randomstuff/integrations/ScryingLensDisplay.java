package geriosb.randomstuff.integrations;

import at.petrak.hexcasting.api.block.circle.BlockAbstractImpetus;
import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.client.ScryingLensOverlayRegistry;
import geriosb.randomstuff.GeriorandomstuffMod;
import geriosb.randomstuff.init.GeriorandomstuffModBlocks;
import geriosb.randomstuff.block.entity.SuperSlateBlockEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.material.MapColor;
import at.petrak.hexcasting.common.lib.HexItems;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.UnaryOperator;

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