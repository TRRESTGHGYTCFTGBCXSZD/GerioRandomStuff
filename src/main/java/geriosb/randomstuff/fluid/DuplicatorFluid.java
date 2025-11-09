
package geriosb.randomstuff.fluid;

import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

import geriosb.randomstuff.GerioItems;
import geriosb.randomstuff.GerioFluids;
import geriosb.randomstuff.GerioBlocks;

public abstract class DuplicatorFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> GerioFluids.DUPLICATOR_TYPE.get(), () -> GerioFluids.DUPLICATOR.get(),
			() -> GerioFluids.FLOWING_DUPLICATOR.get()).explosionResistance(100f).bucket(() -> GerioItems.DUPLICATOR_BUCKET.get()).block(() -> (LiquidBlock) GerioBlocks.DUPLICATOR.get());

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
