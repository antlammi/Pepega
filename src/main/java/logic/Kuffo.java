package logic;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayDeque;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class Kuffo extends MovableGameObject {
    private final double maximumVelocityX = 5.0;
    private final double minimumVelocityX = -5.0;
    private final double maximumVelocityY = 8.0;
    private final double minimumVelocityY = -8.0;
    private Boolean canJump;
    private ArrayDeque<Quadruplet<Integer, Integer, Boolean, Double>> trail;
    private Double currentRotation;
    private Boolean alive;

    public Kuffo(int id, int width, int height, int startingPosX, int startingPosY, Double velocityX, Double velocityY) {
        super(id, "kuffo", width, height, startingPosX, startingPosY, velocityX, velocityY, true, false);
        this.currentRotation = 0.0;
        trail = new ArrayDeque<>();
        canJump = true;
        this.alive = true;
        
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
        if (canJump){
            super.setVelocityY(-8.0);
            this.canJump = false;
        }
    }

    public void enableJump(){
        this.canJump = true;
    }
    public void disableJump(){
        this.canJump = false;
    }
    public void teleport(int x, int y){        
        super.setPosX(x);
        super.setPosY(y);
    }

    public Quadruplet<Integer, Integer, Boolean, Double> pollTrail(){
        return this.trail.poll();
    }
    public int getTrailSize(){
        return this.trail.size();
    }
    public void addToTrail(Quadruplet<Integer, Integer, Boolean, Double> trailEntry){
        this.trail.add(trailEntry);
    }
    public Boolean isAlive(){
        return this.alive;
    }

    public void setAlive(Boolean alive){
        this.alive = alive;
    }

    public Double getCurrentRotation(){
        return this.currentRotation;
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
        currentRotation = (currentRotation < 360) ? currentRotation + (getVelocityX()*2) : 0;

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image img = iv.snapshot(params, null);

        Paint fillImage = new ImagePattern(img);
        
        return fillImage;
    }
}