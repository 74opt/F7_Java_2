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
Hailey Wong: Didn't fuck up F7
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
*/

/*
Sources because im gonna kashoot myself
https://stackoverflow.com/questions/29545597/multiplayer-game-in-java-connect-client-player-to-game-that-was-created-by-ot
https://stackoverflow.com/questions/29325034/how-would-an-mmo-deal-with-calculating-and-sending-packets-for-thousands-of-play/30826823#30826823
https://stackoverflow.com/questions/52565970/can-i-connect-2-computers-using-sockets-in-java
https://www.geeksforgeeks.org/java-networking/
https://www.geeksforgeeks.org/establishing-the-two-way-communication-between-server-and-client-in-java/
 */

/*
Server Notes:
    - Upon entering multiplayer, server is made, regardless of being a host or not
*/
// TODO: can we make everything in construction final (except Players) since we just clone?
public class Main {
    public static void main(String[] args) {
        try {
            // Enemies.setEnemyHashMap();
            // Weapons.setWeaponHashMap();
            // Shields.setShieldHashMap();
            // Rarities.setRaritiesArrayList();
            // Consumables.setConsumableHashMap();
            // WinMenu.setRarityMultipliers();

            // Lanterna.startScreen(Lanterna.getSTANDARD_COLUMNS(), Lanterna.getSTANDARD_ROWS());

            // MainMenu.menu();
            // Network.startServer(Network.MAIN_PORT);
            // Network.joinServer("localhost", Network.MAIN_PORT);
            // Network.retrieveServers();
            
            // usually, servers will have the same port, different addresses.
            Network2 server = new Network2(Network2.MAIN_PORT, "test server");
            
            
            //server.sendData("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
