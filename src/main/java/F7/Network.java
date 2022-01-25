package F7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Network {
    private static Socket socket;
    private static ServerSocket serverSocket;
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;
    private static final int[] portRange = {14000, 15000};

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

    public static void joinServer(String address, int port) throws IOException {
        try {
            socket = new Socket(address, port);
        } catch (ConnectException e) {
            System.out.println("Server with port " + port + " not found");
        }
    }

    public static ArrayList<Socket> retrieveServers() {
        ArrayList<Socket> servers = new ArrayList<>();

        for (int i = portRange[0]; i < portRange[1] + 1; i++) {
            try {
                Socket socket = new Socket("127.0.0.1", i);

                servers.add(socket);
            } catch (IOException ignored) {}
        }

        return servers;
    }
}
