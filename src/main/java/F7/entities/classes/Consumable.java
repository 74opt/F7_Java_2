package F7.entities.classes;

import com.diogonunes.jcolor.*;

public final class Consumable {
    private final String NAME;
    private final int TURNS;
    private final Rarity RARITY;

    public String getNAME() {return NAME;}

    public int getTURNS() {return TURNS;}

    public Rarity getRARITY() {return RARITY;}

    public Consumable(String NAME, int TURNS, Rarity RARITY) {
        this.NAME = NAME;
        this.TURNS = TURNS;
        this.RARITY = RARITY;
    }

    @Override
    public String toString() {
        return RARITY.getCOLOR() + NAME;
    }
}
