package utils;

public class Vector2 {
    public int x;
    public int y;
    public Vector2(int _x, int _y){
        x = _x; y = _y;
    }
    public Vector2(){
        x = 0; y = 0;
    }

    /**
     * @return A Vector2 with the coordinates (0,0), representing the top left corner of the screen.
     */
    public static Vector2 origin(){
        return new Vector2();
    }

    public boolean equals(Vector2 other){
        return x == other.x && y == other.y;
    }
}
