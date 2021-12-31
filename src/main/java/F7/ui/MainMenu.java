package F7.ui;

import java.io.*;
import F7.Lanterna;
import F7.Utils;
import F7.entities.classes.*;
import F7.entities.construction.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

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

    private static Thread keyboardListen = new Thread(() -> {
        boolean running = true;

        while (running) {
            try {
                KeyStroke keyPressed = Lanterna.getScreen().pollInput();

                if (keyPressed != null) {
                    try {
                        switch (keyPressed.getCharacter()) {
                            case '1' -> {
                                start();
                                running = false;
                            }
                            case '2' -> {
                                load();
                                running = false;
                            }
                            case '3' -> System.exit(0);
                        }
                    } catch (Exception ignored) {}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    // Threading should only be used when multiple things have to happen per frame
    // Examples: animation, combat
    public static void menu() throws Exception {
        Lanterna.clear();
        keyboardListen.start();

        Lanterna.println(
                LOGO + "\n" +
                """
                ^G1) New Game
                2) Continue
                3) Quit"""
        );
    }

    private static void start() throws Exception {
        Lanterna.print("\n^WWhat is your name?\n^g> ^G");
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

                                Lanterna.print(name.length() + 3, Lanterna.getGlobalRow(), " ");
                                Lanterna.print(3, Lanterna.getGlobalRow(), name);
                            }
                            case Enter -> running = false;
                            default -> {
                                try {
                                    name += keyPressed.getCharacter();

                                    Lanterna.print(3, Lanterna.getGlobalRow(), name);
                                } catch (Exception ignored) {}
                            }
                        }
                    } catch (Exception ignored) {}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Players.player = new Player(name);

        //! IS FOR TESTING DELETE LATER
        Players.player = Players.presentation;
        MapMenu.getCurrentMap().spawnPlayer(19, 8);

        MapMenu.menu();
    }

    private static void load() throws Exception  {
        ObjectMapper objectMapper = new ObjectMapper();

        Players.player = objectMapper.readValue(new File(Utils.SAVE_PATH), Player.class);

        MapMenu.getCurrentMap().spawnPlayer(Players.player.getX(), Players.player.getY());

        MapMenu.menu();
    }
}
