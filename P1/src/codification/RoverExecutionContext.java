package codification;

import utils.Vector2;

public class RoverExecutionContext {
    public enum rotationDirection{
        RD_LEFT,
        RD_RIGHT
    }
    public static Vector2 DIRECTIONS [] =
    {
        new Vector2(1,0), // R
        new Vector2(0,1), // U
        new Vector2(-1,0),// L
        new Vector2(0,-1) // D
    };
    int lookingAtIdx = 0;
    Vector2 currentTile = new Vector2(1,1);
    public void rotate(rotationDirection rotDir){
        if(rotDir==rotationDirection.RD_RIGHT){
            lookingAtIdx = lookingAtIdx-1;
            if(lookingAtIdx < 0) lookingAtIdx += 3;
        }else if(rotDir==rotationDirection.RD_LEFT){
            lookingAtIdx = lookingAtIdx+1 %4;
        }
    }

    public void advance(){
        currentTile.add(DIRECTIONS[lookingAtIdx]);
    }
}
