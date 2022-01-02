package F7.entities.classes;

import com.fasterxml.jackson.annotation.JsonCreator;

public final class Tile {
    private final String TILE;
    private final boolean COLLISION_ENABLED;

    public String getTILE() {return TILE;}

    public boolean getCOLLISION_ENABLED() {return COLLISION_ENABLED;}

    public Tile(String TILE, boolean COLLISION_ENABLED) {
        this.TILE = TILE;
        this.COLLISION_ENABLED = COLLISION_ENABLED;
    }
}
