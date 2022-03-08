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
                        case 10, 200 -> Lanterna.print(i, 0, "╦");
                        case 0 -> Lanterna.print(i, 0, "╔");
                        case 210 -> Lanterna.print(i, 0, "╗");
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
                        case 10, 200 -> Lanterna.print(i, 2, "╬");
                        case 0 -> Lanterna.print(i, 2, "╠");
                        case 210 -> Lanterna.print(i, 2, "╣");
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
                        case 10, 200 -> Lanterna.print(i, 60, "╩");
                        case 0 -> Lanterna.print(i, 60, "╚");
                        case 210 -> Lanterna.print(i, 60, "╝");
                        default -> Lanterna.print(i, 60, "═");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Vertical lines
        int[] cols = {0, 10, 200, 210};

        for (int i : cols) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 61; j++) {
                        switch (j) {
                            case 0, 2, 60 -> {}
                            default -> Lanterna.print(i, j, "║");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // Text
        Lanterna.print(1, 1, "Ping");
        Lanterna.print(11, 1, "Server Name");
        Lanterna.print(201, 1, "Players");
    }

    // TODO: add key binding list at bottom
    // TODO: cursor at server name column
    public static void menu() throws Exception {
        // List this info: server name, latency, player count
        initialDraw();
    }
}
