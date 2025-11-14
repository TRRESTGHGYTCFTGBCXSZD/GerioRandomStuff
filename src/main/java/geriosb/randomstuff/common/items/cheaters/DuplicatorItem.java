
package geriosb.randomstuff.common.items.cheaters;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

import geriosb.randomstuff.init.GeriorandomstuffModFluids;

public class DuplicatorItem extends BucketItem {
	public DuplicatorItem() {
		super(GeriorandomstuffModFluids.DUPLICATOR, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON));
	}
}
