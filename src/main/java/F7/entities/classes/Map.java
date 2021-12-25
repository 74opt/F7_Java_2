package F7.entities.classes;

import F7.entities.construction.Maps;
import F7.entities.construction.Players;

import java.util.Arrays;
import java.util.HashMap;

public class Map {
    // Map
    private final String NAME;
    private final Tile[][] MAP;
    private final Map PARENT; // only for cloned maps

    public String getNAME() {return NAME;}

    public Tile[][] getMAP() {return MAP;}

    public Map(String NAME, Tile[][] MAP) {
        this.NAME = NAME;
        this.MAP = MAP;
        this.PARENT = this;
    }

    public Map(Map map) { // to clone a map from another
        this.NAME = map.getNAME();
        this.MAP = Arrays.stream(map.getMAP()).map(Tile[]::clone).toArray(Tile[][]::new); // thank you Gayan Weerakutti from stack overflow
        this.PARENT = map;
    }

    public void spawnPlayer(int x, int y) {
        MAP[y][x] = Maps.getPlayer();
    }

    public boolean movePlayer(String direction, int tiles) {
        HashMap<String, Integer[]> dir = new HashMap<>();
        dir.put("up", new Integer[] {0, -1});
        dir.put("down", new Integer[] {0, 1});
        dir.put("left", new Integer[] {-1, 0});
        dir.put("right", new Integer[] {1, 0});

        for (int i = 0; i < tiles; i++) {
            if (!(MAP[Players.player.getY() + dir.get(direction)[1]][Players.player.getX() + dir.get(direction)[0]].getCOLLISION_ENABLED())) {
                int oldX = Players.player.getX();
                int oldY = Players.player.getY();

                // Sets up player's new position
                Players.player.setX(Players.player.getX() + dir.get(direction)[0]);
                Players.player.setY(Players.player.getY() + dir.get(direction)[1]);

                MAP[oldY][oldX] = PARENT.MAP[oldY][oldX];
                MAP[Players.player.getY()][Players.player.getX()] = Maps.getPlayer();
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String string = "^GLocation: ^W" + NAME + "\n";

        for (int i = 0; i < MAP.length; i++) {
            for (int j = 0; j < MAP[0].length; j++) {
                string += MAP[i][j].getTILE();
            }
            string += "\n";
        }

        return string;
    }
}


