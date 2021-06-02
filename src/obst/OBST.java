package obst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OBST {
    private ArrayList<Word> words;
    private int n, m;
    private double q;

    private Map<Integer, Map<Integer, Double>> elements;
    private Map<Integer, Map<Integer, Double>> weight;
    private Map<Integer, Map<Integer, Integer>> root;

    public OBST(ArrayList<Word> words) {
        this.words = words;
        n = words.size();
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
            System.out.println("round " + l + "finished!");
        }
    }

    public void printTree() {
        printTree(0, 1, n);
    }

    public void printTree(int l, int a, int b) {
        if (l == 0) {
            System.out.println(root.get(1).get(n) + " is the root");
            if (a != b) {
                printTree(root.get(a).get(b), a, root.get(1).get(n) - 1);
                printTree(root.get(a).get(b), root.get(1).get(n) + 1, b);
            }
        } else if (l > b) {
            System.out.println(root.get(a).get(b) + " is left child of " + l);
            if (a != b) {
                printTree(root.get(a).get(b), a, root.get(a).get(b) - 1);
                printTree(root.get(a).get(b), root.get(a).get(b) + 1, b);
            }
        } else {
            System.out.println(root.get(a).get(b) + " is right child of " + l);
            if (a != b) {
                printTree(root.get(a).get(b), a, root.get(a).get(b) - 1);
                printTree(root.get(a).get(b), root.get(a).get(b) + 1, b);
            }
        }
    }

}


