package com.example.learnenglish.database;

public class VocabularyDbSchema {
    public static class VocabularyTable{
        public static final String NAME = "vocabulary";
        public static final class cols {
            public static final String WORD = "word";
            public static final String MEANING = "meaning";
            public static final String DATE = "date";
        }
    }
}
