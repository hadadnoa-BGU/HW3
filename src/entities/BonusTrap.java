package entities;

import utils.Position;

public class BonusTrap extends Trap {
    public BonusTrap(Position pos) {
        super("Bonus Trap", 1, 1, 1, 250, 1, 5, pos);
    }

    @Override
    protected String getTrapSymbol() {
        return "B";
    }
}
