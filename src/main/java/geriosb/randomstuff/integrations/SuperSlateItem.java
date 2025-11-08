package geriosb.randomstuff.integrations;

import at.petrak.hexcasting.annotations.SoftImplement;
import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.PatternIota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.hexcasting.api.utils.NBTHelper;
import at.petrak.hexcasting.client.gui.PatternTooltipComponent;
import at.petrak.hexcasting.common.blocks.circles.BlockEntitySlate;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import at.petrak.hexcasting.common.misc.PatternTooltip;
import at.petrak.hexcasting.interop.inline.InlinePatternData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static at.petrak.hexcasting.api.HexAPI.modLoc;

public class SuperSlateItem extends BlockItem implements IotaHolderItem {
    public static final ResourceLocation WRITTEN_PRED = modLoc("written");

    public SuperSlateItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public Component getName(ItemStack pStack) {
        var key = "block." + HexAPI.MOD_ID + ".slate." + (hasPattern(pStack) ? "written" : "blank");
        Component patternText = getPattern(pStack)
            .map(pat -> Component.literal(": ").append(new InlinePatternData(pat).asText(false)))
            .orElse(Component.literal(""));
        return Component.translatable(key).append(patternText);
    }

    public static Optional<HexPattern> getPattern(ItemStack stack){
        var bet = NBTHelper.getCompound(stack, "BlockEntityTag");

        if (bet != null && bet.contains(BlockEntitySlate.TAG_PATTERN, Tag.TAG_COMPOUND)) {
            var patTag = bet.getCompound(BlockEntitySlate.TAG_PATTERN);
            if (!patTag.isEmpty()) {
                var pattern = HexPattern.fromNBT(patTag);
                return Optional.of(pattern);
            }
        }
        return Optional.empty();
    }

    public static boolean hasPattern(ItemStack stack) {
        return getPattern(stack).isPresent();
    }

    @SoftImplement("IForgeItem")
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!hasPattern(stack)) {
            NBTHelper.remove(stack, "BlockEntityTag");
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!hasPattern(pStack)) {
            NBTHelper.remove(pStack, "BlockEntityTag");
        }
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
}