package translator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordTranslator {
    ArrayList<TreeNode> startTree;
    ArrayList<TreeNode> tree;
    int nextDataIndex = 1;
    File startData;
    File currentData;
    String dataPath;


    public WordTranslator() throws FileNotFoundException {
        dataPath = "src\\data\\data";
        readStartTree();
        tree = startTree;
        currentData = startData;
    }

    public WordTranslator(char firstCase) throws FileNotFoundException {
        dataPath = "src\\dataByLetter\\data_" + firstCase;
        readStartTree();
        tree = startTree;
        currentData = startData;
    }

    public String translate(String word) throws FileNotFoundException {
        int index = 1;
        int realIndex = 1;
        nextDataIndex = 1;
        tree = startTree;
        currentData = startData;
        while (true) {
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
                currentData = startData;
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
    }

    private ArrayList<TreeNode> readTree() throws FileNotFoundException {
        currentData = new File(dataPath + nextDataIndex + ".txt");
        nextDataIndex++;

        Scanner in = new Scanner(currentData);
        ArrayList<TreeNode> tree = new ArrayList<>();

        while (in.hasNext()) {
            String line = in.nextLine();
            String[] lineNode = line.split(" ");
            tree.add(new TreeNode(Integer.parseInt(lineNode[0]), lineNode[1], lineNode[2]));
        }

        return tree;
    }

    private void readStartTree() throws FileNotFoundException {
        startTree = new ArrayList<>();
        startTree.add(new TreeNode(-2, "start", "start"));
        startData = new File(dataPath + "0.txt");
        Scanner in = new Scanner(startData);

        while (in.hasNext()) {
            String line = in.nextLine();
            String[] lineNode = line.split(" ");
            startTree.add(new TreeNode(Integer.parseInt(lineNode[0]), lineNode[1], lineNode[2]));
        }
    }

}
