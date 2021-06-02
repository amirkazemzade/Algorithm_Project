package obst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {

    ArrayList<Word> words;

    public Database() {
        words = new ArrayList<>();
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void updateDatabase() throws FileNotFoundException {
        File dictionary = new File("src\\dictionary.txt");
        if (dictionary.exists()) {
            Scanner in = new Scanner(dictionary);
            while (in.hasNext()) {
                String line = in.nextLine();
                String[] lineWord = line.split(" ");
                words.add(new Word(lineWord[0], lineWord[1], Double.parseDouble(lineWord[2])));
            }
        }
    }
}
