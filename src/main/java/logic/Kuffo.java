package logic;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.paint.ImagePattern;


import javafx.scene.SnapshotParameters;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class Kuffo extends MovableGameObject {
    private final double maximumVelocityX = 5.0;
    private final double minimumVelocityX = -5.0;
    private final double maximumVelocityY = 10.0;
    private final double minimumVelocityY = -10.0;
    private Double currentRotation;
   
    public Kuffo(int id, String objectType, int width, int height, int startingPosX, int startingPosY, Double velocityX, Double velocityY, Boolean collidable) {
        super(id, objectType, width, height, startingPosX, startingPosY, velocityX, velocityY, collidable);
        currentRotation = 0.0;
    }
 
    public void incrementVelocityX() {
        super.setVelocityX(Math.min(super.getVelocityX()+1, maximumVelocityX));
    }
    public void decrementVelocityX() {
        super.setVelocityX(Math.max(super.getVelocityX()-1, minimumVelocityX));
    }
    public void incrementVelocityY() {
        super.setVelocityY(Math.min(super.getVelocityY()+1, maximumVelocityY));
    }
    public void jump() {
        super.setVelocityY(minimumVelocityY);
    }
    @Override
    public void setVelocityX(Double velocityX) {
        super.setVelocityX((velocityX >= 0) ? Math.min(velocityX, maximumVelocityX) : Math.max(velocityX, minimumVelocityX));
    }
    @Override
    public void setVelocityY(Double velocityY) {
        super.setVelocityY((velocityY >= 0) ? Math.min(velocityY, maximumVelocityY) : Math.max(velocityY, minimumVelocityY));
    }
    @Override
    public Paint getPaint() {
        ImageView iv = (super.getVelocityX() >= 0) ? new ImageView(new Image("kuffo.png")) : new ImageView(new Image("kuffo_reversed.png"));

        iv.setRotate(currentRotation);
        currentRotation = (currentRotation < 360) ? currentRotation + getVelocityX()*2 : 0;

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image img = iv.snapshot(params, null);

        Paint fillImage = new ImagePattern(img);
        
        return fillImage;
    }
}