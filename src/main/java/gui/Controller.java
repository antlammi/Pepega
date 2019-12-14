package gui;

import javafx.scene.Scene;
import logic.Kuffo;

public class Controller {
    private Kuffo kuffo;
    private Scene scene;
    
    public Controller(Scene scene){
        this.scene = scene;
    }

    public void setKuffo(Kuffo kuffo) {
        this.kuffo = kuffo;
    }

    public void activate() {
        scene.setOnKeyPressed((e) ->{
            switch(e.getCode()){
                case W:
                    break;
                case A:
                    kuffo.decrementVelocityX();
                    break;
                case S:
                    kuffo.incrementVelocityY();
                    break;
                case D:
                    kuffo.incrementVelocityX();
                    break;
                default:
                    break;
            }
        });
        scene.setOnKeyReleased((e) -> {
            switch(e.getCode()){
                case W:
                    kuffo.jump();
                    break;
                case A:
                    break;
                case S:
                    break;
                case D:
                    break;
                default:
                    break;
            }
        });
    }


}