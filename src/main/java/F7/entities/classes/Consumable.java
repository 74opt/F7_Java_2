package F7.entities.classes;

import com.diogonunes.jcolor.*;

public record Consumable(String NAME, int TURNS, Rarity RARITY) {
    @Override
    public String toString() {
        return Ansi.colorize(NAME, Attribute.TEXT_COLOR(RARITY.COLOR()));
    }
}
