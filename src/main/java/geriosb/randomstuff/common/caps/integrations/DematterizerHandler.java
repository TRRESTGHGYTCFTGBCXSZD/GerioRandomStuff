package geriosb.randomstuff.common.caps.integrations;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DematterizerHandler implements IItemHandler, IFluidHandler {
    private List<ItemStack> idiomitem = new ArrayList<ItemStack>(); // even if the object was created, it will always be there to prevent crashes
    private List<FluidStack> idiomfluid = new ArrayList<FluidStack>(); // even if the object was created, it will always be there to prevent crashes

    @Override
    public int getTanks() {
        return 1; //actually it's a pandora's box since the interactions are really funny
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int i) {
        return null;
    }

    @Override
    public int getTankCapacity(int i) {
        return 1000; //actually it's a pandora's box since the interactions are really funny
    }

    @Override
    public boolean isFluidValid(int i, @NotNull FluidStack fluidStack) {
        return false;
    }

    @Override
    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
        return 0;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        return null;
    }

    @Override
    public @NotNull FluidStack drain(int i, FluidAction fluidAction) {
        return null;
    }

    @Override
    public int getSlots() {
        return 0;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int i) {
        return null;
    }

    @Override
    public @NotNull ItemStack insertItem(int i, @NotNull ItemStack itemStack, boolean b) {
        return null;
    }

    @Override
    public @NotNull ItemStack extractItem(int i, int i1, boolean b) {
        return null;
    }

    @Override
    public int getSlotLimit(int i) {
        return 0;
    }

    @Override
    public boolean isItemValid(int i, @NotNull ItemStack itemStack) {
        return false;
    }
}
