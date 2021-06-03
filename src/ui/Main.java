package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import obst.Database;
import obst.OBST;

import java.io.File;
import java.io.IOException;
import java.sql.Time;


public class Main extends Application {

    OBST obst;
    Database database;
    FXMLLoader mainPageLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainPageLoader = new FXMLLoader(getClass().getResource("main_page.fxml"));
        Parent root = mainPageLoader.load();
        MainPageController mainPageController = mainPageLoader.getController();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();

        database = new Database();

        try {
            database.updateDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File data = new File("src\\data\\data1-500.txt");
        if (!data.isFile()) {
            updateDatabase();
        }

        mainPageController.translateButton.setOnAction(actionEvent -> obst.printTree());

        mainPageController.updateDatabaseButton.setOnAction(actionEvent -> updateDatabase());
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void updateDatabase(){
        Thread tree = new Thread(() -> {
            long time = System.currentTimeMillis();
            obst = new OBST(database.getWords());
            System.out.println("OBST created! " + (System.currentTimeMillis()- time));
            obst.makeTree();
            System.out.println("Tree has been made! " + (System.currentTimeMillis() - time));
            try {
                obst.saveTree();
                System.out.println("Tree has been saved! " + ( System.currentTimeMillis() - time));
            } catch (IOException e) {
                e.printStackTrace();
            }
//            obst.printTree();
//            System.out.println("Tree has printed!" + System.currentTimeMillis());
        });
        tree.start();
    }
}

