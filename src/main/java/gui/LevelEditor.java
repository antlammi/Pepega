package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.GameObject;
import logic.Level;
import logic.MovableGameObject;

public class LevelEditor {
    private Level currentLevel;
    private ToolBar toolBar;
    private BorderPane editor;
    private Canvas canvas;
    private Stage stage;
    private Scene scene;
    private GraphicsContext gc;
    private VBox currentObjects;
    public LevelEditor(){
        this.stage = new Stage();

        this.canvas = new Canvas(800, 800);
        this.gc = this.canvas.getGraphicsContext2D();
        this.gc.setFill(Color.rgb(0,0,0));
        this.gc.fillRect(0,0,800,800);

        this.editor = new BorderPane();
        this.scene = new Scene(this.editor, 1100, 900, Color.rgb(0,0,0));
        this.editor.setCenter(canvas);
        
        this.toolBar = setupToolBar();
        this.editor.setTop(this.toolBar);

        this.currentObjects = new VBox();
        this.editor.setLeft(currentObjects);
        this.currentObjects.setStyle("-fx-padding: 20 0 0 0;");
        this.currentLevel = new Level(2, 800, 800);
       
        Button exportLevelToFile = new Button("Export level");
        FileChooser fileChooser = new FileChooser();
        exportLevelToFile.setOnMouseClicked((e) -> {
            File selectedFile = fileChooser.showSaveDialog(stage);
            if (selectedFile != null){
                try {
                    FileWriter fileWriter = new FileWriter(selectedFile);
                    fileWriter.write(currentLevel.exportLevelToString());
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
            
        });
        this.editor.setBottom(exportLevelToFile);
        stage.setScene(scene);
        stage.setTitle("Level Editor");
        stage.show();
    }

    public ToolBar setupToolBar(){
        Button kuffoBtn = new Button("Kuffo");
        kuffoBtn.setOnMouseClicked((e) -> {
            this.editor.setRight(setupMovableObjectEditor("kuffo", 30, 30, 50, 500, 0.0, 0.0));            
        });
        Button floorBtn = new Button("Floor");
        floorBtn.setOnMouseClicked((e) -> {
            this.editor.setRight(setupObjectEditor("floor", 800, 10, 0, 790));
        });

        Button wallBtn = new Button("Wall");
        wallBtn.setOnMouseClicked((e) -> {
            this.editor.setRight(setupObjectEditor("wall", 30, 800, 0, 0));
        });

        Button spikesBtn = new Button("Spikes");
        spikesBtn.setOnMouseClicked((e) -> {
            this.editor.setRight(setupObjectEditor("spikes", 50, 50, 340, 740));
        });

        return new ToolBar(kuffoBtn, floorBtn, wallBtn, spikesBtn);
    }
    private void confirmMovableObject(String objectType, String width, String height, 
    String posX, String posY, String velX, String velY, Label errorLabel) {
        try {
            int widthValue = Integer.parseInt(width);
            int heightValue = Integer.parseInt(height);
            int posXValue = Integer.parseInt(posX);
            int posYValue = Integer.parseInt(posY);
            double velXValue = Double.parseDouble(velX);
            double velYValue = Double.parseDouble(velY);

            this.currentLevel.getLevelObjects().add(this.currentLevel.buildMovableGameObject(objectType, widthValue, heightValue, posXValue, posYValue, velXValue, velYValue));
            errorLabel.setText("");
            drawObjects();
        } catch (Exception ex){
            errorLabel.setText("Invalid input format");
            ex.printStackTrace();
        }
    }
    private void confirmObject(String objectType, String width, String height, 
    String posX, String posY, Label errorLabel) {
        try {
            int widthValue = Integer.parseInt(width);
            int heightValue = Integer.parseInt(height);
            int posXValue = Integer.parseInt(posX);
            int posYValue = Integer.parseInt(posY);
            

            this.currentLevel.getLevelObjects().add(this.currentLevel.buildGameObject(objectType, widthValue, heightValue, posXValue, posYValue));
            errorLabel.setText("");
            drawObjects();
        } catch (Exception ex){
            errorLabel.setText("Invalid input format");
            ex.printStackTrace();
        }
    }
    private void drawObjects(){
        this.canvas = new Canvas(800, 800);
        this.gc = this.canvas.getGraphicsContext2D();
        this.editor.setCenter(this.canvas);
        this.gc.setFill(Color.rgb(0,0,0));
        this.gc.fillRect(0,0,800,800);
        this.currentObjects.getChildren().clear();
        for (GameObject go : currentLevel.getLevelObjects()){
            if (go == null){
                continue;
            }
            HBox hbox = new HBox();
            Button goBtn = new Button(go.getObjectType());
            goBtn.setStyle("-fx-pref-width: 5em");
            goBtn.setOnMouseClicked((e) -> {
                String objectType = go.getObjectType();
                int width = go.getWidth();
                int height = go.getHeight();
                int posX = go.getPosX();
                int posY = go.getPosY();

                if (go instanceof MovableGameObject){
                    MovableGameObject mgo = (MovableGameObject) go;
                    this.editor.setRight(setupMovableObjectEditor(objectType, width, height, posX, posY, mgo.getVelocityX(), mgo.getVelocityY()));
                } else {
                    this.editor.setRight(setupObjectEditor(objectType, width, height, posX, posY));
                }
            });

            Button delBtn = new Button("delete");
            delBtn.setStyle("-fx-font-color: red;");
            delBtn.setOnMouseClicked((e) -> {
                this.currentLevel.getLevelObjects().remove(go);
                drawObjects();
            });
            hbox.getChildren().addAll(goBtn, delBtn);
            this.currentObjects.getChildren().add(hbox);
        }
        for (GameObject go : currentLevel.getLevelObjects()){
            if (go == null){
                continue;
            }
            this.gc.setFill(go.getPaint());
            this.gc.fillRect(go.getPosX(), go.getPosY(), go.getWidth(), go.getHeight());
        }
    }

    public VBox setupMovableObjectEditor(String objectType, Integer defWidth, Integer defHeight, Integer defPosX, Integer defPosY, Double defVelX, Double defVelY){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        Label label = new Label(objectType);
        TextField width = new TextField("" + defWidth);
        TextField height = new TextField("" + defHeight);
        TextField posX = new TextField("" + defPosX);
        TextField posY = new TextField("" + defPosY);
        TextField velX = new TextField("" + defVelX);
        TextField velY = new TextField("" + defVelY);
        Label errorLabel = new Label("");
        Button confirmObject = new Button("Confirm");

        confirmObject.setOnMouseClicked((c) -> {
            confirmMovableObject(objectType, width.getText(), height.getText(), posX.getText(), posY.getText(), velX.getText(), velY.getText(), errorLabel);
        });
        
        vbox.getChildren().addAll(label, width, height, posX, posY, velX, velY, errorLabel, confirmObject);
        return vbox;
    }

    public VBox setupObjectEditor(String objectType, Integer defWidth, Integer defHeight, Integer defPosX, Integer defPosY){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        Label label = new Label(objectType);
        TextField width = new TextField("" + defWidth);
        TextField height = new TextField("" + defHeight);
        TextField posX = new TextField("" + defPosX);
        TextField posY = new TextField("" + defPosY);
        Label errorLabel = new Label("");
        Button confirmObject = new Button("Confirm");

        confirmObject.setOnMouseClicked((c) -> {
            confirmObject(objectType, width.getText(), height.getText(), posX.getText(), posY.getText(), errorLabel);
        });
        
        vbox.getChildren().addAll(label, width, height, posX, posY, errorLabel, confirmObject);
        return vbox;
    }

    public BorderPane getView(){
        return this.editor;
    }
}