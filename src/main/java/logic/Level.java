package logic;
import java.util.ArrayList;
public class Level{
    private ArrayList<GameObject> levelObjects;
    private int currentObjectID;
    private int width; 
    private int height;
    public Level(int levelCode, int width, int height){
        this.width = width;
        this.height = height;
        this.currentObjectID = 1;
        this.levelObjects = new ArrayList<GameObject>();
        switch(levelCode){
            case 1:
                buildLevel();
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    public void buildLevel(){
        MovableGameObject go1 = buildMovableGameObject("kuffo", 30, 30, 50, 500, 0.0, 0.0, true);
        GameObject go2 = buildGameObject("wall", 30, 300, 500, 500, true);
        GameObject go3 = buildGameObject("wall", 30, 300, 400, 400, true);
        GameObject go4 = buildGameObject("floor", this.width, 10, 0, 790, true);
        GameObject go5 = buildGameObject("wall", 30, this.height, 0, 0, true);
        this.levelObjects.add(go1);
        this.levelObjects.add(go2);
        this.levelObjects.add(go3);
        this.levelObjects.add(go4);
        this.levelObjects.add(go5);
    }
    
    private GameObject buildGameObject(String objectType, int width, int height, int startingPosX, int startingPosY, Boolean collidable){
        GameObject go;
       
        switch(objectType.toLowerCase()){
            case "wall":
                go = new Wall(currentObjectID, objectType, width, height, startingPosX, startingPosY, collidable);
                currentObjectID++;
                break;
            case "floor":
                go = new Floor(currentObjectID, objectType, width, height, startingPosX, startingPosY, collidable);
                currentObjectID++;
                break;
            default:
                go = null;
                break;
        }
        return go;
    }
    private MovableGameObject buildMovableGameObject(String objectType, int width, int height, int startingPosX, int startingPosY, Double velocityX, Double velocityY, Boolean collidable){
        MovableGameObject mgo;
        switch(objectType){
            case "kuffo":
                mgo = new Kuffo(currentObjectID, objectType, width, height, startingPosX, startingPosY, velocityX, velocityY, collidable);
                currentObjectID++;
                break;
            default: 
                mgo = null;
                break;
        }
        return mgo;
    }
    public ArrayList<GameObject> getLevelObjects(){
        return this.levelObjects;
    }
}