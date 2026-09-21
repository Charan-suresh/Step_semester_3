interface Attackable {
    String attack();
    String attack(String weaponName);
}

interface Defendable {
    String defend();

    static void resolveDefense(Defendable[] combatants) {
        if (combatants == null) {
            return;
        }
        for (Defendable combatant : combatants) {
            if (combatant != null) {
                System.out.println(combatant.defend());
            }
        }
    }
}

abstract class GameCharacter {
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

class Warrior extends GameCharacter implements Attackable, Defendable {

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

class Trap implements Defendable {
    private final String trapType;

    public Trap(String trapType) {
        if (trapType == null || trapType.trim().isEmpty()) {
            throw new IllegalArgumentException("Trap type cannot be blank");
        }
        this.trapType = trapType.trim();
    }

    public String getTrapType() {
        return trapType;
    }

    @Override
    public String defend() {
        return trapType + " triggers automatically";
    }
}

public class ArenaBattleSimulator {
    public static void resolveDefense(Defendable[] combatants) {
        Defendable.resolveDefense(combatants);
    }

    public static void main(String[] args) {
        Warrior w = new Warrior("Kael");
        System.out.println(w.attack());
        System.out.println(w.attack("Iron Sword"));
        System.out.println(w.defend());
        System.out.println(w.getSpecialMove());

        Trap t = new Trap("Spike Pit");
        System.out.println(t.defend());

        resolveDefense(new Defendable[]{ w, t });
    }
}
