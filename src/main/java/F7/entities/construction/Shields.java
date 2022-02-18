package F7.entities.construction;

import java.util.HashMap;
import F7.entities.classes.Rarity;
import F7.entities.classes.Shield;

public class Shields {
    private static Shield scrap = new Shield("Scrap Shield", 30, 2, 4, Rarities.COMMON);

    private static Shield basic = new Shield("Standard Issue Shield Mk. I", 70, 1, 3, Rarities.UNCOMMON);

    private static Shield military = new Shield("Military Grade Shield (GK-292)", 65, 4, 4, Rarities.RARE);

    private static Shield prototype = new Shield("Prototype FLK-11", 90, 3, 3, Rarities.EXCEPTIONAL);

    private static Shield aegis = new Shield("Aegis-12", 95, 2, 1, Rarities.GODLY);

    private static HashMap<Rarity, Shield[]> shieldHashMap = new HashMap<>();

    public static HashMap<Rarity, Shield[]> getShieldHashMap() {return shieldHashMap;}

    public static void setShieldHashMap() {
        shieldHashMap.put(Rarities.COMMON, new Shield[] {scrap});
        shieldHashMap.put(Rarities.UNCOMMON, new Shield[] {basic});
        shieldHashMap.put(Rarities.RARE, new Shield[] {military});
        shieldHashMap.put(Rarities.EXCEPTIONAL, new Shield[] {prototype});
        shieldHashMap.put(Rarities.GODLY, new Shield[] {aegis});
    }
}
