package geriosb.randomstuff.common.caps;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import ram.talia.hexal.api.config.HexalConfig;
import ram.talia.hexal.api.mediafieditems.ItemRecord;
import ram.talia.hexal.common.blocks.entity.BlockEntityMediafiedStorage;
import ram.talia.hexal.common.lib.HexalBlockEntities;

public class MoteHandler implements IItemHandler {
    private final Map<String, ItemEntry> storage = new HashMap<>();
    private BlockPos positiongetter = null;
    private List<ItemStack> idiom = new ArrayList<ItemStack>(); // even if the object was created, it will always be there to prevent crashes

    public MoteHandler(int virtualSlots) {
    }

    /** Represents an item and its massive count */
    public static class ItemEntry {
        ItemStack stack;
        BigInteger count;
    }

    // --- Standard IItemHandler methods ---

    @Override
    public int getSlots() {
        return HexalConfig.getServer().getMaxRecordsInMediafiedStorage();
    }

    @Override
	public ItemStack getStackInSlot(int slot) {
        if (positiongetter != null && this.getLevel().getBlockEntity(targetloc.pos) instanceof BlockEntityMediafiedStorage watashino) {
            ItemRecord ait = watashino.getStoredItems().get(slot);
            return ait.toStack((int)ait.getCount());
        }
	}

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return ItemStack.EMPTY;

        String key = makeKey(stack);
        ItemEntry entry = storage.get(key);

        if (entry == null) {
            if (!simulate) {
                entry = new ItemEntry();
                entry.stack = stack.copy();
                entry.stack.setCount(1);
                entry.count = BigInteger.valueOf(stack.getCount());
                storage.put(key, entry);
            }
			RecalculateDisplayedSlots();
            return ItemStack.EMPTY;
        }

        // Merge stacks
        if (!ItemHandlerHelper.canItemStacksStack(entry.stack, stack))
            return stack;

        if (!simulate)
            entry.count = entry.count.add(BigInteger.valueOf(stack.getCount()));

		RecalculateDisplayedSlots();

        return ItemStack.EMPTY; // no remainder
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount <= 0) return ItemStack.EMPTY;

        //ItemEntry entry = storage.values().stream().skip(slot).findFirst().orElse(null); //instead of getting by slot, get it by idiom
		if (slot < 0 || slot >= idiom.size()) return ItemStack.EMPTY;
        String key = makeKey(idiom.get(slot));
        ItemEntry entry = storage.get(key);
        if (entry == null) return ItemStack.EMPTY;

        BigInteger removeCount = BigInteger.valueOf(amount);
        if (entry.count.compareTo(removeCount) < 0)
            removeCount = entry.count;

        ItemStack result = entry.stack.copy();
        result.setCount(removeCount.min(BigInteger.valueOf(Integer.MAX_VALUE)).intValue());

        if (!simulate) {
            entry.count = entry.count.subtract(removeCount);
            if (entry.count.signum() <= 0)
                storage.remove(makeKey(entry.stack));
        }

		RecalculateDisplayedSlots();

        return result;
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return !stack.isEmpty();
    }

    // --- Utility functions ---

    private String makeKey(ItemStack stack) {
        return stack.getItem().getDescriptionId() + "|" + (stack.hasTag() ? stack.getTag().toString() : "");
    }

    public Stream<ItemStack> getAllStacks() {
        return storage.values().stream().map(entry -> {
            ItemStack display = entry.stack.copy();
            display.setCount(entry.count.min(BigInteger.valueOf(Integer.MAX_VALUE)).intValue());
            return display;
        });
    }

    public BigInteger getTrueCount(ItemStack stack) {
        String key = makeKey(stack);
        ItemEntry entry = storage.get(key);
        return entry == null ? BigInteger.ZERO : entry.count;
    }

    // --- Persistence (NBT save/load) ---

    public ListTag saveToNBT() {
        ListTag list = new ListTag();

        for (ItemEntry entry : storage.values()) {
            CompoundTag itemTag = new CompoundTag();

            CompoundTag stackTag = new CompoundTag();
            entry.stack.save(stackTag); // Save full ItemStack
            itemTag.put("Stack", stackTag);

            itemTag.putString("Count", entry.count.toString()); // BigInteger as string
            list.add(itemTag);
        }

        return list;
    }

    public void loadFromNBT(ListTag list) {
        storage.clear();

        for (Tag base : list) {
            if (!(base instanceof CompoundTag itemTag))
                continue;

            ItemStack stack = ItemStack.of(itemTag.getCompound("Stack"));
            BigInteger count = new BigInteger(itemTag.getString("Count"));

            ItemEntry entry = new ItemEntry();
            entry.stack = stack;
            entry.count = count;
            storage.put(makeKey(stack), entry);
        }

		RecalculateDisplayedSlots();
    }
	//ae2 compat
	
    public long insertLarge(ItemStack stack, long size, boolean simulate) {
        if (stack.isEmpty()) return 0;

        String key = makeKey(stack);
        ItemEntry entry = storage.get(key);

        if (entry == null) {
            if (!simulate) {
                entry = new ItemEntry();
                entry.stack = stack.copy();
                entry.stack.setCount(1);
                entry.count = BigInteger.valueOf(size);
                storage.put(key, entry);
            }
			RecalculateDisplayedSlots();
            return 0;
        }

        // Merge stacks
        if (!ItemHandlerHelper.canItemStacksStack(entry.stack, stack))
            return stack.getCount();

        if (!simulate)
            entry.count = entry.count.add(BigInteger.valueOf(size));

		RecalculateDisplayedSlots();

        return 0; // no remainder
    }

    public long extractLarge(ItemStack stack, long amount, boolean simulate) {
        if (stack.isEmpty()) return 0;

        String key = makeKey(stack);
        ItemEntry entry = storage.get(key);
        if (entry == null) return 0;

        BigInteger removeCount = BigInteger.valueOf(amount);
        if (entry.count.compareTo(removeCount) < 0)
            removeCount = entry.count;

        if (!simulate) {
            entry.count = entry.count.subtract(removeCount);
            if (entry.count.signum() <= 0)
                storage.remove(makeKey(entry.stack));
        }

		RecalculateDisplayedSlots();

        return removeCount.longValue();
    }

	public List<ItemEntry> GetItemThingy() {
		List<ItemEntry> fucko = new ArrayList<ItemEntry>();
		for (ItemEntry entry : storage.values()) {
			fucko.add(entry);
		}
		return fucko;
	}
}
