package model;

// a class for keeping word's properties
public class Word {
    private final String word;
    private final String translation;
    private final double provability;

    // ** Constructors **//

    public Word(String word, String translation, double provability) {
        this.word = word;
        this.translation = translation;
        this.provability = provability;
    }

    // ** Getters **//

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public double getProvability() {
        return provability;
    }
}

