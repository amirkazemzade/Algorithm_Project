package translator;

// a class to keep properties of a word organized in tree data
public class TreeNode {
    private final int key;
    private final String word;
    private final String translation;

    // ** Constructor **//

    public TreeNode(int key, String word, String translation) {
        this.key = key;
        this.word = word;
        this.translation = translation;
    }

    // ** Getters **//

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
