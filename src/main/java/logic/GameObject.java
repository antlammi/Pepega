package logic;

import javafx.scene.paint.Paint;
import javafx.geometry.Rectangle2D;

public class GameObject {
    private Boolean collidable;
    private int width;
    private int height;
    private int posX;
    private int posY;
    private Paint paint;
    private final int id;
    private String objectType;
    public GameObject(int id, String objectType, int width, int height, int startingPosX, int startingPosY, Boolean collidable) {
        this.id = id;
        this.objectType = objectType;
        this.width = width;
        this.height = height;
        this.posX = startingPosX;
        this.posY = startingPosY;
        this.collidable = collidable;
        
    }

    public int getID(){
        return this.id;
    }
    public String getObjectType(){
        return this.objectType;
    }
    public Boolean getCollidable(){
        return collidable;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public Paint getPaint(){
        return paint;
    }
    public void setPosX(int posX){
        this.posX = posX;
    }
    public void setPosY(int posY){
        this.posY = posY;
    }
   
    public Rectangle2D getBounds(){
        return new Rectangle2D(posX, posY, width, height);
    }
   
}