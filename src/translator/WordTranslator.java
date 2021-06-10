package translator;

import ui.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordTranslator {
    ArrayList<TreeNode> startTree;
    ArrayList<TreeNode> tree;
    int nextDataIndex = 0;
    String dataPath;
    boolean isStartTree = true;
    boolean hasAnyDataRemained = true;


    public WordTranslator() throws FileNotFoundException {
        dataPath = "src\\data\\data";
        tree = startTree = readTree();
    }

    public WordTranslator(char firstCase) throws FileNotFoundException {
        dataPath = "src\\dataByLetter\\data_" + firstCase;
        tree = startTree = readTree();
    }

    public String translate(String word) throws FileNotFoundException {
        int index = 1;
        int realIndex = 1;
        nextDataIndex = Main.settings.getRamUsage() + 1;
        tree = startTree;
        hasAnyDataRemained = true;
        while (hasAnyDataRemained) {
            if (index >= tree.size()) {
                index -= tree.size();
                try {
                    tree = readTree();
                } catch (Exception e) {
                    return "?";
                }
                continue;
            }
            TreeNode node = tree.get(index);
            if (node.getKey() == -1) {
                tree = startTree;
                return "?";
            } else {
                int compare = word.compareTo(node.getWord());
                if (compare == 0) {
                    return node.getTranslation();
                } else if (compare < 0) {
                    index += realIndex;
                    realIndex = 2 * realIndex;
                } else {
                    index += realIndex + 1;
                    realIndex = (2 * realIndex) + 1;
                }
            }
        }
        return "?";
    }

    private ArrayList<TreeNode> readTree() throws FileNotFoundException {
        ArrayList<TreeNode> readTree = new ArrayList<>();
        if (isStartTree) {
            readTree.add(new TreeNode(-2, "start", "start"));
            isStartTree = false;
        }

        for (int i = 0; i <= Main.settings.getRamUsage(); i++) {
            File data = new File(dataPath + nextDataIndex + ".txt");
            nextDataIndex++;

            if (!data.exists()) {
                hasAnyDataRemained = false;
                break;
            }

            Scanner in = new Scanner(data);

            while (in.hasNext()) {
                String line = in.nextLine();
                String[] lineNode = line.split(" ");
                readTree.add(new TreeNode(Integer.parseInt(lineNode[0]), lineNode[1], lineNode[2]));
            }
            in.close();
        }
        return readTree;
    }

}
