package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import logic.GameObject;
import logic.Level;
import logic.Kuffo;
import logic.PhysicsHandler;
import logic.Quadruplet;

public class GameView {
    private Level level;
    private int levelKey;
    private Canvas canvas;
    private GraphicsContext gc;
    private Kuffo kuffo;
    private PhysicsHandler physics;
    private Group root;
    private final long[] previousUpdate = new long[1];
    public GameView(int level, Group root) {
        this.root = root;
        this.canvas = new Canvas(800, 800);
        this.gc = canvas.getGraphicsContext2D();
        this.levelKey = level;
        createLevel(levelKey);
        this.physics = new PhysicsHandler(0.25, this.kuffo);
        gameLoop();
    }

    private void createLevel(int levelKey){
        this.level = new Level(levelKey, 800, 800);
        System.out.println(this.level.exportLevelToString());
        ArrayList<GameObject> toDraw = this.level.getLevelObjects();
        
        if (this.kuffo == null) {
            this.kuffo = (Kuffo) toDraw.get(0);
        } else {
            
            this.kuffo.setPosX(toDraw.get(0).getPosX());
            this.kuffo.setPosY(toDraw.get(0).getPosY());
            this.kuffo.setAlive(true);
            this.kuffo.enableJump();
            toDraw.set(0, this.kuffo);
            kuffo.addToTrail(new Quadruplet<>(kuffo.getPosX(), kuffo.getPosY(), (kuffo.getVelocityX() >= 0), kuffo.getCurrentRotation()));
        }
        for (GameObject go : toDraw){
            drawObject(go);
        }
           
    }
    private void updateLevel(){ 
        this.root.getChildren().remove(this.canvas);
        this.canvas = new Canvas(800, 800);
        this.gc = canvas.getGraphicsContext2D();
        this.root.getChildren().add(this.canvas);
        if (kuffo.getTrailSize() > 50) {
            Quadruplet<Integer, Integer, Boolean, Double> trailEntry = kuffo.pollTrail();
            updateTrail(trailEntry.x, trailEntry.y, trailEntry.dir, trailEntry.rotation);
        }
        ArrayList<GameObject> toDraw = this.level.getLevelObjects();
        for (GameObject go : toDraw){
            drawObject(go);
        }        
    }
    public Canvas getCanvas() {
        return this.canvas;
    }

    public Kuffo getKuffo() {
        return this.kuffo;
    }

    public void gameLoop() {
        previousUpdate[0] = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
			public void handle(long currentTime) {
                long deltaTime = TimeUnit.MILLISECONDS.convert((currentTime - previousUpdate[0]), TimeUnit.NANOSECONDS);
                if (deltaTime > 50) {
                    physics.calculateLocation(kuffo, level.getLevelObjects());
                    kuffo.addToTrail(new Quadruplet<>(kuffo.getPosX(), kuffo.getPosY(), (kuffo.getVelocityX() >= 0), kuffo.getCurrentRotation()));

                    if (!kuffo.isAlive()){
                        createLevel(levelKey);
                    } else {
                        updateLevel();
                    }
                    
                }
            }
        };
        timer.start();
    }

    public void drawObject(GameObject go) {
        gc.setFill(go.getPaint());
        gc.fillRect(go.getPosX(), go.getPosY(), go.getWidth(), go.getHeight());
    }

   
    public void updateTrail(int x, int y, Boolean dir, Double rotation) {

        ImageView iv = (dir) ? new ImageView(new Image("kuffo_shadow.png")) : new ImageView(new Image("kuffo_shadow_reversed.png"));

        iv.setRotate(rotation);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image img = iv.snapshot(params, null);

        Paint fillImage = new ImagePattern(img);
        
        gc.setFill(fillImage);
        gc.fillRect(x, y, kuffo.getWidth(), kuffo.getHeight());       
    }
}