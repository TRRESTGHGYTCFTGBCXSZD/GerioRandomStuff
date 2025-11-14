/**
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside geriosb.randomstuff as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/

//this is made with chatgpt
package geriosb.randomstuff.common.caps;

import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fluids.FluidStack;

public class InfiniteLiquidTank extends FluidTank {

    public InfiniteLiquidTank(FluidStack infiniteFluid) {
        // capacity doesn’t matter, but set something
        super(Integer.MAX_VALUE);
        this.setFluid(infiniteFluid.copy());
        this.getFluid().setAmount(Integer.MAX_VALUE);
    }

    public InfiniteLiquidTank(int Liquid) {
        // capacity doesn’t matter, but set something
        super(Integer.MAX_VALUE);
    }

    @Override
    public void setFluid(FluidStack resource) {
        super.setFluid(resource);
        if (!getFluid().isEmpty()) this.getFluid().setAmount(Integer.MAX_VALUE);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        // optional: if you want to "absorb" anything but stay infinite
        if (resource.isFluidEqual(this.getFluid())) {
            return resource.getAmount();
        }
        return 0;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        // Always return the same fluid (never reduce)
        if (!getFluid().isEmpty()) this.getFluid().setAmount(Integer.MAX_VALUE);
        if (getFluid().isEmpty()) return FluidStack.EMPTY;
        FluidStack copy = getFluid().copy();
        copy.setAmount(maxDrain);
        return copy;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isFluidEqual(getFluid())) {
            FluidStack copy = resource.copy();
            return copy;
        }
        return FluidStack.EMPTY;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return stack.isFluidEqual(this.getFluid());
    }

    @Override
    public int getFluidAmount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getCapacity() {
        return Integer.MAX_VALUE;
    }
}