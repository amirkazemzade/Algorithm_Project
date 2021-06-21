package ui;

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MenuLayoutController implements Initializable {
    @FXML
    private Button enter_text_button;

    @FXML
    private Button settings_button;

    @FXML
    private Button exit_button;

    @FXML
    private VBox box;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        box.setBackground(
                new Background(
                        new BackgroundFill(
                                new LinearGradient(
                                        0, 0, 200, 200 ,  false,
                                        CycleMethod.REFLECT,
                                        new Stop(0,  Color.web("#4776E6")),
                                        new Stop(1, Color.web("#8E54E9"))
                                ),
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )
                )
        );
    }


    @FXML
    public void onEnterTextClicked() throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("layout/translation_layout.fxml"));
        Parent menuP = menuLoader.load();
        Main.window.setScene(new Scene(menuP));
        Main.window.show();
    }


    @FXML
    public void onShowTreeClicked() throws FileNotFoundException {
        //create the graph
        Digraph<String, String> g = new DigraphEdgeList<>();

        File data = new File("src\\data\\data0.txt");

//        String[] nodes = new String[16];

        ArrayList<String> nodes = new ArrayList<>();
        nodes.add(0, "");

        //-------------------------------insert_vertex----------------------------//
        Scanner in = new Scanner(data);
        int index = 1;
        while (in.hasNext() && index < 16) {
            String line = in.nextLine();
            String[] lineNode = line.split(" ");

            nodes.add(index, lineNode[1]);
            index++;
        }
        in.close();
        //-------------------------------insert_edge------------------------------//
        for (int i = 1; i < (nodes.size() - 1) / 2; i++) {
            int leftChild = (2 * i) % nodes.size();
            int rightChild = ((2 * i) + 1) % nodes.size();
            if (i == 1) {
                g.insertVertex(nodes.get(i));
            }
            g.insertVertex(nodes.get(leftChild));
            g.insertVertex(nodes.get(rightChild));
            g.insertEdge(nodes.get(i), nodes.get(leftChild), nodes.get(i) + " (LeftChild)");
            g.insertEdge(nodes.get(i), nodes.get(rightChild), nodes.get(i) + " (RightChild)");
        }
        //------------------------------------------------------------------------//
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);
        Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 1024, 768);

        graphView.getStylableVertex(nodes.get(1)).setStyleClass("myVertex2");

        graphView.setAutomaticLayout(true);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("OPTIMAL BINARY SEARCH TREE");
        stage.setScene(scene);
        stage.show();

//IMPORTANT - Called after scene is displayed so we can have width and height values
        graphView.init();
    }

    @FXML
    public void onSettingsClicked() throws IOException {
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("layout/settings_layout.fxml"));
        Parent settingsP = settingsLoader.load();
        SettingsLayoutController settingsLayoutController = settingsLoader.getController();
        Main.window.setScene(new Scene(settingsP));
        Main.window.show();

        settingsLayoutController.ram_usage.setValue(settingsLayoutController.ramUsageItems.get(Main.settings.getRamUsage()));
        if (Main.settings.isByFirstLetter()) {
            settingsLayoutController.based_on_first_letter.setSelected(true);
        } else {
            settingsLayoutController.create_one_tree.setSelected(true);
            if (Main.settings.getTreeSize() == -1) {
                settingsLayoutController.tree_size.setText("size of all words");
            } else {
                settingsLayoutController.tree_size.setText(String.valueOf(Main.settings.getTreeSize()));
            }
        }
    }

    @FXML
    public void onExitClicked() {
        System.exit(0);
    }

}
