package F7.entities.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Rarity {
    private final String NAME;
    private final String COLOR;
    private final int CHANCE;

    public String getNAME() {return NAME;}

    public String getCOLOR() {return COLOR;}

    public int getCHANCE() {return CHANCE;}

    @JsonCreator
    public Rarity(@JsonProperty("name") String NAME, @JsonProperty("color") String COLOR, @JsonProperty("chance") int CHANCE) {
        this.NAME = NAME;
        this.COLOR = COLOR;
        this.CHANCE = CHANCE;
    }

    @Override
    public String toString() {
        return COLOR + NAME;
    }
}
