package obst;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {

    public static ArrayList<Word> getWords() throws IOException {
        File sortedDictionary = new File("src\\sortedDictionary.txt");
        File dictionary = new File("src\\dictionary.txt");
        ArrayList<Word> words = new ArrayList<>();

        if (sortedDictionary.exists()) {
            words = readWords(sortedDictionary);
            System.out.println("has read sortedDictionary file!");
        } else if (dictionary.exists()) {
            words = readWords(dictionary);
            words.sort(new WordComparator());
            saveSortedArray(sortedDictionary, words);
            System.out.println("has read dictionary file and created sortedDictionary file!");
        }
        return words;
    }

    public static ArrayList<ArrayList<Word>> getWordsByLetter() throws IOException {
        File sortedDictionary = new File("src\\sortedDictionary.txt");
        File dictionary = new File("src\\dictionary.txt");
        ArrayList<ArrayList<Word>> words = new ArrayList<>();

//        if (sortedDictionary.exists()) {
//            words = readWordsByFirstLetter(sortedDictionary);
//            System.out.println("has read sortedDictionary file!");
//        } else
            if (dictionary.exists()) {
            ArrayList<Word> unsortedWords = readWords(dictionary);
            unsortedWords.sort(new WordComparator());
            saveSortedArray(sortedDictionary, unsortedWords);
            System.out.println("has converted dictionary file sortedDictionary file!");
            words = readWordsByFirstLetter(sortedDictionary);
            System.out.println("has read sortedDictionary file!");
        }
        return words;
    }

    private static ArrayList<Word> readWords(File file) throws FileNotFoundException {
        Scanner in = new Scanner(new BufferedReader(new FileReader(file)));
        ArrayList<Word> words = new ArrayList<>();
        while (in.hasNext()) {
            String line = in.nextLine();
            line = line.replace("\uFEFF", "");
            String[] lineWord = line.split("\\s+");
            words.add(new Word(lineWord[0].toLowerCase(), lineWord[1], Double.parseDouble(lineWord[2])));
        }
        return words;
    }

    private static ArrayList<ArrayList<Word>> readWordsByFirstLetter(File file) throws FileNotFoundException {
        Scanner in = new Scanner(new BufferedReader(new FileReader(file)));
        ArrayList<ArrayList<Word>> wordsByLetter = new ArrayList<>();
        ArrayList<Word> currentWords = new ArrayList<>();
        char firstChar = 'a';
        while (in.hasNext()) {
            String line = in.nextLine();
            String[] lineWord = line.split("\\s+");
            if (lineWord[0].toLowerCase().charAt(0) == firstChar && lineWord[0].toLowerCase().charAt(0) <= 'z') {
                currentWords.add(new Word(lineWord[0], lineWord[1], Double.parseDouble(lineWord[2])));
            } else {
                firstChar += 1;
                wordsByLetter.add(currentWords);
                currentWords = new ArrayList<>();
            }
        }
        return wordsByLetter;
    }

    private static void saveSortedArray(File sortedDictionary, ArrayList<Word> words) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(sortedDictionary));
        for (Word word : words) {
            writer.write(word.getWord() + " " + word.getTranslation() + " " + word.getProvability());
            writer.newLine();
        }
        writer.close();
    }
}
