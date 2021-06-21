package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import translator.TextTranslator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TranslationLayoutController implements Initializable {
    @FXML
    private TextArea input_text;

    @FXML
    private TextArea output_text;

    @FXML
    private Button translate_button;

    @FXML
    private Button back_to_menu;

    @FXML
    private VBox box;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
