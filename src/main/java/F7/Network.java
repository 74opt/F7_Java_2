package F7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Network {
    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    // make following void or boolean?
    public static void startServer() {
        
    }

    public static void joinServer() {

    }

    public static ArrayList<ServerSocket> retrieveServers() {
        return new ArrayList<ServerSocket>();
    }
}
