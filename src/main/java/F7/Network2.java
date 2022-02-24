package F7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

// https://stackoverflow.com/questions/8816870/i-want-to-get-the-ping-execution-time-and-result-in-string-after-ping-host
public class Network2 {
    private Socket socket;
    private ServerSocket serverSocket;
    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private String name;
    private String address;
    private boolean connected = false;

    public static final int MAIN_PORT = 14000;
    private static final int MAX_PLAYERS = 2;
    public static final String MAIN_VERIFICATION = "F7 server here!";
    public static final String BROWSER_VERIFICATION = "F7 browser here!";

    public String getName() {return name;}

    public String getAddress() {return address;}

    // Need?
    public ServerSocket getServerSocket() {return serverSocket;}

    // how to split into constructor and server opener?
    // establish all networking code within the same thread
    // verify different connections with different strings
    //     yes, not secure at all, who cares
    public Network2(int port, String name) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                address = InetAddress.getLocalHost().getHostAddress();

                socket = serverSocket.accept();
                connected = true;

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printStream = new PrintStream(socket.getOutputStream());
                
                sendData("test");
            } catch (IOException e) {
                // replace this with something else soon
                System.out.println("Server already exists");
            }
        }).start();
    }

    public void openServer() throws IOException {
        //new Thread(() -> {
        //    try {
                socket = serverSocket.accept();

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printStream = new PrintStream(socket.getOutputStream());
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //    }
        //}).start();
    }

    public void joinServer(String address, int port) throws IOException {
        try {
            socket = new Socket(address, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());
            
            sendData(MAIN_VERIFICATION);
        } catch (ConnectException e) {
            System.out.println("Server with port " + port + " not found");
        }
    }

    // For the hosting server
    public void checkConnection() {
        String verification = readString();

        try {
            if (verification.equals(MAIN_VERIFICATION)) {
                // keep the connection
            } else if (verification.equals(BROWSER_VERIFICATION)) {
                // send the data then disconnect
                printStream.println(name);
                printStream.println(address);
                printStream.println(getPing());
                printStream.println(getPlayers());
                socket.close();
            } else {
                // disconnect
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: use the verification strings 
    // TODO: keep static?
    private static boolean testServerConnection(String address, int port) throws IOException {
        try {
            Socket socket = new Socket(address, port);
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            printStream.println(BROWSER_VERIFICATION);
            socket.close();
            return true;
        } catch (ConnectException e) {
            return false;
        }
    }

    public ArrayList<InetAddress> retrieveServers() {
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
                    // checks if you can connect to the server, it's an F7 server, and it's not the localhost
                    if (address.isReachable(5000) && testServerConnection(output, MAIN_PORT) && !output.equals(InetAddress.getLocalHost().getHostAddress())) {
                        long ping = System.currentTimeMillis() - initialTime;
                        servers.add(address);
                        System.out.println(output + " is on the network");
                        System.out.println("ping: " + ping + "ms");
                    }
                } catch (SocketException ignored) {
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

    // do i need?
    public static int getPing() {
        return 0;
    }

    public static int getPlayers() {
        return 0;
    }
}