package geriosb.randomstuff.common.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class DaegariBlockItem extends BlockItem {
    public DaegariBlockItem(Block block, Properties props) {
        super(block, props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);

        // Try to equip if head is empty
        if (head.isEmpty()) {
            if (!level.isClientSide) {
                player.setItemSlot(EquipmentSlot.HEAD, held.copyWithCount(1));
                if (!player.isCreative()) held.shrink(1);
            }
            return InteractionResultHolder.sidedSuccess(held, level.isClientSide);
        }

        return InteractionResultHolder.pass(held);
    }

    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        // Explicitly declare head slot for dispensers and equip commands
        return EquipmentSlot.HEAD;
    }
}
