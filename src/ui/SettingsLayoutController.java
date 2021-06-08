package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsLayoutController implements Initializable {
    ObservableList<String> ramUsageItems = FXCollections.observableArrayList("low", "medium", "high", "very high");

    @FXML
    private ChoiceBox<String> ram_usage;

    @FXML
    private RadioButton based_on_first_letter;

    @FXML
    private RadioButton create_one_tree;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ram_usage.setItems(ramUsageItems);
        ram_usage.setOnAction(this::getRamUsage);
    }

    public void getRamUsage(ActionEvent event) {
        String ramUsageState = ram_usage.getValue();

        switch (ramUsageState) {
            case "low": {

            }
            case "medium": {

            }
            case "high": {

            }
            case "very high": {

            }
            default: {

            }
        }
    }

    public void getTypeOfTreeCreation(ActionEvent event) {
        if (based_on_first_letter.isSelected()) {

        } else if (create_one_tree.isSelected()) {

        }
    }
}
