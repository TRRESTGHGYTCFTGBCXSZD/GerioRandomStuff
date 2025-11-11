
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package geriosb.randomstuff.common;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import geriosb.randomstuff.GerioMod;

public class AllCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GerioMod.MODID);
    public static final RegistryObject<CreativeModeTab> TABNESS = REGISTRY.register("tabness",
            () -> CreativeModeTab.builder().title(Component.translatable("item_group.geriorandomstuff.tabness")).icon(() -> new ItemStack(AllBlocks.INSTANT_ITEM_PIPE.asItem())).displayItems((parameters, tabData) -> {
                //tools
                //tabData.accept(AllItems.WORST_PICKAXE);
                //tabData.accept(AllItems.WORST_PICKAXE_BROKEN);
                //tabData.accept(AllItems.GPS_DEVICE);
                //tabData.accept(AllItems.SUPER_TOOL_HEAD);

                //pipes
                //tabData.accept(AllItems.INSTANT_PIPE_BASE);
                //tabData.accept(AllItems.INSTANT_PIPE_BASE_BUNDLE);
                tabData.accept(AllBlocks.INSTANT_ITEM_PIPE.asItem());
                //tabData.accept(AllBlocks.INSTANT_LIQUID_PIPE.asItem());
                //tabData.accept(AllBlocks.INSTANT_GAS_PIPE.asItem());

                //recipe blocks
                //tabData.accept(AllBlocks.YONCRUSHER.asItem());

                //black magic
                //tabData.accept(AllBlocks.MEGA_STORAGE.asItem());
                //tabData.accept(AllBlocks.SUPER_SLATE.asItem());
                //tabData.accept(AllBlocks.REMOTE_STORAGE.asItem());
                //tabData.accept(AllBlocks.TELEKINESIS_SETTER.asItem());
                //tabData.accept(AllItems.DUPLICATOR_BUCKET);
                //tabData.accept(AllBlocks.CONGEALED_DUPLICATOR.asItem());
                //tabData.accept(AllItems.CONGEALED_DUPLICATOR_DIV_9);
                //tabData.accept(AllBlocks.SILKER.asItem());
                //tabData.accept(AllBlocks.LIQUID_DUPLICATOR_BLOCK.asItem());
                //tabData.accept(AllItems.SUPERHEATED_DUPLICATOR);

                //misc
                //tabData.accept(AllBlocks.GERIO_BLOCK.asItem());
                //tabData.accept(AllBlocks.UNBREAKABLE_BLOCK.asItem());
                //tabData.accept(AllItems.SAMBADEJANEIRO);
                //tabData.accept(AllItems.PASTE);
                //tabData.accept(AllBlocks.GUSSUNHEAD.asItem());
            }).withSearchBar().build());
}