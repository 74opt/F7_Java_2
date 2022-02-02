package F7.ui;

import F7.Lanterna;
import F7.Utils;
import F7.entities.classes.Rarity;
import F7.entities.classes.Weapon;
import F7.entities.construction.Players;
import F7.entities.construction.Rarities;
import F7.entities.construction.Weapons;
import com.googlecode.lanterna.input.KeyStroke;
import java.util.Arrays;
import java.util.HashMap;

public class WinMenu {
    private static HashMap<Rarity, Double> rarityMultipliers = new HashMap<>();

    public static void menu() throws Exception {
        Lanterna.clear();

        int exp = (int) (((200 + 300 * Math.pow(CombatMenu.getEnemy().getLevel(), 1.6)) * (Utils.randomRange(73, 97) / 100.0)) * rarityMultipliers.get(CombatMenu.getEnemy().getRARITY())); //? should i change 1.6 to less?
        Players.getPlayer().setExp(Players.getPlayer().getExp() + exp);

        Lanterna.printf(1, 1, "^GYou defeated %s^G! %s ^Gexp awarded.", CombatMenu.getEnemy().getNAME(), exp);
        Thread.sleep(Utils.getSTANDARD());

        if (Utils.chance(101)) {
            int weaponRarity = Utils.randomRange(70, 101/* - CombatMenu.getEnemy().getRARITY().CHANCE()*/);

            Rarity testRarity = Rarities.getRarityArrayList().get(weaponRarity);

            System.out.println("weaponRarity: " + weaponRarity);
            System.out.println("testRarity: " + testRarity);

            Weapon weapon = Weapons.getWeaponHashMap().get(testRarity)[Utils.randomRange(0, Weapons.getWeaponHashMap().get(Rarities.getRarityArrayList().get(weaponRarity)).length)];
            weapon.setLevel(CombatMenu.getEnemy().getLevel() + Utils.randomRange(-1, 1));

            Lanterna.printf(1, 2, "%s dropped %s. Will you take it? (^gQ^G to confirm, ^RE^G to cancel)", CombatMenu.getEnemy().getNAME(), weapon.getNAME());
            
            boolean running = true;
            while (running) {
                KeyStroke choice = Lanterna.getScreen().readInput();

                if (choice.getCharacter() == 'q') {
                    running = false;

                    int search = Arrays.asList(Players.getPlayer().getWeapons()).indexOf(null);

                    if (search != -1) {
                        Players.getPlayer().setWeapon(weapon, search);
                    }
                } else if (choice.getCharacter() == 'e') {
                    running = false;
                    
                }
            }
        }

        MapMenu.menu();
    }

    public static void setRarityMultipliers() {
        rarityMultipliers.put(Rarities.getCommon(), 1.0);
        rarityMultipliers.put(Rarities.getUncommon(), 1.5);
        rarityMultipliers.put(Rarities.getRare(), 2.0);
        rarityMultipliers.put(Rarities.getExceptional(), 2.5);
        rarityMultipliers.put(Rarities.getGodly(), 3.0);
    }
}
