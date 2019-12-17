package logic;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.paint.ImagePattern;

public class Spikes extends GameObject {

    public Spikes(int id, int width, int height, int startingPosX, int startingPosY) {
        super(id, "spikes", width, height, startingPosX, startingPosY, true, true);
    }
    
    @Override
    public Paint getPaint() {
        return new ImagePattern(new Image("Spikes.png")); 
    }
    
}