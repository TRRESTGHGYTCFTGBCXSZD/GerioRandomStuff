package geriosb.randomstuff.integrations;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

import geriosb.randomstuff.caps.MegaStorageItemHandler;
import geriosb.randomstuff.init.GeriorandomstuffModBlockEntities;
import net.minecraft.core.Direction;


public interface IMegaStorageBlockEntity { // bro you just created interface just for 2 block entities
	ListTag JadeProviderAdditions();
}
