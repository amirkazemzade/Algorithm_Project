package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import obst.Database;
import obst.OBST;
import obst.Settings;
import obst.Word;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    Boolean makeExactOBST = true;

    public static Stage window;

    public static Settings settings;

    public static boolean isUpdating = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("layout/menu_layout.fxml"));
        Parent root = menuLoader.load();
//        MainPageController mainPageController = mainPageLoader.getController();

        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // Reading local settings from saved settings file
        settings = Database.readSetting();

        // Make tree if there isn't any tree saved
        if (settings.isByFirstLetter()) {
            File data = new File("src\\dataByLetter\\data_a0.txt");
            if (!data.isFile()) {
                Main.updateDatabaseByLetter();
            }
        } else {
            File data = new File("src\\data\\data0.txt");
            if (!data.isFile()) {
                Main.updateDatabase();
            }
        }
//
//        if (makeExactOBST) {
//            File data = new File("src\\data\\data0.txt");
//            if (!data.isFile()) {
//                updateDatabase();
//            }
//        } else {
//            File data = new File("src\\dataByLetter\\data_a0.txt");
//            if (!data.isFile()) {
//                updateDatabaseByLetter();
//            }
//        }
//
//        mainPageController.treeMode.setOnAction(actionEvent -> {
//            if (mainPageController.treeMode.getText().equals("Normal Tree")) {
//                makeExactOBST = false;
//                mainPageController.treeMode.setText("First Case Tree");
//            } else {
//                makeExactOBST = true;
//                mainPageController.treeMode.setText("Normal Tree");
//            }
//        });
//
//        mainPageController.translateButton.setOnAction(actionEvent -> {
//            long translationStartTime = System.currentTimeMillis();
//            String input = mainPageController.inputText.getText().toLowerCase();
//            String output = "";
//            try {
//                if (makeExactOBST) {
//                    output = TextTranslator.translate(input);
//                } else {
//                    output = TextTranslator.translateByLetter(input);
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            mainPageController.outputText.setText(output);
//            System.out.println("Text has been translated in " + (System.currentTimeMillis() - translationStartTime) + " milliseconds!");
//        });
//
//        mainPageController.updateDatabaseButton.setOnAction(actionEvent -> {
//            if (makeExactOBST) {
//                updateDatabase();
//            } else {
//                updateDatabaseByLetter();
//            }
//        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    static void updateDatabase() {
        System.out.println("Started updating database!");
        isUpdating = true;
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
            isUpdating = false;
        });
        tree.start();
    }

    static void updateDatabaseByLetter() {
        System.out.println("Started updating database!");
        isUpdating = true;
        Thread tree = new Thread(() -> {
            long treeStartTime = System.currentTimeMillis();
            ArrayList<ArrayList<Word>> words = new ArrayList<>();
            try {
                words = Database.getWordsByLetter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (ArrayList<Word> wordsByLetter : words) {
                try {
                    OBST obst = new OBST(wordsByLetter);
                    obst.makeTree();
                    obst.saveTree("dataByLetter", "data_" + wordsByLetter.get(0).getWord().charAt(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("OBST has been made and saved in " + (System.currentTimeMillis() - treeStartTime) + " milliseconds!");
            isUpdating = false;
        });
        tree.start();
    }
}

