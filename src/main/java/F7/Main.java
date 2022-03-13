package F7;

import F7.entities.construction.*;
import F7.ui.*;
import java.net.*;

/*
Credits:
Matthieu De Robles: Made F7
Aramie Ewen and Gordon Dewey: Main Rivals/Emotional Support Animals
David Lai and Oliver Malkus: Did My English Work
Mr. Holmer: Assigned This Project
Gayan Weerakutti: Some dude from stack overflow
Hailey Wong: Didn't fuck up F7 when she had my computer
Ev Lisovenko and Harper V: Emotional-er Support Animals
*/

/*
TODO (Ordered by importance):
    - Make the DeathMenu and WinMenu much better please (make sure to unshit code)
        - You deadass forgot to implement the player actually dying
        - Probably should have them in the same class as CombatMenu2
            - Rename to just CombatMenu?
        - Also refactor some variable names to reflect the new combat
    - Networking
        - Good resource even though it's for unity: https://docs-multiplayer.unity3d.com/docs/learn/introduction
        - Set options for multiplayer and singleplayer
        - Networking works with a listen server, a player is host and others join
            - Peer to peer also seems cool but many downsides
        - Server menu?
            - Detect all servers running and put them into an arraylist
                - Sort by latency
                - Good chance to use arraylist methods
        - Option to join or host
            - When hosting, check all available servers first to make sure port is not already taken
        - Need a set tickrate for combat menu? (ScheduledExecutorService?)
    - Inheritance (check dumb notes)
    - You can finally have records again
        - Make the records set in construction public
            - Maybe enum?
    - Javadoc all files in the F7 package?

Server Notes:
    - Upon entering multiplayer, server is made, regardless of being a host or not
*/
// TODO: can we make everything in construction final (except Players) since we just clone?
public class Main {
    public static void main(String[] args) {
        try {
            Network server = new Network(Network.MAIN_PORT, "^Rc^Oo^Yo^gl ^Bser^pver");

            Enemies.setEnemyHashMap();
            Weapons.setWeaponHashMap();
            Shields.setShieldHashMap();
            Rarities.setRaritiesArrayList();
            Consumables.setConsumableHashMap();
            WinMenu.setRarityMultipliers();

            Lanterna.startScreen(Lanterna.STANDARD_COLUMNS, Lanterna.STANDARD_ROWS);

            MainMenu.menu2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
