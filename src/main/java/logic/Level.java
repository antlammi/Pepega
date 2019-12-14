package logic;
import java.util.ArrayList;
public class Level{
    private ArrayList<GameObject> levelObjects;
    private int width; 
    private int height;
    public Level(int levelCode, int width, int height){
        this.width = width;
        this.height = height;
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
        MovableGameObject go1 = new Kuffo(30, 30, 50, 760, 0.0, 0.0, true);
        GameObject go2 = new Wall(30, 300, 500, 500, true);
        GameObject go3 = new Wall(30, 300, 400, 400, true);
        GameObject go4 = new Floor(this.width, 10, 0, 790, true);
        this.levelObjects.add(go1);
        this.levelObjects.add(go2);
        this.levelObjects.add(go3);
        this.levelObjects.add(go4);
    }

    public ArrayList<GameObject> getLevelObjects(){
        return this.levelObjects;
    }
}