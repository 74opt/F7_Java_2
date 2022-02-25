package F7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

// TODO: make constructor + instance vars for OOP with user-hosted servers
@Deprecated
public class Network {
    private static Socket socket;
    private static ServerSocket serverSocket;
    private static PrintStream printStream;
    private static BufferedReader bufferedReader;

    // Server details
    public static final int MAIN_PORT = 14000;
    private static final int MAX_PLAYERS = 2;
    private static String name;
    private static String address;

    public static final String SERVER_VERIFICATION = "F7 server here!";
    public static final String BROWSER_VERIFICATION = "F7 browser here!";

    public static String getName() {return name;}

    public static String getAddress() {return address;}

    // need?
    public static ServerSocket getServerSocket() {return serverSocket;}

    //TODO: do i want the non-static stuff? do i want the static stuff?
    // Possibly make static because only 1 computer will be hosting 1 server

    // make following void or boolean?
    public static void startServer(int port) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                address = InetAddress.getLocalHost().getHostAddress();
    
                // This line waits for a connection
                socket = serverSocket.accept();
    
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printStream = new PrintStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("Server already exists");
            }
        }).start();
    }

    public static void joinServer(String address, int port) throws IOException {
        try {
            socket = new Socket(address, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());

            printStream.println(SERVER_VERIFICATION);
        } catch (ConnectException e) {
            System.out.println("Server with port " + port + " not found");
        }
    }

    // For the hosting server
    public static void checkConnection() {
        
       
        new Thread(() -> {
            while (true) {
                String verification = readString(); 

                try {
                    if (verification.equals(SERVER_VERIFICATION)) {
                        // keep the connection
                        System.out.println("is server");
                        break;
                    } else if (verification.equals(BROWSER_VERIFICATION)) {
                        // send the data then disconnect
                        printStream.println(name);
                        printStream.println(address);
                        printStream.println(getPing());
                        printStream.println(getPlayers());
                        socket.close();
                        System.out.println("is browser");
                    } else {
                        // disconnect
                        socket.close();
                        System.out.println("is disconnect");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static boolean testServerConnection(String address, int port) throws IOException {
        try {
            socket = new Socket(address, port);
            sendData("test");

            return true;
        } catch (ConnectException ignored) {
            return false;
        }
    }

    public static void sendData(Object data) {
        printStream.println(data);
    }

    //? How to handle reading data
    // Once data is read, it is deleted from the buffer
    // We can work with this.
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
        byte[] ip;

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
                    long initialTime = System.currentTimeMillis();
                    if (address.isReachable(5000) && testServerConnection(output, 14000)) {
                        long ping = System.currentTimeMillis() - initialTime;
                        servers.add(address);
                        System.out.println(readString());
                        System.out.println("ping: " + ping + "ms");
                        //System.out.println(output + " is on the network");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        return servers;
    }

    // do i need?
    public static int getPing() {
        return 0;
    }

    public static int getPlayers() {
        return 0;
    }
}
