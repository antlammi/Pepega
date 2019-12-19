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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.GameObject;
import logic.Level;

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
        this.currentLevel = new Level(1, 800, 800);
        for (GameObject go : currentLevel.getLevelObjects()){
            Button goBtn = new Button(go.getObjectType());
            goBtn.setStyle("-fx-pref-width: 5em");
            this.currentObjects.getChildren().add(goBtn);
        }
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
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(20));
            Label label = new Label("Kuffo");
            TextField width = new TextField("30");
            TextField height = new TextField("30");
            TextField posX = new TextField("50");
            TextField posY = new TextField("500");
            TextField velX = new TextField("0.0");
            TextField velY = new TextField("0.0");
            
            Button confirmObject = new Button("Confirm");
            vbox.getChildren().addAll(label, width, height, posX, posY, velX, velY, confirmObject);
            this.editor.setRight(vbox);
            
        });
        Button floorBtn = new Button("Floor");
        floorBtn.setOnMouseClicked((e) -> {
            VBox vbox = new VBox(); 
            vbox.setPadding(new Insets(20));
            Label label = new Label("Floor");
            TextField width = new TextField("800");
            TextField height = new TextField("10");
            TextField posX = new TextField("0");
            TextField posY = new TextField("790");
            
            Button confirmObject = new Button("Confirm");
            vbox.getChildren().addAll(label, width, height, posX, posY, confirmObject);
            this.editor.setRight(vbox);
        });
        Button wallBtn = new Button("Wall");
        wallBtn.setOnMouseClicked((e) -> {
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(20));
            Label label = new Label("Wall");
            TextField width = new TextField("30");
            TextField height = new TextField("800");
            TextField posX = new TextField("0");
            TextField posY = new TextField("0");
            
            Button confirmObject = new Button("Confirm");
            vbox.getChildren().addAll(label, width, height, posX, posY, confirmObject);
            this.editor.setRight(vbox);
        });
        Button spikesBtn = new Button("Spikes");
        spikesBtn.setOnMouseClicked((e) -> {
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(20));
            Label label = new Label("Spikes");
            TextField width = new TextField("50");
            TextField height = new TextField("50");
            TextField posX = new TextField("340");
            TextField posY = new TextField("740");
            
            Button confirmObject = new Button("Confirm");
            vbox.getChildren().addAll(label, width, height, posX, posY, confirmObject);
            this.editor.setRight(vbox);

        });
        return new ToolBar(kuffoBtn, floorBtn, wallBtn, spikesBtn);
    }
    public BorderPane getView(){
        return this.editor;
    }
}