package F7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

// TODO: make constructor + instance vars for OOP with user-hosted servers
public class Network {
    private static Socket socket;
    private static ServerSocket serverSocket;

    // To send data to the client
    private static PrintStream printStream;

    // To read data coming from the client
    private static BufferedReader bufferedReader;

    // To send data to the server
    private static DataOutputStream dataOutputStream;

    // Server details
    private static final int mainPort = 14000;
    private static final int maxPlayers = 2;
    private static String name;
    private static String address;

    public static String getName() {return name;}

    public static String getAddress() {return address;}

    // need?
    public static ServerSocket getServerSocket() {return serverSocket;}

    //TODO: do i want the non-static stuff? do i want the static stuff?
    // Possibly make static because only 1 computer will be hosting 1 server

    // make following void or boolean?
    public static void startServer(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            address = InetAddress.getLocalHost().getHostAddress();

            // This line waits for a connection
            socket = serverSocket.accept();

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());
        } catch (BindException e) {
            System.out.println("Server already exists");
        }
    }

    public static void joinServer(String address, int port) throws IOException {
        try {
            socket = new Socket(address, port);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (ConnectException e) {
            System.out.println("Server with port " + port + " not found");
        }
    }

    private static boolean testServerConnection(String address, int port) throws IOException {
        try {
            socket = new Socket(address, port);

            return true;
        } catch (ConnectException ignored) {
            return false;
        }
    }

    // FIXME PrintStream.println and PrintStream.print turn all data into a string
    public static void sendData(Object data) {
        printStream.println(data);
    }

    private static Object readData() {
        Object object;

        try {
            // will be reading strings
            object = bufferedReader.readLine();

            // object returned can only be turned into a string without any methods
            return object;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String readString() {
        return (String) readData();
    }

    public static int readInt() {
        return Integer.parseInt(readString());
    }

    public static double readDouble() {
        return Double.parseDouble(readString());
    }

    public static boolean readBoolean() {
        return Boolean.parseBoolean(readString());
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
                    if (address.isReachable(5000) && testServerConnection(output, 14000)) {
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

    public static int getPing() {
        return 0;
    }

    public static int getPlayers() {
        return 0;
    }
}
