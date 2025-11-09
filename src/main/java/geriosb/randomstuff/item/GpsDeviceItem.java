
package geriosb.randomstuff.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraft.world.item.Rarity;

import geriosb.randomstuff.block.entity.RemoteStorageBlockEntity;

import geriosb.randomstuff.GerioBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import geriosb.randomstuff.caps.IGPSableBlock;
import geriosb.randomstuff.caps.GPSLocation;

public class GpsDeviceItem extends Item {

	public GpsDeviceItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Direction face = context.getClickedFace();
        ItemStack stack = context.getItemInHand();

        if (world.isClientSide) return InteractionResult.SUCCESS;

        // Sneak + Use: record target block and side
        if (player.isShiftKeyDown()) {
            storePosition(stack, pos, world, face);
            player.displayClientMessage(Component.literal("Position Recorded."), true);
            return InteractionResult.SUCCESS;
        }

        // Normal use: apply to RemoteStorageBlockEntity
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof IGPSableBlock remote) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("TargetX") && tag.contains("TargetY") && tag.contains("TargetZ") && tag.contains("TargetDir") && tag.contains("TargetDim")) {
                BlockPos targetPos = new BlockPos(
                        tag.getInt("TargetX"),
                        tag.getInt("TargetY"),
                        tag.getInt("TargetZ"));
                Direction dir = Direction.from3DDataValue(tag.getByte("TargetDir"));

                remote.SetPositionAndDimension(new GPSLocation(targetPos, dir, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("TargetDim")))));
                player.displayClientMessage(Component.literal("Information copied to remote block."), true);
                return InteractionResult.SUCCESS;
            } else {
                player.displayClientMessage(Component.literal("Please record the position first."), true);
                return InteractionResult.FAIL;
            }
        }

        return InteractionResult.PASS;
    }

    private void storePosition(ItemStack stack, BlockPos pos, Level level, Direction side) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("TargetX", pos.getX());
        tag.putInt("TargetY", pos.getY());
        tag.putInt("TargetZ", pos.getZ());
        tag.putByte("TargetDir", (byte) side.get3DDataValue());
        tag.putString("TargetDim", level.dimension().location().toString());
        stack.setTag(tag);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, java.util.List<Component> tooltip, net.minecraft.world.item.TooltipFlag flag) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("TargetX") && tag.contains("TargetY") && tag.contains("TargetZ") && tag.contains("TargetDir") && tag.contains("TargetDim")) {
            tooltip.add(Component.literal("Recorded: " +
                    tag.getString("TargetDim") + " " +
                    tag.getInt("TargetX") + ", " +
                    tag.getInt("TargetY") + ", " +
                    tag.getInt("TargetZ") + " (" +
                    Direction.from3DDataValue(tag.getByte("TargetDir")) + ")"));
        } else if (tag != null && tag.contains("TargetX") && tag.contains("TargetY") && tag.contains("TargetZ") && tag.contains("TargetDir")) {
            tooltip.add(Component.literal("Please Rerecord " +
                    tag.getInt("TargetX") + ", " +
                    tag.getInt("TargetY") + ", " +
                    tag.getInt("TargetZ") + " (" +
                    Direction.from3DDataValue(tag.getByte("TargetDir")) + ")"));
        } else {
            tooltip.add(Component.literal("Unrecorded"));
        }
    }

	@SubscribeEvent
	public static void DoingStuffOnThings(PlayerInteractEvent.RightClickBlock event) {
		if (event.getItemStack().getItem() instanceof GpsDeviceItem && event.getEntity().isShiftKeyDown()) {
			event.setUseBlock(Result.DENY);
		}
	}
}
