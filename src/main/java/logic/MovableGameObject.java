package logic;
public class MovableGameObject extends GameObject {
    private Double velocityX;
    private Double velocityY;
    
    public MovableGameObject(int id, String objectType, int width, int height, int startingPosX, int startingPosY, Double velocityX, Double velocityY, Boolean collision, Boolean lethal) {
        super(id, objectType, width, height, startingPosX, startingPosY, collision, lethal);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public Double getVelocityX() {
        return this.velocityX;
    }

    public Double getVelocityY() {
        return this.velocityY;
    }
    public void setVelocityX(Double velocityX) {
        this.velocityX = velocityX;
    }
    public void setVelocityY(Double velocityY) {
        this.velocityY = velocityY;
    }
    public void updateLocation(int x, int y) {
        super.setPosX(x);
        super.setPosY(y);
    }
}