package F7.entities.construction;

import java.util.HashMap;
import F7.entities.classes.*;

public class Weapons { // TODO: make setLevel for enemy + weapon to modify stats thansk
    public static Weapon fists = new Weapon("Fists", 2, 45, 1, 2, Rarities.common); // will not be able to loot, only if no weapon

    public static Weapon crowbar = new Weapon("Crowbar", 6, 73, 1, 1, Rarities.common);

    public static Weapon dagger = new Weapon("Dagger", 10, 60, 1, 2, Rarities.common);

    public static Weapon pistol = new Weapon("Semi-Auto Pistol", 22, 60, 1, 1, Rarities.uncommon);

    public static Weapon revolver = new Weapon("Revolver", 30, 57, 1, 1, Rarities.uncommon);

    public static Weapon doubleBarrel = new Weapon("Double-Barrel Shotgun", 4, 50, 1, 16, Rarities.rare);

    public static Weapon fiftyCal = new Weapon(".50 Caliber Rifle", 40, 45, 1, 1, Rarities.rare);

    public static Weapon disvita = new Weapon("Disvita Sledgehammer", 24, 80, 1, 1, Rarities.exceptional);

    public static Weapon gauss = new Weapon("Gauss Cannon", 14, 95, 1, 1, Rarities.exceptional);

    public static Weapon intrensia = new Weapon("Blade of Intrensia", 26, 85, 1, 1, Rarities.godly);

    public static Weapon tachyon = new Weapon("Tachyon Minigun", 2, 70, 1, 60, Rarities.godly);

    public static HashMap<Rarity, Weapon[]> weaponHashMap = new HashMap<>();

    public static void setWeaponHashMap() {
        weaponHashMap.put(Rarities.common, new Weapon[] {crowbar, dagger});
        weaponHashMap.put(Rarities.uncommon, new Weapon[] {pistol, revolver});
        weaponHashMap.put(Rarities.rare, new Weapon[] {doubleBarrel, fiftyCal});
        weaponHashMap.put(Rarities.exceptional, new Weapon[] {disvita, gauss});
        weaponHashMap.put(Rarities.godly, new Weapon[] {intrensia, tachyon});
    }
}
