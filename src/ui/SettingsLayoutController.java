package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import obst.Database;
import obst.Settings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsLayoutController implements Initializable {

    ObservableList<String> ramUsageItems = FXCollections.observableArrayList("low", "medium", "high", "very high");

    @FXML
    Button back_to_menu;

    @FXML
    ChoiceBox<String> ram_usage;

    @FXML
    RadioButton based_on_first_letter;

    @FXML
    RadioButton create_one_tree;

    @FXML
    Button save_button;

    @FXML
    Button update_database_button;

    @FXML
    ToggleGroup tree_creation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ram_usage.setItems(ramUsageItems);
    }

    public int getRamUsage() {
        String ramUsageState = ram_usage.getValue();

        switch (ramUsageState) {
            case "medium":
                return 1;

            case "high":
                return 2;

            case "very high":
                return 3;

            case "low":
            default:
                return 0;
        }
    }

    public boolean isByFirstLetter() {
        if (based_on_first_letter.isSelected()) {
            return true;
        } else if (create_one_tree.isSelected()) {
            return false;
        }
        return false;
    }

    public void onBackToMenuClicked(ActionEvent event) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("layout/menu_layout.fxml"));
        Parent menuP = menuLoader.load();
        Main.window.setScene(new Scene(menuP));
        Main.window.show();
    }

    public void onSaveClicked(ActionEvent event) throws IOException {
        Settings settings = new Settings(getRamUsage(), isByFirstLetter());
        Database.writeSettings(settings);
        Main.settings = settings;
        onBackToMenuClicked(event);
    }

    public void onUpdateDatabaseClicked(ActionEvent event) throws IOException {
        if (Main.isUpdating) {
            System.out.println("Database is updating!");
            return;
        }
        if (isByFirstLetter()) {
            Main.updateDatabaseByLetter();
        }
        else
            Main.updateDatabase();
    }
}
