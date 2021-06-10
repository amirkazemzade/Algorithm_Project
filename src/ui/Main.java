package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Database;
import model.OBST;
import model.Settings;
import model.Word;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    public static Stage window;

    public static Settings settings;

    public static boolean isUpdating = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("layout/menu_layout.fxml"));
        Parent root = menuLoader.load();

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

