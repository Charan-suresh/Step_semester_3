public interface Defendable {
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

    static void main(String[] args) {
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
