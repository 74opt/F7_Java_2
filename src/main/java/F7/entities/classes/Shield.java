package F7.entities.classes;

// org.json.simple.JSONObject;
import com.diogonunes.jcolor.*;

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
    public Shield(String NAME, int DAMAGE_REDUCTION, int TURNS, int COOLDOWN, Rarity RARITY) {
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
            %s %s
            %s %s%%
            %s %s turns
            %s %s turns""",
            RARITY.toString(), NAME,
            Ansi.colorize("Damage Reduction:", Attribute.TEXT_COLOR(231)), DAMAGE_REDUCTION,
            Ansi.colorize("Active Time:", Attribute.TEXT_COLOR(231)), TURNS,
            Ansi.colorize("Cooldown Time:", Attribute.TEXT_COLOR(231)), COOLDOWN
            );
        }
    }

//    public static Shield loadJson(JSONObject jsonObject) {
//        return new Shield((String) jsonObject.get("NAME"), Math.toIntExact((Long) jsonObject.get("DAMAGE_REDUCTION")), Math.toIntExact((Long) jsonObject.get("TURNS")), Math.toIntExact((Long) jsonObject.get("COOLDOWN")), Rarity.loadJson((JSONObject) jsonObject.get("RARITY")));
//    }
}
