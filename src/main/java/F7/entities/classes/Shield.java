package F7.entities.classes;

import com.diogonunes.jcolor.*;
import com.fasterxml.jackson.annotation.*;

public class Shield {
    private final String NAME;
    private final int DAMAGE_REDUCTION, TURNS, COOLDOWN;
    private final Rarity RARITY;

    // Setters and Getters
    public String getNAME() {return NAME;}

    public int getDAMAGE_REDUCTION() {return DAMAGE_REDUCTION;}

    public int getTURNS() {return TURNS;}

    public int getCOOLDOWN() {return COOLDOWN;}

    public Rarity getRARITY() {return RARITY;}

    // Constructor
    public Shield(@JsonProperty("name") String NAME, @JsonProperty("damage_REDUCTION") int DAMAGE_REDUCTION, @JsonProperty("turns") int TURNS, @JsonProperty("cooldown") int COOLDOWN, @JsonProperty("rarity") Rarity RARITY) {
        this.NAME = NAME;
        this.DAMAGE_REDUCTION = DAMAGE_REDUCTION;
        this.TURNS = TURNS;
        this.COOLDOWN = COOLDOWN;
        this.RARITY = RARITY;
    }

    public Shield(Shield shield) {
        this.NAME = shield.getNAME();
        this.DAMAGE_REDUCTION = shield.getDAMAGE_REDUCTION();
        this.TURNS = shield.getTURNS();
        this.COOLDOWN = shield.getCOOLDOWN();
        this.RARITY = shield.getRARITY();
    }

    // Methods
    public String toString(boolean simple) {
        if (simple) {
            return String.format("%s %s", RARITY.toString(), NAME);
        } else {
            return String.format("""
            %s ^G%s
            ^W%s ^G%s%%
            ^W%s ^G%s turns
            ^W%s ^G%s turns""",
            RARITY.toString(), NAME,
            "Damage Reduction:", DAMAGE_REDUCTION,
            "Active Time:", TURNS,
            "Cooldown Time:", COOLDOWN
            ); 
        }
    }
}
