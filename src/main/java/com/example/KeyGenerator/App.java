package com.example.KeyGenerator;

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
    public void start(Stage stage) throws IOException, InterruptedException
    {
        stage.setTitle("Password Generator");
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        App.stage = stage;
        scene = new Scene(loadFXML("PasswordGenerator"));
        stage.setResizable(false);
        stage.setScene(scene);
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