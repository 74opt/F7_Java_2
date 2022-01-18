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

        int exp = (int) (((200 + 300 * Math.pow(CombatMenu2.getEnemy().getLevel(), 1.6)) * (Utils.randomRange(73, 97) / 100.0)) * rarityMultipliers.get(CombatMenu2.getEnemy().getRARITY())); //? should i change 1.6 to less?
        Players.getPlayer().setExp(Players.getPlayer().getExp() + exp);

        Lanterna.printf(1, 1, "^GYou defeated %s^G! %s ^Gexp awarded.", CombatMenu2.getEnemy().getNAME(), exp);
        Thread.sleep(Utils.getSTANDARD());

        if (Utils.chance(34)) {
            int weaponRarity = Utils.randomRange(0, 101 - CombatMenu2.getEnemy().getRARITY().getCHANCE());

            Weapon weapon = Weapons.getWeaponHashMap().get(Rarities.getRarityArrayList().get(weaponRarity))[Utils.randomRange(0, Weapons.getWeaponHashMap().get(Rarities.getRarityArrayList().get(weaponRarity)).length)];

            Lanterna.printf(1, 2, "%s dropped %s. Will you take it? (^gQ^G to confirm, ^RE^G to cancel)");
            
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
