package F7.entities.classes;

public record Rarity(String NAME, String COLOR, int CHANCE) {
    @Override
    public String toString() {
        return COLOR + NAME;
    }
}
