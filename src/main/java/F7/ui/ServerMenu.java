package F7.ui;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import F7.Lanterna;
import F7.Network;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

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
                        case 10, 200 -> Lanterna.print(i, 55, "╩");
                        case 0 -> Lanterna.print(i, 55, "╚");
                        case 210 -> Lanterna.print(i, 55, "╝");
                        default -> Lanterna.print(i, 55, "═");
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
                    for (int j = 0; j < 55; j++) {
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

        // Top Text
        Lanterna.print(1, 1, "Ping");
        Lanterna.print(11, 1, "Server Name");
        Lanterna.print(201, 1, "Players");

        // Controls
        Lanterna.print(1, 56, """
        ^GW) Move up server list
        S) Move down server list
        E) Join server
        Q) Exit""");
    }

    // TODO: add key binding list at bottom
    // TODO: cursor at server name column
    public static void menu() throws Exception {
        // List this info: server name, latency, player count
        initialDraw();

        new Thread(() -> {
            boolean running = true;

            while (running) {
                try {
                    KeyStroke keyPressed = Lanterna.getScreen().pollInput();

                    if (keyPressed != null) {
                        try {
                            switch (keyPressed.getCharacter()) {
                                case 'w' -> {
                                    
                                }
                                case 's' -> {

                                }
                                case 'e' -> {

                                }
                                case 'q' -> {
                                    running = false;
                                    
                                    MainMenu.menu2();
                                }
                            }
                        } catch (Exception ignored) {}
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void searchServers() throws Exception {
        ArrayList<Network> servers = new ArrayList<>();

        // TODO: implement actual server search
        byte[] ips = InetAddress.getLocalHost().getAddress();

        for (int i = 1; i < 255; i++) {
            final int j = i; // must be declared final to use in thread

            new Thread(() -> {
                try {
                    ips[3] = (byte) j;
                    String address = InetAddress.getByAddress(ips).toString().substring(1);

                    Network.testConnection(address, Network.MAIN_PORT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i = 0; i < servers.size(); i++) {
            // TODO: implement server info printing
            // 1st, ping
            // 2nd, name
            // 3rd, players
        }
    }
}
