package com.example.KeyNest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image; // icon


import java.io.IOException;

public class App extends Application
{
    public static Scene scene;
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException
    {
        stage.setTitle("KeyNest ");
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        App.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUImain.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        GeneratorController controller = loader.getController();
        controller.switchKeyGenerator(null); //makes KeyGenerator the "home" screen
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException
    {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args)
    {
        launch();
    }
}