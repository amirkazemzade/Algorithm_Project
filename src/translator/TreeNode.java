package translator;

public class TreeNode {
    private int key;
    private String word;
    private String translation;

    public TreeNode(int key, String word, String translation) {
        this.key = key;
        this.word = word;
        this.translation = translation;
    }

    public int getKey() {
        return key;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }
}
