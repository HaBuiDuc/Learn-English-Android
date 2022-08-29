package com.example.learnenglish;

import java.io.Serializable;
import java.util.Date;

public class Vocabulary implements Serializable {
    private Date date;
    private String word;
    private String meaning;

    public Vocabulary(String word, String meaning, Date date) {
        this.word = word;
        this.meaning = meaning;
        this.date = date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
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
