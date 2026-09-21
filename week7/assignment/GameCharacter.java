public abstract class GameCharacter {
    private static int counter = 1000;
    private final String characterId;
    protected String name;

    public GameCharacter(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        counter++;
        this.characterId = "GC-" + counter;
        this.name = name.trim();
    }

    public String getCharacterId() {
        return characterId;
    }

    public String getName() {
        return name;
    }

    public abstract String getSpecialMove();
}
