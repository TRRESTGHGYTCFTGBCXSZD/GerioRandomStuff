
package geriosb.randomstuff.common.caps;

import mekanism.common.capabilities.ItemCapabilityWrapper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.Reference;

public class InfiniteItemStorage implements IItemHandler {
    private NonNullList<ItemStack> refslot;

    public InfiniteItemStorage(NonNullList<ItemStack> referenceitem) {
        this.refslot = referenceitem;
    }

    @Override
    public int getSlots() {
        return 72;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        ItemStack stackro = refslot.get(0).copy();
        stackro.setCount(Integer.MAX_VALUE);
        return stackro;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) { // insertion is always refused
        return stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack stackro = refslot.get(0).copy();
        stackro.setCount(amount);
        return stackro;
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return false;
    }
}