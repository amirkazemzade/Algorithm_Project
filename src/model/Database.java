package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


// this class does works of reading and writing of not OBST database
public class Database {

    // ** Public Static Functions **//

    // Saves settings into a file for loading it in later runs
    public static void writeSettings(Settings settings) throws IOException {
        File settingsFolder = new File("src\\settings");
        File settingsFile = new File("src\\settings\\settings.txt");

        if (!settingsFolder.exists()) settingsFolder.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile));

        writer.write(settings.getRamUsage() + " " + settings.isByFirstLetter() + " " + settings.getTreeSize());
        writer.close();
    }

    // Reads setting from the setting file and returns an instance of read setting
    public static Settings readSetting() throws IOException {
        File settingsFile = new File("src\\settings\\settings.txt");
        if (!settingsFile.exists()) {
            Settings settings = new Settings();
            writeSettings(settings);
            return settings;
        } else {
            Scanner in = new Scanner(new BufferedReader(new FileReader(settingsFile)));
            return new Settings(in.nextInt(), in.nextBoolean(), in.nextInt());
        }
    }

    // reads sorted dictionary data from file and returns an array of Words
    // if there isn't any sorted dictionary, reads dictionary and sorts it by alphabetic order,
    // and saves it as a sorted dictionary file
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

    // reads words from sorted dictionary and makes an array list for each letter that contains words started with that letter,
    // then returns an array list of those array lists
    // if there isn't any sorted dictionary, reads dictionary and sorts it by alphabetic order,
    // and saves it as a sorted dictionary file
    public static ArrayList<ArrayList<Word>> getWordsByLetter() throws IOException {
        File sortedDictionary = new File("src\\sortedDictionary.txt");
        File dictionary = new File("src\\dictionary.txt");
        ArrayList<ArrayList<Word>> words = new ArrayList<>();

        if (sortedDictionary.exists()) {
            words = readWordsByFirstLetter(sortedDictionary);
            System.out.println("has read sortedDictionary file!");
        } else if (dictionary.exists()) {
            ArrayList<Word> unsortedWords = readWords(dictionary);
            unsortedWords.sort(new WordComparator());
            saveSortedArray(sortedDictionary, unsortedWords);
            System.out.println("has converted dictionary file sortedDictionary file!");
            words = readWordsByFirstLetter(sortedDictionary);
            System.out.println("has read sortedDictionary file!");
        }
        return words;
    }

    // ** Private Functions **//

    // this function does reading file work of getWords() function
    private static ArrayList<Word> readWords(File file) throws FileNotFoundException {
        Scanner in = new Scanner(new BufferedReader(new FileReader(file)));
        ArrayList<Word> words = new ArrayList<>();
        int size = -1;
        try {
            Settings settings = readSetting();
            size = settings.getTreeSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (size == -1) {
            while (in.hasNext()) {
                String line = in.nextLine();
                line = line.replace("\uFEFF", "");
                String[] lineWord = line.split("\\s+");
                words.add(new Word(lineWord[0].toLowerCase(), lineWord[1], Double.parseDouble(lineWord[2])));
            }
        } else {
            while (in.hasNext() && size > 0) {
                String line = in.nextLine();
                line = line.replace("\uFEFF", "");
                String[] lineWord = line.split("\\s+");
                words.add(new Word(lineWord[0].toLowerCase(), lineWord[1], Double.parseDouble(lineWord[2])));
                size--;
            }
        }
        return words;
    }

    // this function does reading file work of getWordsByLetter() function
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

    // this function saves the given list into given file
    private static void saveSortedArray(File sortedDictionary, ArrayList<Word> words) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(sortedDictionary));
        for (Word word : words) {
            writer.write(word.getWord() + " " + word.getTranslation() + " " + word.getProvability());
            writer.newLine();
        }
        writer.close();
    }
}
