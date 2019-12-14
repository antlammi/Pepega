package logic;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.paint.ImagePattern;
public class Kuffo extends MovableGameObject {
    private final double maximumVelocityX = 5.0;
    private final double minimumVelocityX = -5.0;
    private final double maximumVelocityY = 5.0;
    private final double minimumVelocityY = -5.0;
    public Kuffo(int width, int height, int startingPosX, int startingPosY, Double velocityX, Double velocityY, Boolean collidable) {
        super(width, height, startingPosX, startingPosY, velocityX, velocityY, collidable);
    }
 
    public void incrementVelocityX() {
        super.setVelocityX(Math.min(super.getVelocityX()+0.25, maximumVelocityX));
    }
    public void decrementVelocityX() {
        super.setVelocityX(Math.max(super.getVelocityX()-0.25, minimumVelocityX));
    }
    public void incrementVelocityY() {
        super.setVelocityY(Math.min(super.getVelocityY()+0.25, maximumVelocityY));
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
        Image img = (super.getVelocityX() >= 0) ? new Image("kuffo.png") : new Image("kuffo_reversed.png");
        Paint fillImage = new ImagePattern(img);
        return fillImage;
    }
}