package mechanics;

import entities.Unit;
import util.RandomUtils;

public class CombatSystem {

    public static void engageCombat(Unit attacker, Unit defender) {
        int attackRoll = RandomUtils.randomInt(0, attacker.getAttackPoints());
        int defenseRoll = RandomUtils.randomInt(0, defender.getDefensePoints());
        int damage = attackRoll - defenseRoll;

        System.out.println(attacker.getName() + " rolls " + attackRoll + " attack points.");
        System.out.println(defender.getName() + " rolls " + defenseRoll + " defense points.");

        if (damage > 0) {
            defender.receiveDamage(damage);
            System.out.println(attacker.getName() + " hits " + defender.getName() + " for " + damage + " damage.");
        } else {
            System.out.println(attacker.getName() + " missed.");
        }
    }
}
