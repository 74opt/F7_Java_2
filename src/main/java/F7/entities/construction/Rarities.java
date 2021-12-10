package F7.entities.construction;

import F7.entities.classes.Rarity;
import java.util.*;

public class Rarities { // rarities must equal 100 when summed
    public static Rarity common = new Rarity("Common", 249, 60);

    public static Rarity uncommon = new Rarity("Uncommon", 46, 20);

    public static Rarity rare = new Rarity("Rare", 33, 13);

    public static Rarity exceptional = new Rarity("Exceptional", 99, 6);

    public static Rarity godly = new Rarity("Godly", 208, 1);

    public static ArrayList<Rarity> rarityArrayList = new ArrayList<Rarity>();

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
