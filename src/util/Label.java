package util;

public class Label {

    public String getLabel() {
        return this.toString().substring(11, this.toString().length() - 1);
    }

}
