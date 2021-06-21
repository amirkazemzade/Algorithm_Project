package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import model.Database;
import model.Settings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsLayoutController implements Initializable {

    ObservableList<String> ramUsageItems = FXCollections.observableArrayList("low", "medium", "high", "very high");

    int initTreeSize;

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

    @FXML
    TextField tree_size;

    @FXML
    VBox box;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ram_usage.setItems(ramUsageItems);
        tree_size.setDisable(Main.settings.isByFirstLetter());
        initTreeSize = Main.settings.getTreeSize();
        box.setBackground(
                new Background(
                        new BackgroundFill(
                                new LinearGradient(
                                        0, 0, 200, 200 ,  false,
                                        CycleMethod.REFLECT,
                                        new Stop(0,  Color.web("#8E54E9")),
                                        new Stop(1, Color.web("#4776E6"))
                                ),
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )
                )
        );
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
        tree_size.setDisable(!create_one_tree.isSelected());
        if (based_on_first_letter.isSelected()) {
            return true;
        } else if (create_one_tree.isSelected()) {
            return false;
        }
        return false;
    }

    public int getTreeSize() {
        int size = -1;
        if (create_one_tree.isSelected()) {
            if (tree_size.getText().matches("\\d*")) {
                size = Integer.parseInt(tree_size.getText());
            }
        }
        return size;
    }

    public void onBackToMenuClicked(ActionEvent event) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("layout/menu_layout.fxml"));
        Parent menuP = menuLoader.load();
        Main.window.setScene(new Scene(menuP));
        Main.window.show();
    }

    public void onSaveClicked(ActionEvent event) throws IOException {
        if (initTreeSize != Main.settings.getTreeSize()) {
            onUpdateDatabaseClicked(event);
        } else {
            save();
        }
    }

    public void onUpdateDatabaseClicked(ActionEvent event) throws IOException {
        save();
        if (Main.isUpdating) {
            System.out.println("Database is updating!");
            return;
        }
        if (isByFirstLetter()) {
            Main.updateDatabaseByLetter();
        } else
            Main.updateDatabase();
    }

    private void save() throws IOException {
        Settings settings = new Settings(getRamUsage(), isByFirstLetter(), getTreeSize());
        Database.writeSettings(settings);
        Main.settings = settings;
    }
}
