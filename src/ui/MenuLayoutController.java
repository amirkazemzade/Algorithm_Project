package ui;

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import translator.TreeNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuLayoutController {
    @FXML
    private Button enter_text_button;

    @FXML
    private Button settings_button;

    @FXML
    private Button exit_button;

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

        String[] nodes = new String[16];

        //-------------------------------insert_vertex----------------------------//
        Scanner in = new Scanner(data);
        int index = 1;
        while (in.hasNext() && index < 16) {
            String line = in.nextLine();
            String[] lineNode = line.split(" ");

            nodes[index] = lineNode[1];
            index++;
        }
        in.close();
        //-------------------------------insert_edge------------------------------//
        for (int i = 1; i < (nodes.length - 1) / 2; i++) {
            int leftChild = ((2 * i) + 1) % 16;
            int rightChild = (2 * i) % 16;
            if (i == 1) {
                g.insertVertex(nodes[i]);
            }
            g.insertVertex(nodes[leftChild]);
            g.insertVertex(nodes[rightChild]);
            g.insertEdge(nodes[i], nodes[leftChild], nodes[i] + " (LeftChild)");
            g.insertEdge(nodes[i], nodes[rightChild], nodes[i] + " (RightChild)");
        }
        //------------------------------------------------------------------------//
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);
        Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 1024, 768);

        graphView.getStylableVertex(nodes[1]).setStyleClass("myVertex2");

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
        }

    }

    @FXML
    public void onExitClicked() {
        System.exit(0);
    }

}
