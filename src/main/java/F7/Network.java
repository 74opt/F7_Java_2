package F7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import F7.entities.construction.Maps;
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

                System.out.println("network constructed");
                open();
            } catch (IOException e) {
                // replace this with something else soon
                e.printStackTrace();
            }
        }).start();
    }

    /*
    TODO: only problem is that info isnt being sent correctly?
     */
    private void open() throws IOException {
        if (players < MAX_PLAYERS) {
            System.out.println("open method start");
            // The method blocks until a connection is made.
            socket = serverSocket.accept();
            connected = true; //! do i need?

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());

            System.out.println("serverSocket.accept worked");
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

    /*
    Do whatever the hell you did with those verification strings
    those sending seemed to work

    or maybe sending the browser strings dont let you send any more?

    look back on code ok
     */
    public void join(String address, int port) throws IOException {
        try {
            System.out.println("join method");
            socket = new Socket(address, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());
            
            sendData(MAIN_VERIFICATION);
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }

    // For the hosting server
    private void checkConnection() {
        new Thread(() -> {
            while (true) {
                String verification = readString(); 

                try {
                    if (verification.equals(MAIN_VERIFICATION)) {
                        if (Players.getPlayer().getX() == 19 && Players.player.getY() == 8) {
                            // move player somewhere else
                        }
                        MapMenu.setIsMultiplayer(true);
                        System.out.println("this is the one baby");
                        // keep the connection
                        this.players++;
                        printStream.println(Players.getPlayer().getX() + "," + Players.getPlayer().getY());
                        MapMenu.getCurrentMap().setTile(Maps.getPlayer(), 19, 8);
                        // need smth with getting map if i ever implement more than one
                        break;
                    } else if (verification.equals(BROWSER_VERIFICATION)) {
                        // send the data then disconnect
                        System.out.println("Brows");
                        printStream.println(name + "," + players);
                        socket.close();
                        open();
                        break;
                    } else { //! probably shouldnt have this?
                        // disconnect
                        System.out.println("I think you messed up dude");
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

    // TODO: rename method?
    public static String testConnection(String address, int port) throws IOException {
        try {
            System.out.println("testConnection called with " + address);
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
            System.out.println("things be workin fr fr");
            return data;
        } catch (ConnectException e) {
            System.out.println(address + " failed to connect");
            return null;
        }
    }

    //! when sending multiple things, make sure to use a delimiter (,)
    public void sendData(String data) {
        printStream.println(data);
    }

    // Once data is read, it is deleted from the buffer
    private Object readData() {
        try {
            String ver = bufferedReader.readLine();
            System.out.println("read: " + ver);
            return ver;
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