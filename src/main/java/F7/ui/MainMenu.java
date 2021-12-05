package F7.ui;

import F7.Utils;
import F7.entities.classes.*;
import F7.entities.construction.*;
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

public class MainMenu {
    private static final String LOGO = Ansi.colorize(
            """
            oo++++++++++++++++++++++++++++++++sy`.s+++++++++++++++++++++++++++++o:
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
            """,
    Attribute.TEXT_COLOR(50));

    public static void menu() throws Exception {
        Utils.clear();

        System.out.println(
                LOGO + "\n" +
                """
                1) New Game
                2) Continue
                3) Credits
                4) Quit"""
        );

        String choice = Utils.input(false);

        switch (choice) {
            case "1":
                start();
                break;
            case "2":
                load();
                break;
            case "3":
                credits();
                break;
            case "4":
                quit();
                break;
            default:
                Utils.invalidOption();
                menu();
                break;
        }
    }

    private static void start() throws Exception {
        String name = Utils.input("What is your name?", true);

        Players.player = new Player(name);
        MapMenu.getCurrentMap().spawnPlayer(19, 8);
        Players.player = Players.presentation; //! IS FOR TESTING

        MapMenu.menu();
    }

    private static void load() throws Exception  {
        //Players.player = Player.loadJson();

        MapMenu.getCurrentMap().spawnPlayer(Players.player.getX(), Players.player.getY());

        MapMenu.menu();
    }

    private static void credits() throws Exception {
        System.out.println("Matt.\n\nEmotional Support Animals:\nJordan Dewey\nGirl with the hard to spell name\n\nInput anything to exit.");
        Utils.input(false);
        MainMenu.menu();
    }

    private static void quit() {
        System.exit(0);
    }
}
