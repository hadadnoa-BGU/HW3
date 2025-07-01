package entities;

import utils.Position;

public class DeathTrap extends Trap {

    public DeathTrap(Position position) {
        super("Death Trap", 500, 100, 20, 250, 1, 10, position);
    }

    @Override
    protected String getTrapSymbol() {
        return "D";
    }
}
