package F7.entities.construction;

import java.util.*;
import F7.entities.classes.*;

public class Consumables {
    public static Consumable medkit = new Consumable("Medkit", 0, Rarities.common); // this is the only one that doesnt rely on a timer or boolean, just heals in an instant

    public static Consumable smoke = new Consumable("Smoke Grenade", 3, Rarities.common);

    public static Consumable corrosive = new Consumable("Corrosive Acid Grenade", 2, Rarities.uncommon);

    public static Consumable target = new Consumable("Targeting-Assistance Chip", 1, Rarities.uncommon);

    public static Consumable amplifier = new Consumable("Damage Amplifier", 2, Rarities.rare);

    public static Consumable flashbang = new Consumable("Flashbang", 1, Rarities.rare);

    public static HashMap<Rarity, Consumable[]> consumableHashMap = new HashMap<>();

    public static HashMap<Rarity, Consumable[]> getConsumableHashMap() {return consumableHashMap;}

    public static void setConsumableHashMap() {
        consumableHashMap.put(Rarities.common, new Consumable[] {medkit, smoke});
        consumableHashMap.put(Rarities.uncommon, new Consumable[] {corrosive, target});
        consumableHashMap.put(Rarities.rare, new Consumable[] {amplifier, flashbang});
    }
}
