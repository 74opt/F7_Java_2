package F7.entities.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Consumable {
    private final String NAME;
    private final int TURNS;
    private final Rarity RARITY;

    public String getNAME() {return NAME;}

    public int getTURNS() {return TURNS;}

    public Rarity getRARITY() {return RARITY;}

    @JsonCreator
    public Consumable(@JsonProperty("name") String NAME, @JsonProperty("turns") int TURNS, @JsonProperty("rarity") Rarity RARITY) {
        this.NAME = NAME;
        this.TURNS = TURNS;
        this.RARITY = RARITY;
    }

    @Override
    public String toString() {
        return RARITY.getCOLOR() + NAME;
    }
}
