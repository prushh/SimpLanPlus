package util;

public class CGenEnv {

    private int nestingLevel = -1;
    private String label = "";

    public CGenEnv() {

    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public void setNestingLevel(int nestingLevel) {
        this.nestingLevel = nestingLevel;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void incrementNestingLevel() {
        this.nestingLevel++;
    }

    public void decrementNestingLevel() {
        this.nestingLevel--;
    }
}
