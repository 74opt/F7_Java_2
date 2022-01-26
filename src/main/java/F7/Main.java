package F7;

import F7.entities.construction.*;
import F7.ui.*;

import java.io.*;
import java.net.*;
import java.util.*;

/*
Credits:
Matthieu De Robles: Made F7
Aramie Ewen and Gordon Dewey: Main Rivals/Emotional Support Animals
David Lai and Oliver Malkus: Did My English Work
Mr. Holmer: Assigned This Project
Gayan Weerakutti: Some dude from stack overflow
*/

/*
TODO (Ordered by importance):
    - Holmer said you can make variables public if they are final
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
*/

/*
Sources because im gonna kashoot myself
https://stackoverflow.com/questions/29545597/multiplayer-game-in-java-connect-client-player-to-game-that-was-created-by-ot
https://stackoverflow.com/questions/29325034/how-would-an-mmo-deal-with-calculating-and-sending-packets-for-thousands-of-play/30826823#30826823
https://stackoverflow.com/questions/52565970/can-i-connect-2-computers-using-sockets-in-java
 */

public class Main {
    public static void main(String[] args) throws Exception {
//        Enemies.setEnemyHashMap();
//        Weapons.setWeaponHashMap();
//        Shields.setShieldHashMap();
//        Rarities.setRaritiesArrayList();
//        Consumables.setConsumableHashMap();
//        WinMenu.setRarityMultipliers();

        //Lanterna.startScreen(Lanterna.getSTANDARD_COLUMNS(), Lanterna.getSTANDARD_ROWS());

        //MainMenu.menu();

        // For system IP
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("System IP Address: " + inetAddress.getHostAddress());

        // For public IP (will i need?)
        String publicIP = "";

        try {
            URL aws = new URL("http://checkip.amazonaws.com/");

            BufferedReader sc = new BufferedReader(new InputStreamReader(aws.openStream()));

            // reads system IPAddress
            publicIP = sc.readLine().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Public IP Address: " + publicIP);

        Network.startServer(14000);

        //while (true) {}

        final byte[] ip;
        try {
            ip = InetAddress.getLocalHost().getAddress();
        } catch (Exception e) {
            return;     // exit method, otherwise "ip might not have been initialized"
        }

        for (int i = 1; i < 255; i++) {
            final int j = i;  // i as non-final variable cannot be referenced from inner class
            new Thread(() -> {
                try {
                    ip[3] = (byte)j;
                    InetAddress address = InetAddress.getByAddress(ip);
                    String output = address.toString().substring(1);
                    if (address.isReachable(5000)) {
                        System.out.println(output + " is on the network");
                    } else {
                        System.out.println("Not Reachable: "+output);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // Works for school computers (use on different computer)
        // Socket socket = new Socket(inetAddress.getHostAddress(), 14000);
    }
}
