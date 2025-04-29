package com.example.KeyNest;

import java.io.File;
import java.io.IOException;

import com.example.KeyNest.popups.EnterNewPassword;
import com.example.KeyNest.popups.EnterPassword;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


public class LoginController
{
    private Scene scene;

    private String encryptedDB = System.getProperty("user.home") + System.getProperty("file.separator") + "keyholder";
    private String decryptedDB = System.getProperty("user.home") + System.getProperty("file.separator") + ".keyholder";

    @FXML
    private CheckMenuItem showPasswords;

    @FXML
    private Label isDatabaseCreated;

    @FXML
    private Button save;

    @FXML
    private Button focus;

    @FXML
    private void createDatabase() throws IOException
    {
        File f = new File(encryptedDB);
        if(f.exists() && !f.isDirectory())
        {   //database found
            System.out.println("Database found!");

            Alert alert = new Alert(AlertType.NONE);
            alert.setTitle("Database found");
            alert.setContentText("An existing database has been found.\nYou can't create another one!");
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/example/KeyNest/popups.css").toExternalForm());
            alert.showAndWait();
        }
        else
        {   //create new empty database
            EnterNewPassword popup = new EnterNewPassword();
            popup.display();

            if(EnterNewPassword.isPasswordIn)
            {
                CsvFileWriter.writeCsvFile(decryptedDB, null);
                isDatabaseCreated.setText("Database created in\n" + encryptedDB);
            }
        }
    }
    @FXML
    private void loadDatabase() throws IOException
    {
        try
        {
            File f = new File(encryptedDB);
            if(f.exists() && !f.isDirectory())
            {
                EnterPassword popup = new EnterPassword();
                popup.display();

                if(EnterPassword.isPasswordIn)

                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("GUImain.fxml"));
                    Parent root = loader.load();
                    GeneratorController controller = loader.getController();
                    controller.switchKeyManager(null);

                    Scene scene = new Scene(root);
                    App.stage.setScene(scene);
                    App.stage.centerOnScreen();
                }
            }
            else
            {
                //database not found
                System.out.println("No database found!");

                Alert alert = new Alert(AlertType.NONE);
                alert.setTitle("No database found!");
                alert.setContentText("No existing database has been found.\nCreate one now!");
                alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/example/KeyNest/popups.css").toExternalForm());
                alert.showAndWait();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            isDatabaseCreated.setText("The password is incorrect.\nTry again!");
        }
    }
}