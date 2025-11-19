package geriosb.randomstuff.common.caps;

import com.mojang.datafixers.util.Pair;
import geriosb.randomstuff.common.lib.GPSLocation;
import geriosb.randomstuff.utils.HexalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

import ram.talia.hexal.api.config.HexalConfig;
import ram.talia.hexal.api.mediafieditems.ItemRecord;
import ram.talia.hexal.api.mediafieditems.MediafiedItemManager;
import ram.talia.hexal.common.blocks.entity.BlockEntityMediafiedStorage;
import ram.talia.hexal.common.lib.HexalBlockEntities;

public class MoteHandler implements IItemHandler {
    private BlockEntityMediafiedStorage positiongetter = null;
    private int measuredslots = 0;
    private List<Pair<Integer,ItemStack>> idiom = new ArrayList<Pair<Integer,ItemStack>>(); // even if the object was created, it will always be there to prevent crashes

    public MoteHandler() {
    }

    public void setPosition(BlockEntityMediafiedStorage newpos){
        positiongetter = newpos;
    }

    public void RecalculateDisplayedSlots() {
        measuredslots = 1;
        idiom = new ArrayList<Pair<Integer,ItemStack>>();
        for (Map.Entry<Integer,ItemRecord> entry : positiongetter.getStoredItems().entrySet()) {
			/*if (entry.count.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0) {
					ItemStack display = entry.stack.copy();
					display.setCount(entry.count.intValue());
					idiom.add(display);
					measuredslots++;
			} else {
				BigInteger[] fullintsplusremain = entry.count.divideAndRemainder(BigInteger.valueOf(Integer.MAX_VALUE));
				long fullints = fullintsplusremain[0].longValue();
				int remain = fullintsplusremain[1].intValue();
				ItemStack display = entry.stack.copy();
				display.setCount(Integer.MAX_VALUE);
				for (long i=0; i<fullints; i++) {
					idiom.add(display); //points to same instance, for memory reduction
					measuredslots++;
				}
				if (remain>0) {
					display = entry.stack.copy();
					display.setCount(remain);
					idiom.add(display);
					measuredslots++;
				}
			}*/
            // only construct the items with same caps (max 2.1 billion)
            ItemStack display = entry.getValue().toStack().copy();
            display.setCount((int)entry.getValue().getCount());
            idiom.add(new Pair<>(entry.getKey(),display));
            measuredslots++;
        }
    }

    // --- Standard IItemHandler methods ---

    @Override
    public int getSlots() {
        if (positiongetter != null) {
            RecalculateDisplayedSlots();
            return measuredslots;
        }
        return 0;
    }

    @Override
	public ItemStack getStackInSlot(int slot) {
        if (positiongetter != null) {
            RecalculateDisplayedSlots();
            if (slot >= idiom.size()) return ItemStack.EMPTY;
            return idiom.get(slot).getSecond();
        }
        return ItemStack.EMPTY;
	}

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        if (positiongetter != null) {
            if (!MediafiedItemManager.isStorageLoaded(positiongetter.getUuid())) return stack; // the item will not be consumed on
            if (positiongetter.isFull()) return stack; // the item will not be consumed on
            if (!simulate) {
                positiongetter.assignItem(new ItemRecord(stack));
                //HexalUtils.FixMoteInvalidity(positiongetter.getStoredItems());
            }
            return ItemStack.EMPTY; // no remainder
        }
        return stack; // no conditions met
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (positiongetter != null) {
            RecalculateDisplayedSlots();
            if (slot >= idiom.size()) return ItemStack.EMPTY;
            ItemRecord ait = positiongetter.getStoredItems().get(idiom.get(slot).getFirst());
            if (ait == null || ait.getCount() <= 0) return ItemStack.EMPTY;
            if (simulate) {
                ItemRecord roro = ait.copy(ait.getItem(),ait.getTag(),ait.getCount()).split(amount);
                return roro.toStack((int) roro.getCount());
            } else {
                ItemRecord roro = ait.split(amount);
                if (ait.getCount() <= 0) {
                    positiongetter.removeStoredItem(idiom.get(slot).getFirst());
                    //HexalUtils.FixMoteInvalidity(positiongetter.getStoredItems());
                }
                return roro.toStack((int) roro.getCount());
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return !stack.isEmpty();
    }
}
