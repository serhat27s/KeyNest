package com.example.KeyGenerator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Duration;

import java.security.SecureRandom;

public class GeneratorController {

    private String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String lowercase = "abcdefghijklmnopqrstuvwxyz";
    private String numbers = "0123456789";
    private String specialCharacters = "!@#$%&*()-_=+[]{}|;:,.<>?/";
    private String generatedPassword = "";

    private Timeline timeline; // animation

    @FXML
    private CheckBox checkboxLowercase;

    @FXML
    private CheckBox checkboxNumbers;

    @FXML
    private CheckBox checkboxSpecialCharacters;

    @FXML
    private CheckBox checkboxUppercase;

    @FXML
    private Label labelPassword;

    @FXML
    private Label labelStrength;

    @FXML
    private TextField textfield;

    @FXML
    private Tooltip toolStrength;

    @FXML
    void buttonCopy(ActionEvent event) {
        if (!generatedPassword.isEmpty()) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(generatedPassword);
            clipboard.setContent(content);
            showPopup("Password copied to clipboard!", textfield);
        } else {
            showPopup("Please generate a password first!", textfield);
        }
    }
            private void showPopup(String message, TextField textField) {
                Tooltip popup = new Tooltip(message);
                popup.setOpacity(0.87);

                popup.setAutoHide(true); // Automatically hide after a few seconds
                double x = textField.getScene().getWindow().getX() + textField.getLayoutX() + textField.getWidth() / 2 + 86.5;
                double y = textField.getScene().getWindow().getY() + textField.getLayoutY() + textField.getHeight() / 2 + 175;
                popup.show(textField, x, y);

                javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2)); // Show a short duration
                delay.setOnFinished(e -> popup.hide());
                delay.play(); }

    @FXML
    void buttonGenerate(ActionEvent event) {
        String passwordLength = textfield.getText();
            try{
                int passwordLengthInt = Integer.parseInt(passwordLength);
                 if(passwordLengthInt < 4) {
                  labelPassword.setText("Password length has to be at least 4 characters long!");
                  labelStrength.setText("");
                  return;
                 }   else if (passwordLengthInt > 99) {
                      labelPassword.setText("Password length can only be 99 characters long!");
                      labelStrength.setText("");
                      return;
                }
                String characterSet = generateCharacterSet(
                        checkboxUppercase.isSelected(),
                        checkboxLowercase.isSelected(),
                        checkboxNumbers.isSelected(),
                        checkboxSpecialCharacters.isSelected()
                );

                if(characterSet.isEmpty())
                {
                    labelPassword.setText("Select at least one option!");
                    labelStrength.setText("");
                    return;
                }

                 generatedPassword = generateRandomPassword(passwordLengthInt, characterSet);
                 updatePasswordStrength(passwordLengthInt);

            }catch (NumberFormatException e){
                System.out.println(e);
            };
    }

    private String generateCharacterSet(boolean isUppercase, boolean isLowercase, boolean isNumbers, boolean isSpecialCharacters) {
        StringBuilder characterSet = new StringBuilder();
        if (isUppercase) {
            characterSet.append(uppercase);
        }
        //---
        if (isLowercase) {
            characterSet.append(lowercase);
        }
        //---
        if (isNumbers) {
            characterSet.append(numbers);
        }
        //---
        if (isSpecialCharacters) {
            characterSet.append(specialCharacters);
        }
        //---
        return characterSet.toString();
    }


    private void generatePassword(int passwordLength, String characterSet){
        generatedPassword = generateRandomPassword(passwordLength, characterSet);
    }

        private String generateRandomPassword(int passwordLength, String characterSet) {
            SecureRandom random = new SecureRandom();
            StringBuilder password = new StringBuilder();

            labelPassword.setText("");

            if(timeline != null){
                timeline.stop();
            }
            //animation
            timeline = new Timeline();
            for (int i = 0; i < passwordLength; i++) {
                password.append(characterSet.charAt(random.nextInt(characterSet.length())));

                int index = i;
                KeyFrame keyFrame = new KeyFrame(Duration.millis(20 * i), event -> {
                    labelPassword.setText(labelPassword.getText() + generatedPassword.charAt(index));
                });

                timeline.getKeyFrames().add(keyFrame);
            }
            timeline.play();
            //---
            return password.toString(); }

    private void updatePasswordStrength(int passwordLength) {
        String strength;
        if (passwordLength > 15) {
            strength = "Strong";
        } else if (passwordLength >= 8) {
            strength = "Medium";
        } else {
            strength = "Weak";
        }
        labelStrength.setText(strength + " Password");
    }

    public void buttonKeyManager(ActionEvent event) {

    }


}
