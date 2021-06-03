package obst;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {

    private ArrayList<Word> words;

    public Database() {
        words = new ArrayList<>();
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void updateDatabase() throws IOException {
        File sortedDictionary = new File("src\\sortedDictionary.txt");
        File dictionary = new File("src\\dictionary.txt");

        if (sortedDictionary.exists()) {
            readWords(sortedDictionary);
        } else if (dictionary.exists()) {
            readWords(dictionary);
            words.sort(new WordComparator());
            saveSortedArray(sortedDictionary);
        }
    }

    private void readWords(File file) throws FileNotFoundException {
        Scanner in = new Scanner(file);
        while (in.hasNext()) {
            String line = in.nextLine();
            String[] lineWord = line.split(" ");
            words.add(new Word(lineWord[0], lineWord[1], Double.parseDouble(lineWord[2])));
        }
    }

    private void saveSortedArray(File sortedDictionary) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(sortedDictionary));
        for (Word word : words) {
            writer.write(word.getWord() + " " + word.getTranslation() + " " + word.getProvability());
            writer.newLine();
        }
    }
}
