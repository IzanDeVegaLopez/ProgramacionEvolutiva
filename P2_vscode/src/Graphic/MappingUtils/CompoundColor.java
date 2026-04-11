package Graphic.MappingUtils;

import java.awt.*;

public class CompoundColor {
    public Color final_color;
    public int color_count;

    /**
     * Creates an empty compound color to add further colors to.
     */
    public CompoundColor(){
        final_color = new Color(255,255,255);
        color_count = 0;
    }
    public CompoundColor(int n){
        final_color = new Color(n,n,n);
        color_count = 0;
    }
    public CompoundColor(int r, int g, int b){
        final_color = new Color(r,g,b);
        color_count = 0;
    }
    public CompoundColor(Color c){
        final_color = c;
        color_count = 0;
    }

    /**
     * Adds a new color to the compound color and mixes it proportionally with its prior components.
     * @param c The new color to add.
     */
    public void addColor(Color c){
        int new_color_count = color_count+1;
        final_color =  new Color(final_color.getRed()*color_count/new_color_count + c.getRed()/new_color_count,
                final_color.getBlue()*color_count/new_color_count + c.getBlue()/new_color_count,
                final_color.getGreen()*color_count/new_color_count + c.getGreen()/new_color_count);
        color_count = new_color_count;
    }
}
