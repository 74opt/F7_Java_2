package F7.entities.construction;

import F7.entities.classes.Rarity;
import java.util.ArrayList;

public class Rarities {
    public static final Rarity common = new Rarity("Common", "^w", 30);

    public static final Rarity uncommon = new Rarity("Uncommon", "^g", 25);

    public static final Rarity rare = new Rarity("Rare", "^7", 20);

    public static final Rarity exceptional = new Rarity("Exceptional", "^p", 15);

    public static final Rarity godly = new Rarity("Godly", "^O", 10);

    public static Rarity getCommon() {return common;}

    public static Rarity getUncommon() {return uncommon;}

    public static Rarity getRare() {return rare;}

    public static Rarity getExceptional() {return exceptional;}

    public static Rarity getGodly() {return godly;}

    private static ArrayList<Rarity> rarityArrayList = new ArrayList<>();

    public static ArrayList<Rarity> getRarityArrayList() {return rarityArrayList;}

    public static void setRaritiesArrayList() throws Exception {
        if (common.CHANCE() + uncommon.CHANCE() + rare.CHANCE() + exceptional.CHANCE() + godly.CHANCE() != 100) {
            throw new Exception("Invalid rarity probabilities.");
        }

        for (int i = 0; i < common.CHANCE(); i++) {
            rarityArrayList.add(common);
        }

        for (int i = 0; i < uncommon.CHANCE(); i++) {
            rarityArrayList.add(uncommon);
        }

        for (int i = 0; i < rare.CHANCE(); i++) {
            rarityArrayList.add(rare);
        }

        for (int i = 0; i < exceptional.CHANCE(); i++) {
            rarityArrayList.add(exceptional);
        }

        for (int i = 0; i < godly.CHANCE(); i++) {
            rarityArrayList.add(godly);
        }
    }
}
