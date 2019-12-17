package logic;

import java.util.ArrayList;
import javafx.util.Pair;
import javafx.geometry.Rectangle2D;
public class PhysicsHandler {
    private Double gravity;
    private Kuffo kuffo;
    public PhysicsHandler(Double gravity, Kuffo kuffo) {
        this.gravity = gravity;
        this.kuffo = kuffo;
    }

    public void calculateLocation(MovableGameObject go, ArrayList<GameObject> levelObjects) {
        go.setVelocityY(go.getVelocityY() + gravity);
        
        Pair<Integer, Integer> newPositions = detectCollision(go, levelObjects);
        go.updateLocation(newPositions.getKey(), newPositions.getValue());
    }

    private Pair<Integer, Integer> detectCollision(MovableGameObject go,  ArrayList<GameObject> levelObjects) {
        if (!go.getCollidable()) {
            return null;
        }
        int newPosX = (int) Math.floor(go.getPosX() + go.getVelocityX());
        int newPosY = (int) Math.floor(go.getPosY() + go.getVelocityY());
        
        Rectangle2D r1 = new Rectangle2D(newPosX, go.getPosY(), go.getWidth(), go.getHeight());
        Rectangle2D r2 = new Rectangle2D(newPosX, newPosY, go.getWidth(), go.getHeight());
        for (GameObject object : levelObjects) {
            if (object.getCollidable() && go.getID() != object.getID()){ 
                Rectangle2D collidedObject = object.getBounds();
                
                if (r1.intersects(collidedObject)){
                    if (go.getObjectType().equals("kuffo")){
                        kuffo.setAlive(!object.getLethality());
                        if (!object.getLethality()){
                            return handleCollisionX(go, r1, collidedObject);
                        }
                    }

                } else if (r2.intersects(collidedObject)){
                    if (go.getObjectType().equals("kuffo")){
                        kuffo.setAlive(!object.getLethality());
                        if (!object.getLethality()){
                            return handleCollisionY(go, r2, collidedObject);
                        }
                    }
                    
                }
                
            }
        }
        
        return new Pair<>(newPosX, newPosY);
    }

    private Pair<Integer, Integer> handleCollisionX(MovableGameObject go, Rectangle2D r1, Rectangle2D collidedObject){
        Double factor = 0.99;
        int possiblePosX =  (int) Math.floor(go.getPosX() + go.getVelocityX());
       
        while (r1.intersects(collidedObject)){
            possiblePosX = (int) Math.floor((go.getVelocityX() * factor) + go.getPosX()); 
            r1 = new Rectangle2D(possiblePosX, go.getPosY(), go.getWidth(), go.getHeight());
            factor = factor - 0.02;
        }
        go.setVelocityX(0.0);
               
   
        return new Pair<>(possiblePosX, go.getPosY());
    }   

    private Pair<Integer, Integer> handleCollisionY(MovableGameObject go, Rectangle2D r1, Rectangle2D collidedObject){
        if (go.getObjectType().equals("kuffo")){
            kuffo.enableJump();
        }
        Double factor = 0.99;
        int possiblePosX =  (int) Math.floor(go.getPosX() + go.getVelocityX());
        int possiblePosY =  (int) Math.floor(go.getPosY() + go.getVelocityY());

        while (r1.intersects(collidedObject)){
            possiblePosY = (int) Math.floor((go.getVelocityY() * factor) + go.getPosY()); 
            r1 = new Rectangle2D(possiblePosX, possiblePosY, go.getWidth(), go.getHeight());
            factor = factor - 0.02;
        }
        
        go.setVelocityY(0.0);
               
   
        return new Pair<>(possiblePosX, possiblePosY);
    }   


}