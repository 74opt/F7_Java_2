package F7.ui;

import F7.Lanterna;
import F7.Network;

public class ServerMenu {
    //! do i want the network here or in Network? or even in construction package?
    private static Network network;

    public static void start(String name) {
        network = new Network(Network.MAIN_PORT, name);
    }

    private static void initialDraw() throws Exception {
        Lanterna.clear();

        // Setting color
        Lanterna.print(0, 0, "^G");

        // Top line
        new Thread(() -> {
            try {
                for (int i = 0; i < 211; i++) {
                    switch (i) {
                        case 6, 205 -> Lanterna.print(i, 0, "╦");
                        default -> Lanterna.print(i, 0, "═");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Second line
        new Thread(() -> {
            try {
                for (int i = 0; i < 211; i++) {
                    switch (i) {
                        case 6, 205 -> Lanterna.print(i, 2, "╦");
                        default -> Lanterna.print(i, 2, "═");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        
        // Bottom line
        new Thread(() -> {
            try {
                for (int i = 0; i < 211; i++) {
                    switch (i) {
                        case 6, 205 -> Lanterna.print(i, 61, "╩");
                        default -> Lanterna.print(i, 61, "═");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // First vertical line


        // Second vertical line


        // Third vertical line


        // Fourth vertical line
    }

    // TODO: top priority, design a server menu (whiteboard time)
    public static void menu() {
        // List this info: server name, latency, player count
        
    }
}
