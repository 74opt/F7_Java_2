package F7.entities.classes;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

public record Rarity(String NAME, int COLOR, int CHANCE) {
    @Override
    public String toString() {
        return Ansi.colorize(NAME, Attribute.TEXT_COLOR(COLOR));
    }
}
