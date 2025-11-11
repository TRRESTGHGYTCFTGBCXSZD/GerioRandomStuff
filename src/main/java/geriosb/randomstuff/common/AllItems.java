package geriosb.randomstuff.common;

import geriosb.randomstuff.GerioMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AllItems {

    public static final DeferredRegister<Item> REGISTERITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GerioMod.MODID);

    public static void registerItems(IEventBus eventBus) {
        REGISTERITEMS.register(eventBus);
    }
}
