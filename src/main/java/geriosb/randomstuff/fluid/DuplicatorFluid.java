
package geriosb.randomstuff.fluid;

import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

import geriosb.randomstuff.init.GeriorandomstuffModItems;
import geriosb.randomstuff.init.GeriorandomstuffModFluids;
import geriosb.randomstuff.init.GeriorandomstuffModFluidTypes;
import geriosb.randomstuff.init.GeriorandomstuffModBlocks;

public abstract class DuplicatorFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> GeriorandomstuffModFluidTypes.DUPLICATOR_TYPE.get(), () -> GeriorandomstuffModFluids.DUPLICATOR.get(),
			() -> GeriorandomstuffModFluids.FLOWING_DUPLICATOR.get()).explosionResistance(100f).bucket(() -> GeriorandomstuffModItems.DUPLICATOR_BUCKET.get()).block(() -> (LiquidBlock) GeriorandomstuffModBlocks.DUPLICATOR.get());

	private DuplicatorFluid() {
		super(PROPERTIES);
	}

	public static class Source extends DuplicatorFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends DuplicatorFluid {
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
		}

		public boolean isSource(FluidState state) {
			return false;
		}
	}
}
