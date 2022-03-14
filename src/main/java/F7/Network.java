package F7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import F7.entities.construction.Players;
import F7.ui.MapMenu;

// https://stackoverflow.com/questions/8816870/i-want-to-get-the-ping-execution-time-and-result-in-string-after-ping-host
public class Network {
    private Socket socket;
    private ServerSocket serverSocket;
    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private String name;
    private String address;
    private int players;
    private boolean connected = false; //! DO I NEED?

    public static final int MAIN_PORT = 14000;
    public static final int MAX_PLAYERS = 2;
    public static final String MAIN_VERIFICATION = "F7 server here!";
    public static final String BROWSER_VERIFICATION = "F7 browser here!";

    public String getName() {return name;}

    public String getAddress() {return address;}

    // Need?
    public ServerSocket getServerSocket() {return serverSocket;}
    public Socket getSocket() {return socket;}
    public PrintStream getPrintStream() {return printStream;}
    public BufferedReader getBufferedReader() {return bufferedReader;}

    // how to split into constructor and server opener?
    // establish all networking code within the same thread
    // verify different connections with different strings
    //     yes, not secure at all, who cares
    public Network(int port, String name) {
        new Thread(() -> {
            try {
                this.serverSocket = new ServerSocket(port);
                this.address = InetAddress.getLocalHost().getHostAddress();
                this.name = name;
                this.players = 1;

                open();
            } catch (IOException e) {
                // replace this with something else soon
            }
        }).start();
    }

    private void open() throws IOException {
        if (players < MAX_PLAYERS) {
            socket = serverSocket.accept();
            connected = true; //! do i need?

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());

            checkConnection();
        }
    }

    // TODO: implement 
    public void close() {
        try {
            serverSocket.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void join(String address, int port) throws IOException {
        try {
            socket = new Socket(address, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());
            
            sendData(MAIN_VERIFICATION);
        } catch (ConnectException e) {}
    }

    // For the hosting server
    private void checkConnection() {
        new Thread(() -> {
            while (true) {
                String verification = readString(); 

                try {
                    if (verification.equals(MAIN_VERIFICATION)) {
                        // keep the connection
                        this.players++;
                        printStream.println(Players.getPlayer().getX());
                        printStream.println(Players.getPlayer().getY());
                        // need smth with getting map if i ever implement more than one
                        break;
                    } else if (verification.equals(BROWSER_VERIFICATION)) { 
                        // send the data then disconnect
                        printStream.println(name + "," + players);
                        socket.close();
                        open();
                        break;
                    } else { //! probably shouldnt have this?
                        // disconnect
                        socket.close();
                        open();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // TODO: keep static?
    // TODO: make return server info (is this the time where i must use the raw arraylist)
    // ^ remember that printstream only works with strings
    public static String testConnection(String address, int port) throws IOException {
        try {
            Socket socket = new Socket(address, port);
            long initialTime = System.currentTimeMillis();
            long ping = 0;
            if (InetAddress.getByName(address).isReachable(1000)) {
                ping = System.currentTimeMillis() - initialTime;
            }
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream.println(BROWSER_VERIFICATION);
            String data = ping + "," + bufferedReader.readLine();
            socket.close();
            return data;
        } catch (ConnectException e) {
            return null;
        }
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
    public static int getPing(InetAddress address) throws IOException {
        long initialTime = System.currentTimeMillis();
        if (address.isReachable(5000)) {
            return (int) (System.currentTimeMillis() - initialTime);
        } else {
            return -1;
        }
    }

    public static int getPlayers() {
        return 0;
    }
}