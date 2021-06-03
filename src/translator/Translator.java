package translator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Translator {
    ArrayList<TreeNode> startTree;
    ArrayList<TreeNode> tree;
    int nextDataIndex = 1;
    File startData;
    File currentData;


    public Translator() throws FileNotFoundException {
        readStartTree();
        tree = startTree;
        currentData = startData;
    }

    public String translate(String word) throws FileNotFoundException {
        int index = 1;
        while (true) {
            if (index >= tree.size()) {
                index -= tree.size();
                tree = readTree();
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
                    index = 2 * index;
                } else {
                    index = (2 * index) + 1;
                }
            }
        }
    }

    private ArrayList<TreeNode> readTree() throws FileNotFoundException {
        currentData = new File("src\\data\\data" + nextDataIndex + ".txt");
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
        startData = new File("src\\data\\data0.txt");
        Scanner in = new Scanner(startData);

        while (in.hasNext()) {
            String line = in.nextLine();
            String[] lineNode = line.split(" ");
            startTree.add(new TreeNode(Integer.parseInt(lineNode[0]), lineNode[1], lineNode[2]));
        }
    }

}
