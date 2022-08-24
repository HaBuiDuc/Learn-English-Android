package com.example.learnenglish;

import java.io.Serializable;

public class Vocabulary implements Serializable {
    private String word;
    private String meaning;

    public Vocabulary(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }
    public Vocabulary() {};
    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
