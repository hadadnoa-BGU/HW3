package model.tiles;

import model.tiles.units.Unit;
import model.tiles.units.players.Player;
import utils.Position;

public abstract class Tile 
{
    protected Position position;
    protected char tile;

    public Tile(char tile)
    {

        this.tile = tile;
    }

    public char getTile()
    {
        return this.tile;
    }

    public Tile initialize(Position p)
    {
        this.position = p;
        return this;
    }

    @Override
    public String toString()
    {
      return String.valueOf(tile);
    }

    public abstract void accept(Unit unit);

    public void swapPosition(Tile t)
    {
        Position temp = t.position;
        t.position = this.position;
        this.position = temp;
    }

    public Position getPosition()
    {
        return position;
    }

    public int compareTo(Tile tile) 
    {
        return getPosition().compareTo(tile.getPosition());
    }

    public void setPosition(Position position) 
    {
        this.position = position;
    }
}
