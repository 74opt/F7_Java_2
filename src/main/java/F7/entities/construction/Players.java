package F7.entities.construction;

import F7.entities.classes.Player;
import F7.entities.classes.Weapon;
import F7.entities.construction.Consumables;
import java.util.ArrayList;
import java.util.Arrays;

public class Players {
    // This is the player that should be used for the entire game
    // All other instances of player are just tests and stuff that doesn't need to be here
    public static Player player;

    public static Player getPlayer() {return player;}

    public static void setPlayer(Player player) {Players.player = player;}
    // Use this for dev testing, but won't need for now when I have working save/load code + saved game
//    public static Player presentation = new Player(
//        "Mr. Holmer", 10000, 9000, 20, 0, new Weapon[] {new Weapon(Weapons.disvita), new Weapon(Weapons.tachyon), null, new Weapon(Weapons.dagger)},
//        3, Shields.military, 19, 8, new ArrayList<>(Arrays.asList(Consumables.getMedkit(), Consumables.getMedkit(), Consumables.getMedkit(), Consumables.getSmoke(),
//        Consumables.getCorrosive(), Consumables.getTarget(), Consumables.getAmplifier(), Consumables.getFlashbang())), 2, 7, 5, 2, 3
//    );
}
