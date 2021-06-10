package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class OBST {
    private final ArrayList<Word> words;
    private final int n;
    private final double q;

    private Map<Integer, Map<Integer, Double>> elements;
    private Map<Integer, Map<Integer, Double>> weight;
    private Map<Integer, Map<Integer, Integer>> root;

    public OBST(ArrayList<Word> words) {
        this.words = words;
        n = words.size();
        int m = n + 1;
        double sumOfP = 0;
        for (int i = 0; i < n; i++) {
            sumOfP += words.get(i).getProvability();
        }
        q = (1 - sumOfP) / m;
    }

    public void makeTree() {
        elements = new HashMap<>();
        weight = new HashMap<>();
        root = new HashMap<>();

        for (int i = 1; i <= n + 1; i++) {
            elements.put(i, new HashMap<>());
            weight.put(i, new HashMap<>());
            root.put(i, new HashMap<>());
        }

        for (int i = 1; i <= n + 1; i++) {
            elements.get(i).put(i - 1, q);
            weight.get(i).put(i - 1, q);
        }

        for (int l = 1; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                weight.get(i).put(j, weight.get(i).get(j - 1) + words.get(j - 1).getProvability() + q);
                for (int r = i; r <= j; r++) {
                    if (i == r) {
                        elements.get(i).put(j, elements.get(i).get(r - 1) + elements.get(r + 1).get(j) + weight.get(i).get(j));
                        root.get(i).put(j, r);
                    } else {
                        double temp = elements.get(i).get(r - 1) + elements.get(r + 1).get(j) + weight.get(i).get(j);
                        if (temp < elements.get(i).get(j)) {
                            elements.get(i).put(j, temp);
                            root.get(i).put(j, r);
                        }
                    }
                }
            }
            System.out.println("round " + l + " finished!");
        }
        System.out.println("Tree has been made letter: " + words.get(0).getWord().charAt(0));
    }

    public void saveTree(String dataFolderName, String dataFileName) throws IOException {
        Queue<Integer> nodesQueue = new LinkedList<>();
        Queue<Integer> aQueue = new LinkedList<>();
        Queue<Integer> bQueue = new LinkedList<>();
        int lines = 0;
        int nextDataIndex = 0;

        File dataFolder = new File("src\\" + dataFolderName);
        if (!dataFolder.exists()) dataFolder.mkdir();

        File dataFile = new File("src\\" + dataFolderName + "\\" + dataFileName + nextDataIndex + ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));
        nextDataIndex++;


        int a = 1, b = n;
        int node = root.get(a).get(b);
        nodesQueue.add(node);
        aQueue.add(a);
        bQueue.add(b);
        int nodeCount = 1;
        int qCount = 0;
        while (!nodesQueue.isEmpty()) {
            if (lines % 500 == 0 && lines != 0) {
                writer.close();
                dataFile = new File("src\\" + dataFolderName + "\\" + dataFileName + nextDataIndex + ".txt");
                writer = new BufferedWriter(new FileWriter(dataFile));
                nextDataIndex++;
            }
            node = nodesQueue.poll();
            a = aQueue.poll();
            b = bQueue.poll();
            if (node == -1) {
                writer.write("-1 q q");
                writer.newLine();
                lines++;
                qCount++;
                continue;
            }
            Word word = words.get(node - 1);
            writer.write(node + " " + word.getWord() + " " + word.getTranslation());
            writer.newLine();
            lines++;
            if (a != b) {
                if (a <= node - 1) {
                    nodesQueue.add(root.get(a).get(node - 1));
                    aQueue.add(a);
                    bQueue.add(node - 1);
                    nodeCount++;
                } else {
                    nodesQueue.add(-1);
                    aQueue.add(0);
                    bQueue.add(0);
                }
                if (node + 1 <= b) {
                    nodesQueue.add(root.get(node + 1).get(b));
                    aQueue.add(node + 1);
                    bQueue.add(b);
                    nodeCount++;
                } else {
                    nodesQueue.add(-1);
                    aQueue.add(0);
                    bQueue.add(0);
                }
            } else {
                nodesQueue.add(-1);
                aQueue.add(0);
                bQueue.add(0);
                nodesQueue.add(-1);
                aQueue.add(0);
                bQueue.add(0);
            }
        }
        System.out.println(nodeCount + " nodes has been saved!");
        System.out.println(qCount + " q has been saved!");
        writer.close();
    }

    public void printTree() {
        printTree(0, 1, n);
    }

    public void printTree(int l, int a, int b) {
        if (l == 0) {
            System.out.println(root.get(1).get(n) + " is the root");
        } else if (l > b) {
            System.out.println(root.get(a).get(b) + " is left child of " + l);
        } else {
            System.out.println(root.get(a).get(b) + " is right child of " + l);
        }
        if (a != b) {
            if (a <= root.get(a).get(b) - 1)
                printTree(root.get(a).get(b), a, root.get(a).get(b) - 1);
            if (root.get(a).get(b) + 1 <= b)
                printTree(root.get(a).get(b), root.get(a).get(b) + 1, b);
        }
    }
}


