package geriosb.randomstuff.common.caps;

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

    public MoteHandler() {
    }

    public void setPosition(BlockEntityMediafiedStorage newpos){
        positiongetter = newpos;
    }

    // --- Standard IItemHandler methods ---

    @Override
    public int getSlots() {
        return HexalConfig.getServer().getMaxRecordsInMediafiedStorage();
    }

    @Override
	public ItemStack getStackInSlot(int slot) {
        if (positiongetter != null) {
            ItemRecord ait = positiongetter.getStoredItems().get(slot);
            if (ait == null) return ItemStack.EMPTY; // another failsafe
            return ait.toStack((int)ait.getCount());
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
            ItemRecord ait = positiongetter.getStoredItems().get(slot);
            if (ait == null || ait.getCount() <= 0) return ItemStack.EMPTY;
            if (simulate) {
                ItemRecord roro = ait.copy(ait.getItem(),ait.getTag(),ait.getCount()).split(amount);
                return roro.toStack((int) roro.getCount());
            } else {
                ItemRecord roro = ait.split(amount);
                if (ait.getCount() <= 0) {
                    positiongetter.getStoredItems().remove(slot);
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
