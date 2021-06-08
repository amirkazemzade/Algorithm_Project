package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuLayoutController {
    @FXML
    private Button enter_text_button;

    @FXML
    private Button settings_button;

    @FXML
    private Button exit_button;

    @FXML
    public void onEnterTextClicked(){

    }

    @FXML
    public void onSettingsClicked(){

    }

    @FXML
    public void onExitClicked(){
        System.exit(0);
    }

}
