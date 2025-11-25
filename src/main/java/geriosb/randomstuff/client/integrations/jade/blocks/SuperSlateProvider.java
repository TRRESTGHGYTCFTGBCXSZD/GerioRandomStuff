package geriosb.randomstuff.client.integrations.jade.blocks;

import at.petrak.hexcasting.api.casting.iota.IotaType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import geriosb.randomstuff.common.blocks.integrations.SuperSlateBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import geriosb.randomstuff.client.integrations.jade.GerioJadeAdditions;

public enum SuperSlateProvider implements
  IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
  INSTANCE;

  @Override
  public void appendTooltip(
    ITooltip tooltip,
    BlockAccessor accessor,
    IPluginConfig config
  ) {
    if (accessor.getServerData().contains("SuperSlateIota")) {
        var datumTag = accessor.getServerData().getCompound("SuperSlateIota");
        if (datumTag != null) {
            var cmp = IotaType.getDisplay(datumTag);
			tooltip.add(Component.translatable("hexcasting.spelldata.onitem", cmp));
        }
    }
  }

  @Override
  public void appendServerData(CompoundTag data, BlockAccessor accessor) {
    SuperSlateBlockEntity furnace = (SuperSlateBlockEntity) accessor.getBlockEntity();
    data.put("SuperSlateIota", furnace.getIotaTag());
  }

  @Override
  public ResourceLocation getUid() {
    return GerioJadeAdditions.SUPER_SLATE;
  }
}