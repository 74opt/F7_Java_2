package F7.entities.construction;

import F7.entities.classes.Rarity;
import java.util.ArrayList;

public class Rarities {
    public static final Rarity COMMON = new Rarity("Common", "^w", 30);

    public static final Rarity UNCOMMON = new Rarity("Uncommon", "^g", 25);

    public static final Rarity RARE = new Rarity("Rare", "^7", 20);

    public static final Rarity EXCEPTIONAL = new Rarity("Exceptional", "^p", 15);

    public static final Rarity GODLY = new Rarity("Godly", "^O", 10);

    private static ArrayList<Rarity> rarityArrayList = new ArrayList<>();

    public static ArrayList<Rarity> getRarityArrayList() {return rarityArrayList;}

    public static void setRaritiesArrayList() throws Exception {
        if (COMMON.CHANCE() + UNCOMMON.CHANCE() + RARE.CHANCE() + EXCEPTIONAL.CHANCE() + GODLY.CHANCE() != 100) {
            throw new Exception("Invalid rarity probabilities.");
        }

        for (int i = 0; i < COMMON.CHANCE(); i++) {
            rarityArrayList.add(COMMON);
        }

        for (int i = 0; i < UNCOMMON.CHANCE(); i++) {
            rarityArrayList.add(UNCOMMON);
        }

        for (int i = 0; i < RARE.CHANCE(); i++) {
            rarityArrayList.add(RARE);
        }

        for (int i = 0; i < EXCEPTIONAL.CHANCE(); i++) {
            rarityArrayList.add(EXCEPTIONAL);
        }

        for (int i = 0; i < GODLY.CHANCE(); i++) {
            rarityArrayList.add(GODLY);
        }
    }
}
