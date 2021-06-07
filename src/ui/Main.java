package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import obst.Database;
import obst.OBST;
import translator.TextTranslator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    OBST obst;
    Database database;
    FXMLLoader mainPageLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainPageLoader = new FXMLLoader(getClass().getResource("main_page.fxml"));
        Parent root = mainPageLoader.load();
        MainPageController mainPageController = mainPageLoader.getController();

        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();

        database = new Database();


        File data = new File("src\\data\\data0.txt");
        if (!data.isFile()) {
            updateDatabase();
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
        long databaseStartTime = System.currentTimeMillis();
        try {
            database.updateDatabase();
            System.out.println("Database has updated in " + (System.currentTimeMillis() - databaseStartTime) + " milliseconds!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread tree = new Thread(() -> {
            long treeStartTime = System.currentTimeMillis();
            obst = new OBST(database.getWords());
            obst.makeTree();
            System.out.println("OBST has been made in " + (System.currentTimeMillis() - treeStartTime) + " milliseconds!");
            try {
                obst.saveTree();
                System.out.println("OBST has been saved! ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        tree.start();
    }
}

