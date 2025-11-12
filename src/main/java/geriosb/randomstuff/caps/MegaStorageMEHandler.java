package geriosb.randomstuff.caps;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.*;
import appeng.api.storage.*;

public class MegaStorageMEHandler implements MEStorage {
    private final MegaStorageItemHandler link;

    public MegaStorageMEHandler(MegaStorageItemHandler backtrack) {
		link = backtrack;
    }

    @Override
    public long insert(AEKey what, long amount, Actionable mode, IActionSource source) {
		if (what instanceof AEItemKey itemina) {
			ItemStack stack = itemina.toStack();
			long remaining = link.insertLarge(stack,amount,mode == Actionable.SIMULATE);
			return amount - remaining;
		}
		link.RecalculateDisplayedSlots();
		return 0;
    }

    @Override
    public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {
		if (what instanceof AEItemKey itemina) {
			ItemStack stack = itemina.toStack();
			return link.extractLarge(stack,amount,mode == Actionable.SIMULATE);
		}
		link.RecalculateDisplayedSlots();
		return 0;
    }

	@Override
    public void getAvailableStacks(KeyCounter out) {
		List<MegaStorageItemHandler.ItemEntry> idiom = link.GetItemThingy();
		for (MegaStorageItemHandler.ItemEntry entry : idiom) {
			out.add(AEItemKey.of(entry.stack),entry.count.min(BigInteger.valueOf(Long.MAX_VALUE)).longValue());
		}
    }

    @Override
    public Component getDescription() {
        return Component.empty();
    }
}
