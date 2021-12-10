package F7.entities.classes;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Rarity {
    private final String NAME;
    private final int COLOR;
    private final int CHANCE;

    public String getNAME() {return NAME;}

    public int getCOLOR() {return COLOR;}

    public int getCHANCE() {return CHANCE;}

    public Rarity(String NAME, int COLOR, int CHANCE) {
        this.NAME = NAME;
        this.COLOR = COLOR;
        this.CHANCE = CHANCE;
    }

    @Override
    public String toString() {
        return Ansi.colorize(NAME, Attribute.TEXT_COLOR(COLOR));
    }
}
