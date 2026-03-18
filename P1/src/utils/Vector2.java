package utils;

import java.util.Objects;

public class Vector2 {
    public int x;
    public int y;
    private int hashCode;
    public Vector2(int _x, int _y){
        x = _x; y = _y;
        this.hashCode = Objects.hash(x,y);
    }
    public Vector2(){
        x = 0; y = 0;
        this.hashCode = Objects.hash(x,y);
    }

    /**
     * @return A Vector2 with the coordinates (0,0), representing the top left corner of the screen.
     */
    public static Vector2 origin(){
        return new Vector2();
    }

    public Vector2 clone(){
        return new Vector2(this.x, this.y);
    }

    @Override
    public boolean equals(Object other){
        if(this == other) return true;
        if(other==null || getClass() != other.getClass()) return false;
        Vector2 that = (Vector2) other;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
