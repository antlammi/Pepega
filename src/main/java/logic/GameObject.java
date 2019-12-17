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
    private Boolean lethal; 
    public GameObject(int id, String objectType, int width, int height, int startingPosX, int startingPosY, Boolean collidable, Boolean lethal) {
        this.id = id;
        this.objectType = objectType;
        this.width = width;
        this.height = height;
        this.posX = startingPosX;
        this.posY = startingPosY;
        this.collidable = collidable;
        this.lethal = lethal;
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
    public Boolean getLethality(){
        return this.lethal;
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