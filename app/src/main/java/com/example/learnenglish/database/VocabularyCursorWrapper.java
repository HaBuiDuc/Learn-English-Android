package com.example.learnenglish.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.learnenglish.Vocabulary;
import com.example.learnenglish.database.VocabularyDbSchema.VocabularyTable;

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

        return new Vocabulary(word, meaning);
    }
}
