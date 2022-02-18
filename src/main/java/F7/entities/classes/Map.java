package F7.entities.classes;

import F7.entities.construction.Maps;
import F7.entities.construction.Players;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.HashMap;

public class Map {
    // Map
    private final String NAME;
    private final Tile[][] MAP;
    private final Map PARENT; // only for cloned maps

    public String getNAME() {return NAME;}

    public Tile[][] getMAP() {return MAP;}

    public Map getPARENT() {return PARENT;}

    @JsonCreator
    public Map(@JsonProperty("name") String NAME, @JsonProperty("map") Tile[][] MAP, @JsonProperty("parent") Map PARENT) {
        this.NAME = NAME;
        this.MAP = MAP;
        this.PARENT = PARENT;
    }

    public Map(String NAME, Tile[][] MAP) {
        this.NAME = NAME;
        this.MAP = MAP;
        this.PARENT = this;
    }

    // to clone a map from another
    public Map(Map map) {
        this.NAME = map.getNAME();
        this.MAP = Arrays.stream(map.getMAP()).map(Tile[]::clone).toArray(Tile[][]::new); // thank you Gayan Weerakutti from stack overflow
        this.PARENT = map;
    }

    public void spawnPlayer(int x, int y) {
        MAP[y][x] = Maps.getPlayer();
    }

    public void movePlayer(String direction, int tiles) {
        HashMap<String, Integer[]> dir = new HashMap<>();
        dir.put("up", new Integer[] {0, -1});
        dir.put("down", new Integer[] {0, 1});
        dir.put("left", new Integer[] {-1, 0});
        dir.put("right", new Integer[] {1, 0});

        for (int i = 0; i < tiles; i++) {
            if (!(MAP[Players.getPlayer().getY() + dir.get(direction)[1]][Players.getPlayer().getX() + dir.get(direction)[0]].COLLISION_ENABLED())) {
                int oldX = Players.getPlayer().getX();
                int oldY = Players.getPlayer().getY();

                // Sets up player's new position
                Players.getPlayer().setX(Players.getPlayer().getX() + dir.get(direction)[0]);
                Players.getPlayer().setY(Players.getPlayer().getY() + dir.get(direction)[1]);

                MAP[oldY][oldX] = PARENT.MAP[oldY][oldX];
                MAP[Players.getPlayer().getY()][Players.getPlayer().getX()] = Maps.getPlayer();
            } else {
                return;
            }
        }
    }

    @Override
    public String toString() {
        String string = "^GLocation: ^W" + NAME + "\n";

        for (Tile[] t : MAP) {
            for (Tile u : t) {
                string += u.TILE();
            }
            string += "\n";
        }

        return string;
    }
}


