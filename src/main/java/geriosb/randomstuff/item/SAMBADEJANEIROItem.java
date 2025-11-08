
package geriosb.randomstuff.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

public class SAMBADEJANEIROItem extends RecordItem {
	public SAMBADEJANEIROItem() {
		super(0, () -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("geriorandomstuff:sambadejaneiro")), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 0);
	}
}
