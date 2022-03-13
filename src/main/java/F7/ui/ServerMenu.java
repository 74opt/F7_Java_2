package F7.ui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import F7.Lanterna;
import F7.Network;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class ServerMenu {
    private record ServerInfo(String name, long ping, int players, String address) {}

    //! do i want the network here or in Network? or even in construction package?
    private static Network network;
    private static ArrayList<ServerInfo> servers = new ArrayList<>();
    private static final ArrayList<ServerInfo> demoServers = new ArrayList<>(Arrays.asList(new ServerInfo("Demo Server 1", 53, 1, ""), new ServerInfo("Demo Server 2", 37, 1, ""), new ServerInfo("Demo Server 3", 52, 1, ""), new ServerInfo("Demo Server 4", 42, 1, ""), new ServerInfo("Demo Server 5", 22, 1, "")));

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
                        case 10, 179 -> Lanterna.print(i, 0, "╦");
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
                        case 10, 179 -> Lanterna.print(i, 2, "╬");
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
                        case 10, 179 -> Lanterna.print(i, 55, "╩");
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
        int[] cols = {0, 10, 179, 210};

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
        Lanterna.print(180, 1, "Players");

        // Controls
        Lanterna.print(1, 56, """
        ^GW) Move up server list
        S) Move down server list
        E) Join server
        Q) Exit""");
    }

    public static void menu() throws Exception {
        // List this info: server name, latency, player count
        initialDraw();
        searchServers();

        new Thread(() -> {
            boolean running = true;
            int selectedServer = 0;

            while (running) {
                try {
                    KeyStroke keyPressed = Lanterna.getScreen().pollInput();

                    if (keyPressed != null) {
                        try {
                            switch (keyPressed.getCharacter()) {
                                case 'w' -> { // go up
                                    if (selectedServer == 0) {
                                        // go back to end
                                        selectedServer = servers.size() - 1;

                                        Lanterna.print(11, 3, "^W" + servers.get(selectedServer).name + "  ");
                                        Lanterna.print(11, selectedServer + 3, "^g> ^W" + servers.get(selectedServer).name);
                                    } else {
                                        selectedServer--;
                                        
                                        Lanterna.print(11, selectedServer + 4, "^W" + servers.get(selectedServer).name + "  ");
                                        Lanterna.print(11, selectedServer + 3, "^g> ^W" + servers.get(selectedServer).name);
                                    }         
                                }
                                case 's' -> { // go down
                                    if (selectedServer == servers.size() - 1) {
                                        // go back to start
                                        selectedServer = 0;

                                        Lanterna.print(11, servers.size() + 2, "^W" + servers.get(selectedServer).name + "  ");
                                        Lanterna.print(11, 3, "^g> ^W" + servers.get(selectedServer).name);
                                    } else {
                                        selectedServer++;

                                        Lanterna.print(11, selectedServer + 2, "^W" + servers.get(selectedServer).name + "  ");
                                        Lanterna.print(11, selectedServer + 3, "^g> ^W" + servers.get(selectedServer).name);
                                    }
                                }
                                case 'e' -> {
                                    network.join(servers.get(selectedServer).address, Network.MAIN_PORT);

                                    // TODO: implementation
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

    private static void searchServers() throws UnknownHostException {
        // TODO: uncomment when game done
        //servers = new ArrayList<>();
        byte[] ips = InetAddress.getLocalHost().getAddress();

        for (int i = 1; i < 255; i++) {
            final int j = i; // must be declared final to use in thread

            new Thread(() -> {
                try {
                    ips[3] = (byte) j;
                    String address = InetAddress.getByAddress(ips).toString().substring(1);
                    
                    String data = Network.testConnection(address, Network.MAIN_PORT);
                    // 1. connect with a BROWSER_VERIFICATION
                    // 2. have that server return info
                    // 3. add to list
                    Thread.sleep(1000);
                    if (!data.equals(null)) {
                        String[] datum = data.split(",");
                        String ping = datum[0];
                        String name = datum[1];
                        String players = datum[2];
                        servers.add(new ServerInfo(name, Long.valueOf(ping), Integer.parseInt(players), address));
                    }

                    printServers();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // TODO: only for testing
        servers = demoServers;
    }

    // Sort by ping
    private static void sortServers() {
        //* implementing insertion and selection sort
        //* gonna have them in their own threads
        //* also need statement execution counts
        //* this rubric sucks

        // insertion sort
        new Thread(() -> {
            int count = 0;
            
            for (int i = 1; i < servers.size(); i++) {
                ServerInfo temp = servers.get(i);
                int j = i;

                while (j > 0 && servers.get(j - 1).ping > temp.ping) {
                    servers.set(j, servers.get(j - 1));
                    j--;
                    count++;
                }

                servers.set(j, temp);
            }
        }).start();

        // selection sort
        new Thread(() -> {
            int count = 0;

            for (int i = 0; i < servers.size() - 1; i++) {
                int min = i;

                // TODO: do i need the count here?
                for (int j = i + 1; j < servers.size(); j++) {
                    if (servers.get(j).ping < servers.get(min).ping) {
                        min = j;
                    }
                }

                if (min != i) {
                    count++;

                    ServerInfo temp = servers.get(i);
                    servers.set(i, servers.get(min));
                    servers.set(min, temp);
                }
            }
        }).start();
    }

    private static void printServers() throws Exception {
        for (int i = 0; i < servers.size(); i++) {
            if (i == 0) {
                Lanterna.print(11, 3, "^g> ^W" + servers.get(i).name);
            } else {
                Lanterna.print(11, i + 3, servers.get(i).name);
            }

            // Reset color to white in case it was changed by the name
            Lanterna.print(0, 0, "^W");
            Lanterna.print(1, i + 3, servers.get(i).ping + " ms");
            Lanterna.print(180, i + 3, servers.get(i).players + "/2");
        }
    }
}
