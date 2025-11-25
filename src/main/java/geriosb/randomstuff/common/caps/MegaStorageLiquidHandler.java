//package geriosb.randomstuff.common.caps;
//
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.nbt.ListTag;
//import net.minecraft.nbt.Tag;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.fluids.FluidStack;
//import net.minecraftforge.fluids.capability.IFluidHandler;
//import net.minecraftforge.items.IItemHandler;
//import net.minecraftforge.items.ItemHandlerHelper;
//import org.jetbrains.annotations.NotNull;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Stream;
//
//public class MegaStorageLiquidHandler implements IFluidHandler {
//    private final Map<String, FluidEntry> storage = new HashMap<>();
//    private int measuredslots = 1;
//    private int maxslots;
//    private List<FluidStack> idiom = new ArrayList<FluidStack>(); // even if the object was created, it will always be there to prevent crashes
//
//    public MegaStorageLiquidHandler(int virtualSlots) {
//        maxslots = virtualSlots;
//    }
//
//    /** Represents an item and its massive count */
//    public static class FluidEntry {
//        public FluidStack stack;
//        public BigInteger count;
//    }
//
//    // --- Standard IItemHandler methods ---
//
//    @Override
//    public int getTanks() {
//        return measuredslots;
//    }
//
//	public void RecalculateDisplayedSlots() {
//		measuredslots = 1;
//		idiom = new ArrayList<FluidStack>();
//		for (FluidEntry entry : storage.values()) {
//			/*if (entry.count.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) < 0) {
//					ItemStack display = entry.stack.copy();
//					display.setCount(entry.count.intValue());
//					idiom.add(display);
//					measuredslots++;
//			} else {
//				BigInteger[] fullintsplusremain = entry.count.divideAndRemainder(BigInteger.valueOf(Integer.MAX_VALUE));
//				long fullints = fullintsplusremain[0].longValue();
//				int remain = fullintsplusremain[1].intValue();
//				ItemStack display = entry.stack.copy();
//				display.setCount(Integer.MAX_VALUE);
//				for (long i=0; i<fullints; i++) {
//					idiom.add(display); //points to same instance, for memory reduction
//					measuredslots++;
//				}
//				if (remain>0) {
//					display = entry.stack.copy();
//					display.setCount(remain);
//					idiom.add(display);
//					measuredslots++;
//				}
//			}*/
//			// only construct the items with same caps (max 2.1 billion)
//            FluidStack display = entry.stack.copy();
//			display.setAmount(entry.count.min(BigInteger.valueOf(Integer.MAX_VALUE)).intValue());
//			idiom.add(display);
//			measuredslots++;
//		}
//	}
//
//    @Override
//	public FluidStack getFluidInTank(int slot) {
//		if (idiom == null) return FluidStack.EMPTY; // so the game won't freak out if the idiom is null
//		if (slot < 0 || slot >= idiom.size()) return FluidStack.EMPTY;
//		return idiom.get(slot);
//	}
//
//    @Override
//    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
//        return 0;
//    }
//
//    @Override
//    public @NotNull FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
//        return null;
//    }
//
//    @Override
//    public @NotNull FluidStack drain(int i, FluidAction fluidAction) {
//        return null;
//    }
//
//    @Override
//    public int getTankCapacity(int slot) {
//        return Integer.MAX_VALUE;
//    }
//
//    @Override
//    public boolean isFluidValid(int slot, FluidStack stack) {
//        return !stack.isEmpty();
//    }
//
//
//    // --- Utility functions ---
//
//    private String makeKey(FluidStack stack) {
//        return stack.getFluid().getFluidType().getDescriptionId() + "|" + (stack.hasTag() ? stack.getTag().toString() : "");
//    }
//
//    public Stream<FluidStack> getAllStacks() {
//        return storage.values().stream().map(entry -> {
//            FluidStack display = entry.stack.copy();
//            display.setAmount(entry.count.min(BigInteger.valueOf(Integer.MAX_VALUE)).intValue());
//            return display;
//        });
//    }
//
//    public BigInteger getTrueCount(FluidStack stack) {
//        String key = makeKey(stack);
//        FluidEntry entry = storage.get(key);
//        return entry == null ? BigInteger.ZERO : entry.count;
//    }
//
//    // --- Persistence (NBT save/load) ---
//
//    public ListTag saveToNBT() {
//        ListTag list = new ListTag();
//
//        for (FluidEntry entry : storage.values()) {
//            CompoundTag itemTag = new CompoundTag();
//
//            CompoundTag stackTag = new CompoundTag();
//            entry.stack.setTag(stackTag); // Save full ItemStack
//            itemTag.put("Stack", stackTag);
//
//            itemTag.putString("Count", entry.count.toString()); // BigInteger as string
//            list.add(itemTag);
//        }
//
//        return list;
//    }
//
//    public void loadFromNBT(ListTag list) {
//        storage.clear();
//
//        for (Tag base : list) {
//            if (!(base instanceof CompoundTag itemTag))
//                continue;
//
//            FluidStack stack = new FluidStack(itemTag.getCompound("Stack"));
//            BigInteger count = new BigInteger(itemTag.getString("Count"));
//
//            FluidEntry entry = new FluidEntry();
//            entry.stack = stack;
//            entry.count = count;
//            storage.put(makeKey(stack), entry);
//        }
//
//		RecalculateDisplayedSlots();
//    }
//	//ae2 compat
//
//    public long insertLarge(ItemStack stack, long size, boolean simulate) {
//        if (stack.isEmpty()) return 0;
//
//        String key = makeKey(stack);
//        ItemEntry entry = storage.get(key);
//
//        if (entry == null) {
//            if (!simulate) {
//                entry = new ItemEntry();
//                entry.stack = stack.copy();
//                entry.stack.setCount(1);
//                entry.count = BigInteger.valueOf(size);
//                storage.put(key, entry);
//            }
//			RecalculateDisplayedSlots();
//            return 0;
//        }
//
//        // Merge stacks
//        if (!ItemHandlerHelper.canItemStacksStack(entry.stack, stack))
//            return stack.getCount();
//
//        if (!simulate)
//            entry.count = entry.count.add(BigInteger.valueOf(size));
//
//		RecalculateDisplayedSlots();
//
//        return 0; // no remainder
//    }
//
//    public long extractLarge(ItemStack stack, long amount, boolean simulate) {
//        if (stack.isEmpty()) return 0;
//
//        String key = makeKey(stack);
//        ItemEntry entry = storage.get(key);
//        if (entry == null) return 0;
//
//        BigInteger removeCount = BigInteger.valueOf(amount);
//        if (entry.count.compareTo(removeCount) < 0)
//            removeCount = entry.count;
//
//        if (!simulate) {
//            entry.count = entry.count.subtract(removeCount);
//            if (entry.count.signum() <= 0)
//                storage.remove(makeKey(entry.stack));
//        }
//
//		RecalculateDisplayedSlots();
//
//        return removeCount.longValue();
//    }
//
//	public List<ItemEntry> GetItemThingy() {
//		List<ItemEntry> fucko = new ArrayList<ItemEntry>();
//		for (ItemEntry entry : storage.values()) {
//			fucko.add(entry);
//		}
//		return fucko;
//	}
//}
//