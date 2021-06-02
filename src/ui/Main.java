package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import obst.Database;
import obst.OBST;

public class Main extends Application {

    FXMLLoader mainPageLoader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainPageLoader = new FXMLLoader(getClass().getResource("main_page.fxml"));
        Parent root = mainPageLoader.load();
        MainPageController mainPageController = mainPageLoader.getController();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
        Database database = new Database();
        try {
            database.updateDatabase();
        } catch (Exception e){
            e.printStackTrace();
        }
        OBST obst = new OBST(database.getWords());
        obst.makeTree();
        obst.printTree();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
