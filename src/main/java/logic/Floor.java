package logic;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

//For now a complete copy paste of wall, may prove useful later
public class Floor extends GameObject{

    public Floor(int id, String objectType, int width, int height, int startingPosX, int startingPosY, Boolean collidable) {
        super(id, objectType, width, height, startingPosX, startingPosY, collidable);
        
    }

    @Override
    public Paint getPaint() {
        return Color.BLUE;
    }
    
}