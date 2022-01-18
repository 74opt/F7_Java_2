package F7.entities.construction;

import java.util.HashMap;
import F7.entities.classes.Rarity;
import F7.entities.classes.Weapon;

public class Weapons { // TODO: make setLevel for enemy + weapon to modify stats thansk
    private static Weapon fists = new Weapon("Fists", 2, 45, 1, 2, Rarities.getCommon(), 1, 2, 1); // will not be able to loot, only if no weapon

    private static Weapon crowbar = new Weapon("Crowbar", 6, 73, 1, 1, Rarities.getCommon(), 7, 1.8, 1);

    private static Weapon dagger = new Weapon("Dagger", 10, 60, 1, 2, Rarities.getCommon(), 5, 1.8, 1);

    private static Weapon pistol = new Weapon("Semi-Auto Pistol", 22, 60, 1, 1, Rarities.getUncommon(), 8, 1.9, 1);

    private static Weapon revolver = new Weapon("Revolver", 30, 57, 1, 1, Rarities.getUncommon(), 5, 2.2, 2);

    private static Weapon doubleBarrel = new Weapon("Double-Barrel Shotgun", 32, 50, 1, 2, Rarities.getRare(), 1, 1.75, 2);

    private static Weapon fiftyCal = new Weapon(".50 Caliber Rifle", 40, 45, 1, 1, Rarities.getRare(), 10, 2, 3);

    private static Weapon disvita = new Weapon("Disvita Sledgehammer", 24, 80, 1, 1, Rarities.getExceptional(), 10, 3, 1);

    private static Weapon gauss = new Weapon("Gauss Cannon", 14, 95, 1, 1, Rarities.getExceptional(), 20, 2.8, 3);

    private static Weapon intrensia = new Weapon("Blade of Intrensia", 26, 85, 1, 1, Rarities.getGodly(), 28, 2.5, 1);

    private static Weapon tachyon = new Weapon("Tachyon Minigun", 2, 70, 1, 60, Rarities.getGodly(), 2, 2.1, 3);

    private static HashMap<Rarity, Weapon[]> weaponHashMap = new HashMap<>();

    public static HashMap<Rarity, Weapon[]> getWeaponHashMap() {return weaponHashMap;}

    public static Weapon getFists() {return fists;}

    public static void setWeaponHashMap() {
        weaponHashMap.put(Rarities.getCommon(), new Weapon[] {crowbar, dagger});
        weaponHashMap.put(Rarities.getUncommon(), new Weapon[] {pistol, revolver});
        weaponHashMap.put(Rarities.getRare(), new Weapon[] {doubleBarrel, fiftyCal});
        weaponHashMap.put(Rarities.getExceptional(), new Weapon[] {disvita, gauss});
        weaponHashMap.put(Rarities.getGodly(), new Weapon[] {intrensia, tachyon});
    }
}
