package F7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

// TODO: make constructor + instance vars for OOP with user-hosted servers
public class Network {
    // Static vars
    private static Socket socket;
    private static ServerSocket serverSocket;
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;
    private static final int mainPort = 14000;
    @Deprecated
    private static final int[] portRange = {14000, 15000};

    // Non static vars
    private String name;
    private String address;
    private int ping;
    private int playerCount;

    public Network(String name, String address, int ping /*ping pong*/, int playerCount) throws UnknownHostException {
        this.name = name;
        this.address = InetAddress.getLocalHost().getHostAddress();
    }

    // make following void or boolean?
    public static void startServer(int port) throws IOException {
        try {
            if (portRange[0] <= port && port <= portRange[1]) {
                serverSocket = new ServerSocket(port);
            } else {
                System.out.println("Invalid port " + port);
            }
        } catch (BindException e) {
            System.out.println("Server with port " + port + " already exists");
        }
    }

    public static boolean joinServer(String address, int port) throws IOException {
        try {
            socket = new Socket(address, port);
            return true;
        } catch (ConnectException e) {
            //System.out.println("Server with port " + port + " not found");
            return false;
        }
    }

    public static ArrayList<InetAddress> retrieveServers() {
        ArrayList<InetAddress> servers = new ArrayList<>();
        final byte[] ip;

        try {
            ip = InetAddress.getLocalHost().getAddress();
        } catch (Exception e) {
            return null;
        }

        for (int i = 1; i < 255; i++) {
            final int j = i;  // i as non-final variable cannot be referenced from inner class
            new Thread(() -> {
                try {
                    ip[3] = (byte) j;
                    InetAddress address = InetAddress.getByAddress(ip);
                    String output = address.toString().substring(1);
                    if (address.isReachable(5000) && Network.joinServer(output, 14000)) {
                        servers.add(address);
                        //System.out.println(output + " is on the network");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        return servers;
    }
}
