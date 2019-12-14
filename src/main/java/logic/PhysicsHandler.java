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
        System.out.print(go.getVelocityY() + gravity);
        go.setVelocityY(go.getVelocityY() + gravity);
        System.out.println(" AND " + go.getVelocityY());
        int newPosX = (int) Math.floor(go.getPosX() + go.getVelocityX());
        int newPosY = (int) Math.floor(go.getPosY() + go.getVelocityY());

        go.updateLocation(newPosX, newPosY);
    }

    public Pair<Integer, Integer> detectCollision(MovableGameObject go,  ArrayList<GameObject> levelObjects) {
        if (!go.getCollidable()) {
            return null;
        }
        Rectangle2D r1 = go.getBounds();
        for (GameObject object : levelObjects) {
            Rectangle2D r2 = object.getBounds();
            if (r1.intersects(r2)){
                
            }
        }
        return null;
    }
}