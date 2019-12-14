package logic;

import java.util.ArrayList;
import javafx.util.Pair;
import javafx.geometry.Rectangle2D;
public class PhysicsHandler {
    private Double gravity;
    public PhysicsHandler(Double gravity) {
        this.gravity = gravity;
    }

    public void calculateLocation(MovableGameObject go, ArrayList<GameObject> levelObjects) {
        go.setVelocityY(go.getVelocityY() + gravity);
        
        Pair<Integer, Integer> newPositions = detectCollision(go, levelObjects);
        System.out.println(newPositions.getKey() +", "+ newPositions.getValue());
       
        go.updateLocation(newPositions.getKey(), newPositions.getValue());
    }

    private Pair<Integer, Integer> detectCollision(MovableGameObject go,  ArrayList<GameObject> levelObjects) {
        if (!go.getCollidable()) {
            return null;
        }
        int newPosX = (int) Math.floor(go.getPosX() + go.getVelocityX());
        int newPosY = (int) Math.floor(go.getPosY() + go.getVelocityY());
        Rectangle2D r1 = new Rectangle2D(newPosX, newPosY, go.getWidth(), go.getHeight());
      
        
        for (GameObject object : levelObjects) {
            if (object.getCollidable() && go.getID() != object.getID()){ 
                Rectangle2D r2 = object.getBounds();
                if (r1.intersects(r2)){
                    return handleCollision(go, object.getObjectType(), r1, r2);
                }
            }
        }
        return new Pair<>(newPosX, newPosY);
    }

    private Pair<Integer, Integer> handleCollision(MovableGameObject go, String collidedObjectType, Rectangle2D r1, Rectangle2D r2){
        Double factor = 0.99;
        int possiblePosX =  (int) Math.floor(go.getPosX() + go.getVelocityX());
        int possiblePosY =  (int) Math.floor(go.getPosY() + go.getVelocityY());
        
        switch(collidedObjectType){
            case"wall":
                while (r1.intersects(r2)){
                    possiblePosX = (int) Math.floor((go.getVelocityX() * factor) + go.getPosX()); 
                    r1 = new Rectangle2D(possiblePosX, possiblePosY, go.getWidth(), go.getHeight());
                    factor = factor - 0.01;
                }
                go.setVelocityX(0.0);
                break;
            case "floor":
                while (r1.intersects(r2)){
                    possiblePosY = (int) Math.floor((go.getVelocityY() * factor) + go.getPosY()); 
                    r1 = new Rectangle2D(possiblePosX, possiblePosY, go.getWidth(), go.getHeight());
                    factor = factor - 0.01;
                }
                go.setVelocityY(0.0);
                break;
            default:
                break;
        }
        return new Pair<>(possiblePosX, possiblePosY);
    }   


}