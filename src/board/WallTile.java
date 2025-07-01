package model.tiles;

import model.tiles.units.Unit;
import utils.Position;

public class WallTile extends Tile 
{
    public static final char WALL_TILE='#';
    public Wall(Position p)
    {
        super(WALL_TILE);
        initialize(p);
    }

    @Override
    public void accept(Unit unit)
    {
        unit.visit(this);
    }
}
