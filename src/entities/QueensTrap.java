package entities;

import utils.Position;

public class QueensTrap extends Trap {

    public QueensTrap(Position position) {
        super("Queen's Trap", 250, 50, 10, 100, 3, 7, position);
    }

    @Override
    protected String getTrapSymbol() {
        return "Q";
    }
}
