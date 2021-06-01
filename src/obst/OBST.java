package obst;

import java.util.ArrayList;

public class OBST {
    ArrayList<Word> words;
    int n, m;
    double q;

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


}


