package entities;

import utils.Position;

public class Boss extends Monster implements HeroicUnit {

    private final int abilityFrequency;
    private int combatTicks;

    public Boss(String name, int healthPool, int attackPoints, int defensePoints, int experienceValue,
                int visionRange, int abilityFrequency, Position position) {
        super(name, healthPool, attackPoints, defensePoints, experienceValue, visionRange, position);
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;
    }


    public void takeTurn(Position playerPosition, Unit player) {
        super.takeTurn(playerPosition);

        if (position.distance(playerPosition) < getVisionRange()) {
            combatTicks++;
            if (combatTicks >= abilityFrequency) {
                combatTicks = 0;
                castAbility(player);
            }
        } else {
            combatTicks = 0;
        }
    }

    @Override
    public void castAbility(Unit target) {
        if (target != null && position.distance(target.getPosition()) <= getVisionRange()) {
            System.out.println(getName() + " uses Shoebodybop on " + target.getName() +
                    " for " + attackPoints + " damage!");
            engageCombat(target);
        }
    }

    @Override
    public String toString() {
        switch (name) {
            case "Queen Cersei":
                return "C";
            case "Night's King":
                return "K";
            default:
                return "B"; // Generic Boss fallback
        }
    }

}
