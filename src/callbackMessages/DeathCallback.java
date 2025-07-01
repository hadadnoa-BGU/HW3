package callbackMessages;

import entities.Enemy;

@FunctionalInterface
public interface DeathCallback {
    void onDeath(Enemy enemy);
}
