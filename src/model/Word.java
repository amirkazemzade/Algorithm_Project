package model;

public class Word {
    private String word;
    private String translation;
    private double provability;

    public Word(String word, String translation, double provability) {
        this.word = word;
        this.translation = translation;
        this.provability = provability;
    }

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

