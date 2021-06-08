package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuLayoutController {
    @FXML
    private Button enter_text_button;

    @FXML
    private Button settings_button;

    @FXML
    private Button exit_button;

    @FXML
    public void onEnterTextClicked() throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("translation_layout.fxml"));
        Parent menuP = menuLoader.load();
        Main.window.setScene(new Scene(menuP));
        Main.window.show();
    }

    @FXML
    public void onSettingsClicked() throws IOException{
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("settings_layout.fxml"));
        Parent settingsP = settingsLoader.load();
        Main.window.setScene(new Scene(settingsP));
        Main.window.show();
    }

    @FXML
    public void onExitClicked(){
        System.exit(0);
    }

}
