package F7.entities.construction;

import java.util.ArrayList;
import java.util.Arrays;

import F7.entities.classes.Player;
import F7.entities.classes.Weapon;

public class Players {
    // This is the player that should be used for the entire game
    // All other instances of player are just tests and stuff
    public static Player player;// = new Player("God");

    public static Player dev = new Player(
            "Dev", 9821, 8472, 90, 0, new Weapon[]{new Weapon(Weapons.tachyon),
            new Weapon(Weapons.crowbar), null, new Weapon(Weapons.gauss)},
            0, Shields.aegis, 19, 8, new ArrayList<>(Arrays.asList(Consumables.medkit, Consumables.medkit, Consumables.medkit, Consumables.smoke, 
                                                                   Consumables.corrosive, Consumables.target, Consumables.amplifier, Consumables.flashbang)),
            25, 14, 22, 15, 13
    );

    public static Player presentation = new Player(
        "Mr. Holmer", 10000, 9000, 20, 0, new Weapon[] {new Weapon(Weapons.disvita), new Weapon(Weapons.tachyon), null, new Weapon(Weapons.dagger)},
        3, Shields.military, 19, 8, new ArrayList<>(Arrays.asList(Consumables.medkit, Consumables.medkit, Consumables.medkit, Consumables.smoke, 
        Consumables.corrosive, Consumables.target, Consumables.amplifier, Consumables.flashbang)), 2, 7, 5, 2, 3
    );
}
