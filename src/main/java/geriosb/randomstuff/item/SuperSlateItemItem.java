
package geriosb.randomstuff.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class SuperSlateItemItem extends SuperSlateItem {
	public SuperSlateItemItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
