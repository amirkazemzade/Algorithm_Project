package ui;

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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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
    public void onShowTreeClicked() {
        //create the graph
        Digraph<String, String> g = new DigraphEdgeList<>();

        //-------------------------MAKE_TREE------------------------------//

        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");

        g.insertEdge("A","C","1");
        g.insertEdge("A","B","2");

        //----------------------------------------------------------------//

        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);
        Scene scene = new Scene(graphView, 1024, 768);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFXGraph Visualization");
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
