package F7.entities.classes;

public record Consumable(String NAME, int TURNS, Rarity RARITY) {
    @Override
    public String toString() {
        return RARITY.COLOR() + NAME;
    }
}
