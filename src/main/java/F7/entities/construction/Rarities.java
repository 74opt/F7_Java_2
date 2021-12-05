package F7.entities.construction;

import F7.entities.classes.Rarity;
import java.util.*;

public class Rarities { // rarities must equal 100 when summed
    public static Rarity common = new Rarity("Common", 249, 60);

    public static Rarity uncommon = new Rarity("Uncommon", 46, 20);

    public static Rarity rare = new Rarity("Rare", 33, 13);

    public static Rarity exceptional = new Rarity("Exceptional", 99, 6);

    public static Rarity godly = new Rarity("Godly", 208, 1);

    public static ArrayList<Rarity> rarities = new ArrayList<Rarity>();

    public static void setRaritiesArrayList() throws Exception {
        if (common.CHANCE() + uncommon.CHANCE() + rare.CHANCE() + exceptional.CHANCE() + godly.CHANCE() != 100) {
            throw new Exception("Invalid rarity probabilities.");
        }

        for (int i = 0; i < common.CHANCE(); i++) {
            rarities.add(common);
        }

        for (int i = 0; i < uncommon.CHANCE(); i++) {
            rarities.add(uncommon);
        }

        for (int i = 0; i < rare.CHANCE(); i++) {
            rarities.add(rare);
        }

        for (int i = 0; i < exceptional.CHANCE(); i++) {
            rarities.add(exceptional);
        }

        for (int i = 0; i < godly.CHANCE(); i++) {
            rarities.add(godly);
        }
    }
}
