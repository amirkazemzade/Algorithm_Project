package model;

// a class for keeping settings
public class Settings {

    private int ramUsage;
    private boolean byFirstLetter;
    private int treeSize;

    // ** Constructors **//

    public Settings(int ramUsage, boolean byFirstLetter, int treeSize) {
        this.ramUsage = ramUsage;
        this.byFirstLetter = byFirstLetter;
        this.treeSize = treeSize;
    }

    public Settings() {
        ramUsage = 0;
        byFirstLetter = true;
        treeSize = -1;
    }

    // ** Getters And Setters **//

    public int getTreeSize() {
        return treeSize;
    }

    public void setTreeSize(int treeSize) {
        this.treeSize = treeSize;
    }

    public int getRamUsage() {
        return ramUsage;
    }

    public void setRamUsage(int ramUsage) {
        this.ramUsage = ramUsage;
    }

    public boolean isByFirstLetter() {
        return byFirstLetter;
    }

    public void setByFirstLetter(boolean byFirstLetter) {
        this.byFirstLetter = byFirstLetter;
    }
}

