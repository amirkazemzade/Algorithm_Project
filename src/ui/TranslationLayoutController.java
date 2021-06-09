package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import translator.TextTranslator;
import ui.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TranslationLayoutController {
    @FXML
    private TextArea input_text;

    @FXML
    private TextArea output_text;

    @FXML
    private Button translate_button;

    @FXML
    private Button back_to_menu;

    public void onTranslateClicked(ActionEvent event) {
        long translationStartTime = System.currentTimeMillis();
            String input = input_text.getText().toLowerCase();
            String output = "";
            try {
                if (Main.settings.isByFirstLetter()) {
                    output = TextTranslator.translateByLetter(input);
                } else {
                    output = TextTranslator.translate(input);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            output_text.setText(output);
            System.out.println("Text has been translated in " + (System.currentTimeMillis() - translationStartTime) + " milliseconds!");
    }

    public void onBackToMenuClicked(ActionEvent event) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("layout/menu_layout.fxml"));
        Parent menuP = menuLoader.load();
        Main.window.setScene(new Scene(menuP));
        Main.window.show();
    }

}
