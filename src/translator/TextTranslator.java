package translator;

import java.io.FileNotFoundException;

public class TextTranslator {

    public static String translate(String text) throws FileNotFoundException {
        WordTranslator wordTranslator = new WordTranslator();
        String[] words = text.split("\\s+");
        StringBuilder translation = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            translation.append(wordTranslator.translate(words[i])).append(" ");
        }
        return translation.toString();
    }
}
