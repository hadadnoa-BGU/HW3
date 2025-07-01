package utils.callbacks;

import model.tiles.units.Enemies.Enemy;
import model.tiles.units.Unit;

public interface DeathCallback {
    void call(Enemy e);
}


