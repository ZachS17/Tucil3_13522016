package Utilities;

import java.util.*;

public class Tuple {
    private String word;
    private int evalValue;
    private List<String> prev;

    public Tuple(String word, int evalValue, List<String> prev) {

        this.word = word;
        this.evalValue = evalValue;
        this.prev = new ArrayList<>(prev);
    }

    public Tuple(Tuple temTuple) {

        this.word = temTuple.word;
        this.evalValue = temTuple.evalValue;
        this.prev = new ArrayList<>(temTuple.prev);
    }

    public String getWord() {
        return this.word;
    }

    public int getEvalValue() {
        return this.evalValue;
    }

    public void setEvalValue(int value) {
        this.evalValue = value;
    }

    public List<String> getPrev() {
        return this.prev;
    }
}
