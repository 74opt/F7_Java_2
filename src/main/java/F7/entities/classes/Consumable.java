package F7.entities.classes;

import com.diogonunes.jcolor.*;
//import org.json.simple.JSONObject;

public record Consumable(String NAME, int TURNS, Rarity RARITY) {
    @Override
    public String toString() {
        return Ansi.colorize(NAME, Attribute.TEXT_COLOR(RARITY.COLOR()));
    }

//    public static Consumable loadJson(JSONObject jsonObject) {
//        return new Consumable((String) jsonObject.get("NAME"), Math.toIntExact((Long) jsonObject.get("TURNS")), Rarity.loadJson((JSONObject) jsonObject.get("RARITY")));
//    }
}
