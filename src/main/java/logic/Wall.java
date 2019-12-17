package logic;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
public class Wall extends GameObject{

    public Wall(int id, int width, int height, int startingPosX, int startingPosY) {
        super(id, "wall", width, height, startingPosX, startingPosY, true, false);
    }
    
    @Override
    public Paint getPaint() {
        return Color.BLUE;
    }
    
}