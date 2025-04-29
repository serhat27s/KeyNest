package com.example.KeyNest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import com.example.KeyNest.popups.AddAccount;
import com.example.KeyNest.popups.EditAccount;
import com.example.KeyNest.popups.RemoveAccount;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class DatabaseController
{
    private Boolean showPw = false;

    @FXML
    private TableView<Account> table;

    @FXML
    private TableColumn<Account, String> nameColumn;

    @FXML
    private TableColumn<Account, String> emailColumn;

    @FXML
    private TableColumn<Account, String> passwordColumn;

    @FXML
    private Label accountCounter;

    @FXML
    private CheckBox showPasswordCheckbox;

    private String encryptedDB = System.getProperty("user.home") + System.getProperty("file.separator") + "keyholder";
    private String decryptedDB = System.getProperty("user.home") + System.getProperty("file.separator") + ".keyholder";

    @FXML
    private void initialize()
    {
        //load database
        ObservableList<Account> accounts = CsvFileReader.readCsvFile(encryptedDB);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        nameColumn.setReorderable(false);
        emailColumn.setReorderable(false);
        passwordColumn.setReorderable(false);

        passwordColumn.setVisible(false);


        table.setPlaceholder(new Label("No accounts here!"));
        table.setItems(accounts);
        nameColumn.setSortable(false);

        accountCounter.setText("Accounts saved: " + accounts.size());

        System.out.println("Database loaded");

    }

    @FXML
    private void showPasswords() throws IOException
    {
        if(showPw)
        {
            //hide password
            passwordColumn.setVisible(false);
            showPasswordCheckbox.setSelected(false);
            showPw = false;
            System.out.println("Passwords status set to hidden");
        }
        else
        {
            //show password
            passwordColumn.setVisible(true);
            showPasswordCheckbox.setSelected(true);
            showPw = true;
            System.out.println("Passwords status set to visible");
        }
    }

    private void save()
    {
        nameColumn.setSortable(true);
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(nameColumn);
        table.sort();
        nameColumn.setSortable(false);
        ObservableList<Account> accounts = table.getItems();
        CsvFileWriter.writeCsvFile(decryptedDB, accounts);


        accountCounter.setText("Accounts saved: " + accounts.size());
    }

    @FXML
    private void addAccount() throws MalformedURLException, URISyntaxException
    {
        AddAccount popup = new AddAccount();
        popup.display();

        if (AddAccount.account != null)
        {
            ObservableList<Account> accounts = table.getItems();
            accounts.add(AddAccount.account);

            System.out.println("Added account:\n" + AddAccount.account);

            AddAccount.account = null;

            save();
        }
    }

    @FXML
    private void editAccount() throws MalformedURLException
    {
        int row = table.getSelectionModel().getSelectedIndex();

        if(row != -1)
        {
            Account account = table.getSelectionModel().getSelectedItem();

            EditAccount popup = new EditAccount(account);
            popup.display();

            if(EditAccount.editRow)
            {
                table.getItems().remove(row);

                ObservableList<Account> accounts = table.getItems();
                accounts.add(EditAccount.account);

                EditAccount.editRow = false;

                System.out.println("Edit account in row " + (row+1));

                save();
            }
        }
    }

    @FXML
    private void removeAccount() throws MalformedURLException
    {
        int row = table.getSelectionModel().getSelectedIndex();

        if(row != -1)
        {
            RemoveAccount popup = new RemoveAccount();
            popup.display();

            if(RemoveAccount.removeRow)
            {
                table.getItems().remove(row);

                RemoveAccount.removeRow = false;

                System.out.println("Removed account in row " + (row+1));

                save();
            }
        }
    }

    @FXML
    private void about() throws MalformedURLException
    {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("About");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Hyperlink link = new Hyperlink("https://github.com/serhat27s");
        Label label = new Label("Find more of my projects here:" );
        VBox vb = new VBox(label, link);

        alert.getDialogPane().setContent(vb);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/example/KeyNest/popups.css").toExternalForm());
        alert.show();
    }

    @FXML
    private void locateDatabase() throws MalformedURLException
    {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("Locate Database");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Text text1 = new Text("The location of the database is:\n");
        text1.setFill(Color.WHITE);

        // Bold part
        Text text2 = new Text(encryptedDB);
        text2.setFont(Font.font("System", FontWeight.BOLD, 12));
        text2.setFill(Color.WHITE);

        // Combine both strings
        TextFlow textFlow = new TextFlow(text1, text2);

        alert.getDialogPane().setContent(textFlow);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/com/example/KeyNest/popups.css").toExternalForm());
        alert.show();
    }

}
