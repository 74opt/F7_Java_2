package F7.entities.classes;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
//import org.json.simple.JSONObject;

public record Rarity(String NAME, int COLOR, int CHANCE) {
    @Override
    public String toString() {
        return Ansi.colorize(NAME, Attribute.TEXT_COLOR(COLOR));
    }

//    public static Rarity loadJson(JSONObject jsonObject) {
//        return new Rarity((String) jsonObject.get("NAME"), Math.toIntExact((Long) jsonObject.get("COLOR")), Math.toIntExact((Long) jsonObject.get("CHANCE")));
//    }
}
