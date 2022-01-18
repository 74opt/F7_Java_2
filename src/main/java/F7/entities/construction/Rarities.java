package F7.entities.construction;

import F7.entities.classes.Rarity;
import java.util.ArrayList;

public class Rarities { // rarities must equal 100 when summed
    private static Rarity common = new Rarity("Common", "^w", 60);

    private static Rarity uncommon = new Rarity("Uncommon", "^g", 20);

    private static Rarity rare = new Rarity("Rare", "^7", 13);

    private static Rarity exceptional = new Rarity("Exceptional", "^p", 6);

    private static Rarity godly = new Rarity("Godly", "^O", 1);

    public static Rarity getCommon() {return common;}

    public static Rarity getUncommon() {return uncommon;}

    public static Rarity getRare() {return rare;}

    public static Rarity getExceptional() {return exceptional;}

    public static Rarity getGodly() {return godly;}

    private static ArrayList<Rarity> rarityArrayList = new ArrayList<>();

    public static ArrayList<Rarity> getRarityArrayList() {return rarityArrayList;}

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
