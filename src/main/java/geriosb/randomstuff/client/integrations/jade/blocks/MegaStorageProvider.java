package geriosb.randomstuff.client.integrations.jade.blocks;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import geriosb.randomstuff.common.blocks.cheaters.IMegaStorageBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.IElement;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import geriosb.randomstuff.client.integrations.jade.GerioJadeAdditions;

public enum MegaStorageProvider  implements
  IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
  INSTANCE;

  @Override
  public void appendTooltip(
    ITooltip tooltip,
    BlockAccessor accessor,
    IPluginConfig config
  ) {
    if (accessor.getServerData().contains("BigStorage", Tag.TAG_LIST)) {
		ListTag list = accessor.getServerData().getList("BigStorage", Tag.TAG_COMPOUND);
      tooltip.add(
        Component.translatable(
          "gui.geriorandomstuff.uniqueitems",
          list.size()
        )
      );
		long elems = 9;
        for (Tag base : list) {
            if (!(base instanceof CompoundTag itemTag))
                continue;
			IElementHelper elements = IElementHelper.get();

			ItemStack stack = ItemStack.of(itemTag.getCompound("Stack"));
			String count = itemTag.getString("Count");
			
			if (count.length() >= 7) { // since the counts are string-based, we don't use log, power, or something like that, it's not a float, it's a string
				String[] measurions = {"Un","K","M","B","T","Qa","Qu","Sx","Sp","Oc","No","De","UDe","DDe","TDe","QDe","QuDe","SDe","SpDe","ODe","NDe","Vg","UVg","DVg","TVg","QVg","QuVg","SVg","SpVg","OVg","NVg",};
				
				StringBuffer recount = new StringBuffer(count.substring(0,4));
				if (((count.length() - 1) / 3) >= measurions.length) {
					recount = recount.insert(1,".");
					count = recount + "E+" + (count.length() - 1);
				} else {
					recount = recount.insert(((count.length() - 1)%3)+1,".");
					count = recount + (String) measurions[(count.length() - 1) / 3];
				}
			}
			IElement icon = elements.item(stack, 1f, count);
			if (elems++ == 9) {
				tooltip.add(icon);
				elems = 0;
			} else {
				tooltip.append(icon);
			}
        }
    }
  }

  @Override
  public void appendServerData(CompoundTag data, BlockAccessor accessor) {
    IMegaStorageBlockEntity furnace = (IMegaStorageBlockEntity) accessor.getBlockEntity();
    data.put("BigStorage", furnace.JadeProviderAdditions());
  }

  @Override
  public ResourceLocation getUid() {
    return GerioJadeAdditions.MEGASTORAGE;
  }
}
