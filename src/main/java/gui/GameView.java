package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.ArrayDeque;
import javafx.util.Pair;

import logic.GameObject;
import logic.Level;
import logic.Kuffo;
import logic.PhysicsHandler;

public class GameView {
    private Level level;
    private Canvas canvas;
    private GraphicsContext gc;
    private Kuffo kuffo;
    private ArrayDeque<Pair<Integer, Integer>> trail;
    private ArrayDeque<Boolean> trail_direction;
    private PhysicsHandler physics;
    private final long[] previousUpdate = new long[1];
    public GameView(int level) {
        this.canvas = new Canvas(800, 800);
        this.gc = canvas.getGraphicsContext2D();
        trail_direction = new ArrayDeque<>();
        trail = new ArrayDeque<>();
        drawLevel(level);
        this.physics = new PhysicsHandler(0.25);
        gameLoop();
    }

    private void drawLevel(int level){
        this.level = new Level(level, 800, 800);
        ArrayList<GameObject> toDraw = this.level.getLevelObjects();
        for (GameObject go : toDraw){
           drawObject(go);
        }
        
        this.kuffo = (Kuffo) toDraw.get(0);
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
                    if (trail.size() > 30) {
                        Boolean dir = trail_direction.poll();
                        updateTrail(trail.poll(), dir);
                    }
                    physics.calculateLocation(kuffo, level.getLevelObjects());
                    drawObject(kuffo);
                    trail.add(new Pair<Integer, Integer>(kuffo.getPosX(), kuffo.getPosY()));
                    trail_direction.add(kuffo.getVelocityX() >= 0);
                }
            }
        };
        timer.start();
    }

    public void drawObject(GameObject go) {
        gc.setFill(go.getPaint());
        gc.fillRect(go.getPosX(), go.getPosY(), go.getWidth(), go.getHeight());
    }

   
    public void updateTrail(Pair<Integer, Integer> location, Boolean dir) {
        Image img = (dir) ? new Image("kuffo_removed.png") : new Image("kuffo_reversed_removed.png");
        ImagePattern trail_end = new ImagePattern(img);
        gc.setFill(trail_end);
        gc.fillRect(location.getKey(), location.getValue(), kuffo.getWidth(), kuffo.getHeight());
    }
}