package geriosb.randomstuff.common.caps;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
import ram.talia.hexal.api.mediafieditems.MediafiedItemManager;
import ram.talia.hexal.common.blocks.entity.BlockEntityMediafiedStorage;
import ram.talia.hexal.common.lib.HexalBlockEntities;

public class MoteHandler implements IItemHandler {
    private BlockPos positiongetter = null;
    private Level level = null;

    public MoteHandler(Level currlevel) {
        level = currlevel;
    }

    public MoteHandler(Level currlevel, BlockPos newpos) {
        level = currlevel;
        positiongetter = newpos;
    }

    public void setPosition(BlockPos newpos){
        positiongetter = newpos;
    }

    // --- Standard IItemHandler methods ---

    @Override
    public int getSlots() {
        return HexalConfig.getServer().getMaxRecordsInMediafiedStorage();
    }

    @Override
	public ItemStack getStackInSlot(int slot) {
        if (positiongetter != null && level.getBlockEntity(positiongetter) instanceof BlockEntityMediafiedStorage watashino) {
            ItemRecord ait = watashino.getStoredItems().get(slot);
            return ait.toStack((int)ait.getCount());
        }
        return ItemStack.EMPTY;
	}

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        if (positiongetter != null && level.getBlockEntity(positiongetter) instanceof BlockEntityMediafiedStorage watashino) {
            if (!MediafiedItemManager.isStorageLoaded(watashino.getUuid())) return stack; // the item will not be consumed on
            if (watashino.isFull()) return stack; // the item will not be consumed on
            watashino.assignItem(new ItemRecord(stack));
            return ItemStack.EMPTY; // no remainder
        }
        return stack; // no conditions met
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (positiongetter != null && level.getBlockEntity(positiongetter) instanceof BlockEntityMediafiedStorage watashino) {
            ItemRecord ait = watashino.getStoredItems().get(slot);
            if (ait == null || ait.getCount() <= 0) return ItemStack.EMPTY;
            ItemRecord roro = ait.split(amount);
            if (ait.getCount() <= 0) watashino.getStoredItems().put(slot,null);
            return roro.toStack((int)roro.getCount());
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
