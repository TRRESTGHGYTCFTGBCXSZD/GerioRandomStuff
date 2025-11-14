package geriosb.randomstuff.common.items.cheaters;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.hexcasting.api.utils.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import static at.petrak.hexcasting.api.HexAPI.modLoc;

import geriosb.randomstuff.common.blocks.cheaters.SuperSlateBlockEntity;
import net.minecraft.world.item.TooltipFlag;
import java.util.List;

public class SuperSlateItem extends BlockItem implements IotaHolderItem {
    public static final ResourceLocation WRITTEN_PRED = modLoc("written");

    public SuperSlateItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public Component getName(ItemStack pStack) {
        var beTag = NBTHelper.getOrCreateCompound(pStack, "BlockEntityTag");
        var key = "block." + HexAPI.MOD_ID + ".slate." + (!beTag.isEmpty() ? "written" : "blank");
        return Component.translatable(key);
    }

    @Override
    public @Nullable
    CompoundTag readIotaTag(ItemStack stack) {
        var bet = NBTHelper.getCompound(stack, "BlockEntityTag");

        if (bet == null || !bet.contains(SuperSlateBlockEntity.TAG_IOTA, Tag.TAG_COMPOUND)) {
            return null;
        }

        return bet.getCompound(SuperSlateBlockEntity.TAG_IOTA);
    }

    @Override
    public boolean writeable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canWrite(ItemStack stack, Iota datum) {
        return true;
    }

    @Override
    public void writeDatum(ItemStack stack, Iota datum) {
            if (datum == null) {
                var beTag = NBTHelper.getOrCreateCompound(stack, "BlockEntityTag");
                beTag.remove(SuperSlateBlockEntity.TAG_IOTA);
                if (beTag.isEmpty()) {
                    NBTHelper.remove(stack, "BlockEntityTag");
                }
            } else {
                var beTag = NBTHelper.getOrCreateCompound(stack, "BlockEntityTag");
                beTag.put(SuperSlateBlockEntity.TAG_IOTA, IotaType.serialize(datum));
            }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents,
        TooltipFlag pIsAdvanced) {
        IotaHolderItem.appendHoverText(this, pStack, pTooltipComponents, pIsAdvanced);
    }
}