package logic;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
public class Wall extends GameObject{

    public Wall(int width, int height, int startingPosX, int startingPosY, Boolean collidable) {
        super(width, height, startingPosX, startingPosY, collidable);
    }

    @Override
    public Paint getPaint() {
        return Color.BLUE;
    }
    
}