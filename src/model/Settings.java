package model;

public class Settings {

    private int ramUsage;
    private boolean byFirstLetter;

    public Settings(int ramUsage, boolean byFirstLetter) {
        this.ramUsage = ramUsage;
        this.byFirstLetter = byFirstLetter;
    }

    public Settings() {
        ramUsage = 0;
        byFirstLetter = true;
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

