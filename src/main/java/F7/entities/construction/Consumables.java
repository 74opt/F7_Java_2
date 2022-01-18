package F7.entities.construction;

import java.util.*;
import F7.entities.classes.*;

public class Consumables {
    private static final Consumable medkit = new Consumable("Medkit", 0, Rarities.getCommon()); // this is the only one that doesnt rely on a timer or boolean, just heals in an instant

    private static final Consumable smoke = new Consumable("Smoke Grenade", 10, Rarities.getCommon());

    private static final Consumable corrosive = new Consumable("Corrosive Acid Grenade", 5, Rarities.getUncommon());

    private static final Consumable target = new Consumable("Targeting-Assistance Chip", 8, Rarities.getUncommon());

    private static final Consumable amplifier = new Consumable("Damage Amplifier", 6, Rarities.getRare());

    private static final Consumable flashbang = new Consumable("Flashbang", 5, Rarities.getRare());

    public static Consumable getMedkit() {return medkit;}

    public static Consumable getSmoke() {return smoke;}

    public static Consumable getCorrosive() {return corrosive;}

    public static Consumable getTarget() {return target;}

    public static Consumable getAmplifier() {return amplifier;}

    public static Consumable getFlashbang() {return flashbang;}

    public static HashMap<Rarity, Consumable[]> consumableHashMap = new HashMap<>();

    public static HashMap<Rarity, Consumable[]> getConsumableHashMap() {return consumableHashMap;}

    public static void setConsumableHashMap() {
        consumableHashMap.put(Rarities.getCommon(), new Consumable[] {medkit, smoke});
        consumableHashMap.put(Rarities.getUncommon(), new Consumable[] {corrosive, target});
        consumableHashMap.put(Rarities.getRare(), new Consumable[] {amplifier, flashbang});
    }
}
