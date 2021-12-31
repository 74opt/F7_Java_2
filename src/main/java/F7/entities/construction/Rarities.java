package F7.entities.construction;

import F7.entities.classes.Rarity;
import java.util.*;

public class Rarities { // rarities must equal 100 when summed
    public static Rarity common = new Rarity("Common", "^w", 60);

    public static Rarity uncommon = new Rarity("Uncommon", "^g", 20);

    public static Rarity rare = new Rarity("Rare", "^7", 13);

    public static Rarity exceptional = new Rarity("Exceptional", "^p", 6);

    public static Rarity godly = new Rarity("Godly", "^O", 1);

    public static ArrayList<Rarity> rarityArrayList = new ArrayList<>();

    public static void setRaritiesArrayList() throws Exception {
        if (common.getCHANCE() + uncommon.getCHANCE() + rare.getCHANCE() + exceptional.getCHANCE() + godly.getCHANCE() != 100) {
            throw new Exception("Invalid rarity probabilities.");
        }

        for (int i = 0; i < common.getCHANCE(); i++) {
            rarityArrayList.add(common);
        }

        for (int i = 0; i < uncommon.getCHANCE(); i++) {
            rarityArrayList.add(uncommon);
        }

        for (int i = 0; i < rare.getCHANCE(); i++) {
            rarityArrayList.add(rare);
        }

        for (int i = 0; i < exceptional.getCHANCE(); i++) {
            rarityArrayList.add(exceptional);
        }

        for (int i = 0; i < godly.getCHANCE(); i++) {
            rarityArrayList.add(godly);
        }
    }
}
