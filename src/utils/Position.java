package utils;

public class Position implements Comparable<Position> {
    private int x;
    private int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public double range(Position other)
    {
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int compareTo(Position position) 
    {
        if(this.y == position.getY())
            return Integer.compare(x,position.getX());
        else
            return Integer.compare(y,position.getY());
    }
}
