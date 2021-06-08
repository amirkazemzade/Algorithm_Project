package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class TranslationLayoutController {
    @FXML
    private TextArea input_text;

    @FXML
    private TextArea output_text;

    @FXML
    private Button translate_button;

    @FXML
    private Button update_database_button;

    @FXML
    private Button tree_mode_button;

    @FXML
    private Button back_to_menu;

    public void onTranslateClicked(ActionEvent event){

    }

    public void onUpdateDatabaseClicked(ActionEvent event){

    }

    public void onTreeModeClicked(ActionEvent event){

    }

    public void onBackToMenuClicked(ActionEvent event) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("menu_layout.fxml"));
        Parent menuP = menuLoader.load();
        Main.window.setScene(new Scene(menuP));
        Main.window.show();
    }

}
