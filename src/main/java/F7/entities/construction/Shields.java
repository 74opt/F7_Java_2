package F7.entities.construction;

import java.util.HashMap;

import F7.entities.classes.Rarity;
import F7.entities.classes.Shield;

public class Shields {
    public static Shield scrap = new Shield("Scrap Shield", 30, 2, 4, Rarities.common);

    public static Shield basic = new Shield("Standard Issue Shield Mk. I", 70, 1, 3, Rarities.uncommon);

    public static Shield military = new Shield("Military Grade Shield (GK-292)", 65, 4, 4, Rarities.rare);

    public static Shield prototype = new Shield("Prototype FLK-11", 90, 3, 3, Rarities.exceptional);

    public static Shield aegis = new Shield("Aegis-12", 95, 2, 1, Rarities.godly);

    public static HashMap<Rarity, Shield[]> shieldHashMap = new HashMap<>();

    public static void setShieldHashMap() {
        shieldHashMap.put(Rarities.common, new Shield[] {scrap});
        shieldHashMap.put(Rarities.uncommon, new Shield[] {basic});
        shieldHashMap.put(Rarities.rare, new Shield[] {military});
        shieldHashMap.put(Rarities.exceptional, new Shield[] {prototype});
        shieldHashMap.put(Rarities.godly, new Shield[] {aegis});
    }
}
