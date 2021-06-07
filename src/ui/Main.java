package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import obst.Database;
import obst.OBST;
import obst.Word;
import translator.TextTranslator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    FXMLLoader mainPageLoader;
    Boolean makeExactOBST = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainPageLoader = new FXMLLoader(getClass().getResource("main_page.fxml"));
        Parent root = mainPageLoader.load();
        MainPageController mainPageController = mainPageLoader.getController();

        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();

        if (makeExactOBST) {
            File data = new File("src\\data\\data0.txt");
            if (!data.isFile()) {
                updateDatabase();
            }
        } else {
            File data = new File("src\\dataByLetter\\data_a0.txt");
            if (!data.isFile()) {
                updateDatabaseByLetter();
            }
        }

        mainPageController.translateButton.setOnAction(actionEvent -> {
            long translationStartTime = System.currentTimeMillis();
            String input = mainPageController.inputText.getText().toLowerCase();
            String output = "";
            try {
                output = TextTranslator.translate(input);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mainPageController.outputText.setText(output);
            System.out.println("Text has been translated in " + (System.currentTimeMillis() - translationStartTime) + " milliseconds!");
        });

        mainPageController.updateDatabaseButton.setOnAction(actionEvent -> updateDatabase());
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void updateDatabase() {
        Thread tree = new Thread(() -> {
            long treeStartTime = System.currentTimeMillis();
            try {
                OBST obst = new OBST(Database.getWords());
                obst.makeTree();
                System.out.println("OBST has been made in " + (System.currentTimeMillis() - treeStartTime) + " milliseconds!");
                obst.saveTree("data", "data");
                System.out.println("OBST has been saved! ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        tree.start();
    }

    private void updateDatabaseByLetter() {
        Thread tree = new Thread(() -> {
            long treeStartTime = System.currentTimeMillis();
            ArrayList<ArrayList<Word>> words = new ArrayList<>();
            try {
                words = Database.getWordsByLetter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (ArrayList<Word> wordsByLetter: words) {
                try {
                    OBST obst = new OBST(Database.getWords());
                    obst.makeTree();
                    obst.saveTree("dataByLetter", "data_" + wordsByLetter.get(0).getWord().charAt(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("OBST has been made and saved in " + (System.currentTimeMillis() - treeStartTime) + " milliseconds!");

        });
        tree.start();
    }
}

