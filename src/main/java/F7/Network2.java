package F7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Network2 {
    private Socket socket;
    private ServerSocket serverSocket;
    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private String name;
    private String address;

    public static final int MAIN_PORT = 14000;
    private static final int MAX_PLAYERS = 2;
    public final String VERIFICATION = "F7 server here!";

    public String getName() {return name;}

    public String getAddress() {return address;}

    // Need?
    public ServerSocket getServerSocket() {return serverSocket;}

    public Network2(int port, String name) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                address = InetAddress.getLocalHost().getHostAddress();
            } catch (IOException e) {
                // replace this with something else soon
                System.out.println("Server already exists");
            }
        }).start();
    }

    public void openServer() {
        new Thread(() -> {
            try {
                socket = serverSocket.accept();

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printStream = new PrintStream(socket.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void joinServer(String address, int port) throws IOException {
        try {
            socket = new Socket(address, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (ConnectException e) {
            System.out.println("Server with port " + port + " not found");
        }
    }

    private static boolean testServerConnection(String address, int port) throws IOException {
        try {
            Socket socket = new Socket(address, port);
            socket.close();
            return true;
        } catch (ConnectException e) {
            return false;
        }
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
                    if (address.isReachable(5000) && testServerConnection(output, 14000)) {
                        servers.add(address);
                        System.out.println(output + " is on the network");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        return servers;
    }

    public void sendData(String data) {
        printStream.println(data);
    }

    // Once data is read, it is deleted from the buffer
    private Object readData() {
        try {
            return bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public String readString() {
        return (String) readData();
    }

    public int readInt() {
        return Integer.parseInt(readString());
    }

    public double readDouble() {
        return Double.parseDouble(readString());
    }

    public boolean readBoolean() {
        return Boolean.parseBoolean(readString());
    }
}