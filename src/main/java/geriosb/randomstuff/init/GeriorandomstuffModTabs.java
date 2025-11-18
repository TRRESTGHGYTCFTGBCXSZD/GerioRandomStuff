
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.init;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import geriosb.randomstuff.GeriorandomstuffMod;

public class GeriorandomstuffModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GeriorandomstuffMod.MODID);
	public static final RegistryObject<CreativeModeTab> TABNESS = REGISTRY.register("tabness",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.geriorandomstuff.tabness")).icon(() -> new ItemStack(GeriorandomstuffModItems.WORST_PICKAXE.get())).displayItems((parameters, tabData) -> {
				//tools
				tabData.accept(GeriorandomstuffModItems.WORST_PICKAXE.get());
				tabData.accept(GeriorandomstuffModItems.WORST_PICKAXE_BROKEN.get());
				tabData.accept(GeriorandomstuffModItems.GPS_DEVICE.get());
				tabData.accept(GeriorandomstuffModItems.SUPER_TOOL_HEAD.get());
				//pipes
				tabData.accept(GeriorandomstuffModItems.INSTANT_PIPE_BASE.get());
				tabData.accept(GeriorandomstuffModItems.INSTANT_PIPE_BASE_BUNDLE.get());
				tabData.accept(GeriorandomstuffModBlocks.INSTANT_ITEM_PIPE.get().asItem());
				tabData.accept(GeriorandomstuffModBlocks.INSTANT_LIQUID_PIPE.get().asItem());
				tabData.accept(GeriorandomstuffModBlocks.INSTANT_GAS_PIPE.get().asItem());
				//recipe blocks
				tabData.accept(GeriorandomstuffModBlocks.YONCRUSHER.get().asItem());
				//black magic
				tabData.accept(GeriorandomstuffModBlocks.MEGA_STORAGE.get().asItem());
                if (ModList.get().isLoaded("hexcasting")) tabData.accept(GeriorandomstuffModBlocks.SUPER_SLATE.get().asItem());
				tabData.accept(GeriorandomstuffModBlocks.REMOTE_STORAGE.get().asItem());
                if (ModList.get().isLoaded("hexcasting")) tabData.accept(GeriorandomstuffModBlocks.REMOTE_SLATE.get().asItem());
				tabData.accept(GeriorandomstuffModBlocks.TELEKINESIS_SETTER.get().asItem());
				tabData.accept(GeriorandomstuffModItems.DUPLICATOR_BUCKET.get());
				tabData.accept(GeriorandomstuffModBlocks.CONGEALED_DUPLICATOR.get().asItem());
				tabData.accept(GeriorandomstuffModItems.CONGEALED_DUPLICATOR_DIV_9.get());
				tabData.accept(GeriorandomstuffModBlocks.SILKER.get().asItem());
				tabData.accept(GeriorandomstuffModBlocks.LIQUID_DUPLICATOR_BLOCK.get().asItem());
				tabData.accept(GeriorandomstuffModItems.SUPERHEATED_DUPLICATOR.get());
                if (ModList.get().isLoaded("hexal")) tabData.accept(GeriorandomstuffModBlocks.EXPOSED_MOTE.get().asItem());
				//misc
				tabData.accept(GeriorandomstuffModBlocks.GERIO_BLOCK.get().asItem());
				tabData.accept(GeriorandomstuffModBlocks.UNBREAKABLE_BLOCK.get().asItem());
				tabData.accept(GeriorandomstuffModItems.SAMBADEJANEIRO.get());
				tabData.accept(GeriorandomstuffModItems.PASTE.get());
				tabData.accept(GeriorandomstuffModBlocks.GUSSUNHEAD.get().asItem());
			}).build());
}
