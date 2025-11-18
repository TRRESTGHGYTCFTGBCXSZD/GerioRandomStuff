package geriosb.randomstuff.utils;

import ram.talia.hexal.api.config.HexalConfig;
import ram.talia.hexal.api.mediafieditems.ItemRecord;

import java.util.Map;

public class HexalUtils {
    public static void FixMoteInvalidity(Map<Integer, ItemRecord> MoteInventory){
        // this will fix the invalid null slots with valid slots
        for (Map.Entry<Integer, ItemRecord> elem : MoteInventory.entrySet()){
            int g = elem.getKey();
            ItemRecord ba = elem.getValue();
            MoteInventory.remove(elem.getKey());
            while (MoteInventory.get(g-1) != null) {
                g--;
                if (g == 0) break;
            }
            MoteInventory.put(g,ba);
        }
    }
}
