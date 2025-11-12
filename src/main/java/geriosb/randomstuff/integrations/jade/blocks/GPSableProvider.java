package geriosb.randomstuff.integrations.jade.blocks;

import geriosb.randomstuff.caps.IGPSableBlock;
import geriosb.randomstuff.integrations.IMegaStorageBlockEntity;
import geriosb.randomstuff.integrations.jade.GerioJadeAdditions;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum GPSableProvider implements
  IBlockComponentProvider {
  INSTANCE;

  @Override
  public void appendTooltip(
    ITooltip tooltip,
    BlockAccessor accessor,
    IPluginConfig config
  ) {
    if (accessor.getBlockEntity() instanceof IGPSableBlock rerere) {
        if (!(rerere.GetGPSLocation() == null)) {
            tooltip.add(Component.literal("Recorded: " +
                    rerere.GetGPSLocation().dimension.location().toString() + " " +
                    rerere.GetGPSLocation().pos.getX() + ", " +
                    rerere.GetGPSLocation().pos.getY() + ", " +
                    rerere.GetGPSLocation().pos.getZ() + " (" +
                    rerere.GetGPSLocation().side.getName() + ")"));
        }
    }
  }

  @Override
  public ResourceLocation getUid() {
    return GerioJadeAdditions.MEGASTORAGE;
  }
}
