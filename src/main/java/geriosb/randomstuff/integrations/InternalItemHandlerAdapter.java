package geriosb.randomstuff.integrations;

import appeng.api.inventories.InternalInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class InternalItemHandlerAdapter implements InternalInventory {
    private final IItemHandler handler;

    public InternalItemHandlerAdapter(IItemHandler handler) {
        this.handler = handler;
    }

    @Override
    public int size() {
        return handler.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return handler.getStackInSlot(slot);
    }

    @Override
    public void setItemDirect(int slot, ItemStack stack) {
        //if (handler instanceof MegaStorageItemHandler mega)
            //mega.setStackInSlot(slot, stack); //since this uses virtual inventory and nothing else (proprietary item system), this does nothing
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return handler.extractItem(slot, amount, simulate);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return handler.insertItem(slot, stack, simulate);
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }
}
