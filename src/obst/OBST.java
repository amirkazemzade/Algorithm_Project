package obst;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class OBST {
    private ArrayList<Word> words;
    private int n, m;
    private double q;

    private Map<Integer, Map<Integer, Double>> elements;
    private Map<Integer, Map<Integer, Double>> weight;
    private Map<Integer, Map<Integer, Integer>> root;

    public OBST(ArrayList<Word> words) {
        this.words = words;
//        n = words.size(); TODO: make it preform good for size of words
        n = 1000;
        m = n + 1;
        double sumOfP = 0;
        for (int i = 0; i < n; i++) {
            sumOfP += words.get(i).getProvability();
        }
        q = (1 - sumOfP) / m;
    }

    public Map<Integer, Map<Integer, Integer>> getRoot() {
        return root;
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
//            System.out.println("round " + l + " finished!");
        }
    }

    public void saveTree() throws IOException {
        Queue<Integer> nodesQueue = new LinkedList<>();
        Queue<Integer> aQueue = new LinkedList<>();
        Queue<Integer> bQueue = new LinkedList<>();
        int lines = 0;
        File dataFile = new File("src\\data\\data" + (lines + 1) + "-" + (lines + 500) + ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));

        int a = 1, b = n;
        int node = root.get(a).get(b);
        nodesQueue.add(node);
        aQueue.add(a);
        bQueue.add(b);
        while (!nodesQueue.isEmpty()) {
            if (lines % 500 == 0 && lines != 0) {
                writer.close();
                dataFile = new File("src\\data\\data" + (lines + 1) + "-" + (lines + 500) + ".txt");
                writer = new BufferedWriter(new FileWriter(dataFile));
            }
            node = nodesQueue.poll();
            a = aQueue.poll();
            b = bQueue.poll();
            Word word = words.get(node);
            writer.write(node + " " + word.getWord() + " " + word.getTranslation());
            writer.newLine();
            lines++;
            if (a != b) {
                if (a <= node - 1) {
                    nodesQueue.add(root.get(a).get(node - 1));
                    aQueue.add(a);
                    bQueue.add(node - 1);
                }
                if (node + 1 <= b) {
                    nodesQueue.add(root.get(node + 1).get(b));
                    aQueue.add(node + 1);
                    bQueue.add(b);
                }
            }
        }
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


