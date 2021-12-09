package F7.entities.classes;

import F7.entities.construction.Maps;
import F7.entities.construction.Players;
import com.diogonunes.jcolor.*;

import java.util.Arrays;

public class Map {
    // Map
    private final String NAME;
    private final Tile[][] MAP;
    private Map parent; // only for cloned maps
    //private Maps maps = new Maps();

    public String getNAME() {return NAME;}

    public Tile[][] getMAP() {return MAP;}

    public Map(String NAME, Tile[][] MAP) {
        this.NAME = NAME;
        this.MAP = MAP;
        this.parent = this;
    }

    public Map(Map map) { // to clone a map from another
        this.NAME = map.getNAME();
        this.MAP = Arrays.stream(map.getMAP()).map(Tile[]::clone).toArray(Tile[][]::new); // thank you Gayan Weerakutti from stack overflow
        this.parent = map;
    }

    public void spawnPlayer(int x, int y) {
        MAP[y][x] = Maps.getPlayer();
    }

    public boolean movePlayer(String direction, int tiles) { // TODO: rewrite with hashmap pwease uwu 
        Integer[] dir = new Integer[2];

        switch (direction) {
            case "up":
                dir = new Integer[] {0, -1};
                break;
            case "down":
                dir = new Integer[] {0, 1};
                break;
            case "left":
                dir = new Integer[] {-1, 0};
                break;
            case "right":
                dir = new Integer[] {1, 0};
                break;
        }

        for (int i = 0; i < tiles; i++) {
            if (!(MAP[Players.player.getY() + dir[1]][Players.player.getX() + dir[0]].getCOLLISION_ENABLED())) {
                int oldX = Players.player.getX();
                int oldY = Players.player.getY();

                // Sets up player's new position
                Players.player.setX(Players.player.getX() + dir[0]);
                Players.player.setY(Players.player.getY() + dir[1]);

                MAP[oldY][oldX] = parent.MAP[oldY][oldX];
                MAP[Players.player.getY()][Players.player.getX()] = Maps.getPlayer();
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String string = "Location: " + Ansi.colorize(NAME, Attribute.TEXT_COLOR(231)) + "\n";

        for (int i = 0; i < MAP.length; i++) {
            for (int j = 0; j < MAP[0].length; j++) {
                string += MAP[i][j].getTILE();
            }
            string += "\n";
        }

        return string;
    }
}


