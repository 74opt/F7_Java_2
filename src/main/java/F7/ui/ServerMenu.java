package F7.ui;

import F7.Lanterna;
import F7.Network;

public class ServerMenu {
    //! do i want the network here or in Network? or even in construction package?
    private static Network network;

    public static void start(String name) {
        network = new Network(Network.MAIN_PORT, name);
    }

    // TODO: top priority, design a server menu (whiteboard time)
    public static void menu() {
        // List this info: server name, latency, player count
        
    }
}
