package com.kazy.copedia.model;

public class Word {

    final private int id;

    final private String word;

    final private String mean;

    final private int level;

    public Word(int id, String word, String mean, int level) {
        this.id = id;
        this.word = word;
        this.mean = mean;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getMean() {
        return mean;
    }

    public int getLevel() {
        return level;
    }
}
