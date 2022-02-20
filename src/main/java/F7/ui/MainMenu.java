package F7.ui;

import java.io.*;
import F7.Lanterna;
import F7.Utils;
import F7.entities.classes.*;
import F7.entities.construction.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

/*
TODO: Main Menu overhaul
1) Singleplayer
    1) New Character
    2) Load Character
    3) Exit
2) Multiplayer (Create Server)
    1) New Character
        1) Host Server -> Start Game
        2) Join Server -> Server Browser
        3) Exit
    2) Load Character
        1) Host Server -> Start Game
        2) Join Server -> Server Browser
        3) Exit
    3) Exit
3) Quit
*/

public class MainMenu {
    private static final String LOGO =
            """
            ^Coo++++++++++++++++++++++++++++++++sy`.s+++++++++++++++++++++++++++++o:
            h.                              -s+.oo`                              /s:
            h.                            -s/.oo`                                  :s:
            h.                          -s/.o+`                                      :s:
            h.                        -s/.o+`                                          :s:
            h.      +++++++++++++++++s/.yh:------------------------------------`         /h
            h.      m                  `-------------------------------------:d`         y-
            h.      m                                                       `h.         o/
            h.      m                                                       h-         +o
            h.      m                                                      y:         :y
            h.      m                                                     s/         .h`
            h.      h+++++++++++++++o/                                   o+         `h`
            h.                      ++                                  /s          h.
            h.                      ++                                 :y          y:
            h.     oo+++++++++++++++o-                                -h          o/
            h.     y-                                                .h`         /o
            h.     y-                                               `h.         :y
            h.     y-                                               h.         .h`
            h.     y-                                              y:         `h`
            h.     y-                                             s/          h-
            h.     y-                                            o+          y:
            h.     y-                                           /s          o+
            h-     y-                                          :y          /s
          .s+      y-                                         -h          -y
        `so`       y-                                        .h`         .h`
       `d:         y-                                       `h`         `h`
         /s-       y-                                      `h.          h.
           /s-     y-                                      y-          y:
             /s-   y-                                     s:          o+
               /s- y-                                    o+          /s
                 /sd-                                   /s          -y
                   :`                                  .m+++++++++++h`
            """;

    public static void menu() throws Exception {
        Lanterna.clear();

        Lanterna.print(1, 1,
                LOGO + "\n" +
                        """
                        ^G1) New Game
                        2) Continue
                        3) Quit"""
        );

        new Thread(() -> {
            boolean running = true;

            while (running) {
                try {
                    KeyStroke keyPressed = Lanterna.getScreen().pollInput();

                    if (keyPressed != null) {
                        try {
                            switch (keyPressed.getCharacter()) {
                                case '1' -> {
                                    running = false;
                                    start();
                                }
                                case '2' -> {
                                    running = false;
                                    load();
                                }
                                case '3' -> System.exit(0);
                            }
                        } catch (Exception ignored) {}
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void start() throws Exception {
        Lanterna.print(1, 37, "\n^WWhat is your name?\n^g> ^G");
        String name = "";
        boolean running = true;

        while (running) {
            try {
                KeyStroke keyPressed = Lanterna.getScreen().readInput();

                if (keyPressed != null) {
                    if (keyPressed.getKeyType().equals(KeyType.F7)) {
                        System.exit(0);
                    }

                    try {
                        switch (keyPressed.getKeyType()) {
                            case Backspace, Delete -> {
                                name = name.substring(0, name.length() - 1);

                                Lanterna.print(name.length() + 3, 39, " ");
                                Lanterna.print(3, 39, name);
                            }
                            case Enter -> running = false;
                            default -> {
                                try {
                                    name += keyPressed.getCharacter();

                                    Lanterna.print(3, 39, name);
                                } catch (Exception ignored) {}
                            }
                        }
                    } catch (Exception ignored) {}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MapMenu.setCurrentMap(new Map(Maps.getPlains()));
        Players.setPlayer(new Player(name));

        //! IS FOR TESTING DELETE LATER
        //Players.getPlayer() = Players.presentation;

        MapMenu.getCurrentMap().spawnPlayer(19, 8);

        MapMenu.menu();
    }

    // this appears to be working and i sure hope it does buddy
    private static void load() throws Exception  {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Players.setPlayer(objectMapper.readValue(new File(Utils.PLAYER_SAVE_PATH), Player.class));
            MapMenu.setCurrentMap(objectMapper.readValue(new File(Utils.MAP_SAVE_PATH), Map.class));

            MapMenu.getCurrentMap().spawnPlayer(Players.getPlayer().getX(), Players.getPlayer().getY());
        } catch (Exception e) {
            e.printStackTrace();
        }

        MapMenu.menu();
    }

    private static void singleplayer() throws Exception {
        
    }

    private static void multiplayer() throws Exception {
        
    }
}
