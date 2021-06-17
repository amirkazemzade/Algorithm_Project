package translator;

import java.io.FileNotFoundException;
import java.util.ArrayList;

// a class to translate text
public class TextTranslator {

    // ** Public Static Functions **//

    // translates given text using wordTranslator class with normal tree method and returning translation as text (String)
    public static String translate(String text) throws FileNotFoundException {
        WordTranslator wordTranslator = new WordTranslator();
        String[] words = text.split("\\s+");
        StringBuilder translation = new StringBuilder();
        for (String word : words) {
            translation.append(wordTranslator.translate(word)).append(" ");
        }
        return translation.toString();
    }

    // translates given text using wordTranslator class with fist case tree method and returning translation as text (String)
    public static String translateByLetter(String text) throws FileNotFoundException {
        char firstCase = 'a';
        ArrayList<WordTranslator> translators = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            translators.add(new WordTranslator(firstCase));
            firstCase++;
        }
        String[] words = text.split("\\s+");
        StringBuilder translation = new StringBuilder();
        for (String word : words) {
            int translatorIndex = word.charAt(0) - 'a';
            if (translatorIndex < 0 || translatorIndex >= 26)
                translation.append("? ");
            else
                translation.append(translators.get(translatorIndex).translate(word)).append(" ");
        }
        return translation.toString();
    }
}
