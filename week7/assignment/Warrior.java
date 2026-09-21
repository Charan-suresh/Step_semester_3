public class Warrior extends GameCharacter implements Attackable, Defendable {

    public Warrior(String name) {
        super(name);
    }

    @Override
    public String attack() {
        return getName() + " strikes with a blade";
    }

    @Override
    public String attack(String weaponName) {
        if (weaponName == null || weaponName.trim().isEmpty()) {
            return attack();
        }
        String trimmed = weaponName.trim();
        String prefix;
        if (trimmed.toLowerCase().startsWith("a ") || trimmed.toLowerCase().startsWith("an ")) {
            prefix = "";
        } else {
            char first = Character.toLowerCase(trimmed.charAt(0));
            if (first == 'a' || first == 'e' || first == 'i' || first == 'o' || first == 'u') {
                prefix = "an ";
            } else {
                prefix = "a ";
            }
        }
        return getName() + " strikes with " + prefix + trimmed;
    }

    @Override
    public String defend() {
        return getName() + " raises a shield";
    }

    @Override
    public String getSpecialMove() {
        return getName() + " unleashes Whirlwind Slash";
    }
}
