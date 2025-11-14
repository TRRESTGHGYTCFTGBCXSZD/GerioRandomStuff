
package geriosb.randomstuff.common.blocks.cheaters;

import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Containers;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;
import java.util.Collections;

public class LiquidDuplicatorBlockBlock extends Block implements EntityBlock {
	public LiquidDuplicatorBlockBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(1f, 10f));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(Component.literal("Place With Bucket on this block to produce specific liquid infinitely."));
		list.add(Component.literal("Crouch+Place With Empty Hand on this block to reset."));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty())
			return dropsOriginal;
		return Collections.singletonList(new ItemStack(this, 1));
	}

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof LiquidDuplicatorBlockBlockEntity tankEntity)) return InteractionResult.PASS;

        ItemStack held = player.getItemInHand(hand);
        // If sneaking with empty hand, clear filter
        if (held.isEmpty()) {
        	if (player.isShiftKeyDown()) {
            	tankEntity.setFluid(FluidStack.EMPTY);
            	player.displayClientMessage(net.minecraft.network.chat.Component.literal("Stopped output"), true);
            	return InteractionResult.SUCCESS;
        	} else {
            	player.displayClientMessage(net.minecraft.network.chat.Component.literal("The output is: " + tankEntity.getFluid().getDisplayName().getString()), true);
            	return InteractionResult.SUCCESS;
        	}
        }

        // If holding a fluid container
        LazyOptional<IFluidHandlerItem> itemCap = held.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
        if (itemCap.isPresent()) {
            IFluidHandlerItem handler = itemCap.orElseThrow(IllegalStateException::new);
            FluidStack drained = handler.drain(Integer.MAX_VALUE, IFluidHandlerItem.FluidAction.SIMULATE);
            if (!drained.isEmpty()) {
                tankEntity.setFluid(drained);
                player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("Set output to: " + drained.getDisplayName().getString()),
                    true
                );
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new LiquidDuplicatorBlockBlockEntity(pos, state);
	}

	@Override
	public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
		super.triggerEvent(state, world, pos, eventID, eventParam);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof LiquidDuplicatorBlockBlockEntity be) {
				Containers.dropContents(world, pos, be);
				world.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, world, pos, newState, isMoving);
		}
	}
}
