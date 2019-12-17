package logic;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

//For now a complete copy paste of wall, may prove useful later
public class Floor extends GameObject{

    public Floor(int id, int width, int height, int startingPosX, int startingPosY) {
        super(id, "floor", width, height, startingPosX, startingPosY, true, false);
        
    }

    @Override
    public Paint getPaint() {
        return Color.BLUE;
    }
    
}