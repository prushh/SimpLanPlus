package util;

import java.util.ArrayList;

/**
 * Minimal version of symbol table. It stores just current nesting level in order to perform
 * look up with access link and a list of labels for jump instructions.
 */

public class CGenEnv {

    private int nestingLevel = -1;
    private ArrayList<String> labels = new ArrayList<>();

    public CGenEnv() {

    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public String getLabel() {
        return labels.get(labels.size() - 1);
    }

    public void removeLabel() {
        this.labels.remove(labels.size() - 1);
    }

    public void setLabel(String label) {
        this.labels.add(label);
    }

    public void incrementNestingLevel() {
        this.nestingLevel++;
    }

    public void decrementNestingLevel() {
        this.nestingLevel--;
    }
}
