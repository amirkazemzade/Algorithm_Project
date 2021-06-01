package obst;

import java.util.ArrayList;

public class OBST {
    ArrayList<Word> words;
    int n, m;
    double q;

    double[][] elements;
    double[][] weight;
    int[][] root;

    public OBST(ArrayList<Word> words) {
        this.words = words;
        n = words.size();
        m = n + 1;
        double sumOfP = 0;
        for (int i = 0; i < n; i++) {
            sumOfP += words.get(i).provability;
        }
        q = (1 - sumOfP) / m;
    }

    public void makeTree() {
        elements = new double[n + 2][n + 1];
        weight = new double[n + 2][n + 1];
        root = new int[n + 1][n + 1];

        for (int i = 1; i <= n + 1; i++) {
            elements[i][i - 1] = q;
            weight[i][i - 1] = q;
        }

        for (int l = 1; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                weight[i][j] = weight[i][j - 1] + words.get(j - 1).provability + q;
                for (int r = i; r <= j; r++) {
                    if (i == r) {
                        elements[i][j] = elements[i][r - 1] + elements[r + 1][j] + weight[i][j];
                        root[i][j] = r;
                    } else {
                        double temp = elements[i][r - 1] + elements[r + 1][j] + weight[i][j];
                        if (temp < elements[i][j]) {
                            elements[i][j] = temp;
                            root[i][j] = r;
                        }
                    }
                }
            }
        }
    }

    public void printTree() {
        printTree(0, 1, n);
    }

    public void printTree(int l, int a, int b) {
        if (l == 0) {
            System.out.println(root[1][n] + " is the root");
            if (a != b) {
                printTree(root[a][b], a, root[1][n] - 1);
                printTree(root[a][b], root[1][n] + 1, b);
            }
        } else if (l > b) {
            System.out.println(root[a][b] + " is left child of " + l);
            if (a != b) {
                printTree(root[a][b], a, root[a][b] - 1);
                printTree(root[a][b], root[a][b] + 1, b);
            }
        } else {
            System.out.println(root[a][b] + " is right child of " + l);
            if (a != b) {
                printTree(root[a][b], a, root[a][b] - 1);
                printTree(root[a][b], root[a][b] + 1, b);
            }
        }
    }

}


