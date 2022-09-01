package com.example.learnenglish.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.learnenglish.VocabularyPackage.Vocabulary;
import com.example.learnenglish.database.VocabularyDbSchema.VocabularyTable;

import java.util.Date;

public class VocabularyCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public VocabularyCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Vocabulary getVocabulary() {
        String word = getString(getColumnIndex(VocabularyTable.cols.WORD));
        String meaning = getString(getColumnIndex(VocabularyTable.cols.MEANING));
        String date = getString(getColumnIndex(VocabularyTable.cols.DATE));

        return new Vocabulary(word, meaning, new Date(date));
    }
}
