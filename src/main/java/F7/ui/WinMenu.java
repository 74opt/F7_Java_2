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

        int exp = (int) (((200 + 30 * CombatMenu.getEnemy().getLevel()) * (Utils.randomRange(73, 97) / 100.0)) * rarityMultipliers.get(CombatMenu.getEnemy().getRARITY())); //? should i change 1.6 to less?
        Players.getPlayer().setExp(Players.getPlayer().getExp() + exp);

        Lanterna.printf(1, 1, "^GYou defeated %s^G! %s ^Gexp awarded.", CombatMenu.getEnemy().getNAME(), exp);
        Thread.sleep(Utils.STANDARD);

        if (Utils.chance(101)) {
            Rarity[] rarities = {Rarities.common, Rarities.uncommon, Rarities.rare, Rarities.exceptional, Rarities.godly, Rarities.godly, Rarities.godly};
            int weaponChance = Utils.randomRange(0, 101);
            int rarityIndex = 0;
            Rarity weaponRarity;

            for (int i = 0; i < rarities.length; i++) {
                if (CombatMenu.getEnemy().getRARITY().equals(rarities[i])) {
                    rarityIndex = i;
                    break;
                }
            }

            if (weaponChance > 90 && weaponChance <= 99) {
                rarityIndex++;
            } else {
                rarityIndex += 2;
            }

            weaponRarity = rarities[rarityIndex];

            // System.out.println("weaponRarity: " + weaponRarity);
            // System.out.println("testRarity: " + testRarity);

            Weapon weapon = new Weapon(Weapons.getWeaponHashMap().get(weaponRarity)[Utils.randomRange(0, Weapons.getWeaponHashMap().get(weaponRarity).length)]);
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
                    } else {
                        Lanterna.print(1, 4, "^GYou have no room for this weapon, select a weapon to drop or cancel.");
                        
                        for (int i = 0; i < 4; i++) {
                            Lanterna.printf(1, 5 + i, i + 1 + ") ^G%s^G", Players.getPlayer().getWeapons()[i].toString(true));
                        }

                        Lanterna.print(1, 9, "5) Cancel");
                        
                        boolean dropping = true;

                        while (dropping) {
                            KeyStroke dropChoice = Lanterna.getScreen().readInput();

                            try {
                                if (Integer.parseInt(dropChoice.getCharacter() + "") == 5) {
                                    dropping = false;
                                }

                                if (!Players.getPlayer().getWeapons()[Integer.parseInt(dropChoice.getCharacter() + "")].equals(Weapons.getFists())) {
                                    Players.getPlayer().setWeapon(weapon, Integer.parseInt(dropChoice.getCharacter() + "") - 1);
                                    dropping = false;
                                }
                            } catch (Exception e) {
                                Lanterna.print(1, 10, e.getClass().getName());
                            }
                        }
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
