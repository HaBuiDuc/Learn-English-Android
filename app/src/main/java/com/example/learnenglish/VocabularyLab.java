package com.example.learnenglish;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.learnenglish.database.VocabularyBaseHelper;
import com.example.learnenglish.database.VocabularyCursorWrapper;
import com.example.learnenglish.database.VocabularyDbSchema;
import com.example.learnenglish.database.VocabularyDbSchema.VocabularyTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VocabularyLab {
    private static VocabularyLab sVocabularyLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;
    public static VocabularyLab get(Context context) {
        if (sVocabularyLab == null) {
            sVocabularyLab = new VocabularyLab(context);
        }
        return sVocabularyLab;
    }
    private VocabularyLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new VocabularyBaseHelper(mContext).getWritableDatabase();
    }
    public List<Vocabulary> getVocabularyList() {
        List<Vocabulary> vocabularyList = new ArrayList<>();
        VocabularyCursorWrapper cursorWrapper = queryVocabulary(null, null);
        try {
            if (cursorWrapper.moveToFirst()) {
                while (!cursorWrapper.isAfterLast()) {
                    vocabularyList.add(cursorWrapper.getVocabulary());
                    cursorWrapper.moveToNext();
                }
            }
        } finally {
            cursorWrapper.close();
        }
        return vocabularyList;
    }
    public Vocabulary getVocabulary(String word) {
        try (VocabularyCursorWrapper cursorWrapper = queryVocabulary(VocabularyTable.cols.WORD + " = ? ", new String[] {word});){
            if (cursorWrapper.getCount() == 0) {
                return null;
            }
            cursorWrapper.moveToFirst();
            return cursorWrapper.getVocabulary();
        }
    }
    public void addVocabulary(Vocabulary vocabulary) {
        ContentValues contentValues = getContentValues(vocabulary);
        mDatabase.insert(VocabularyTable.NAME, null, contentValues);
    }
    public void deleteVocabulary(Vocabulary vocabulary) {
        mDatabase.delete(VocabularyTable.NAME, VocabularyTable.cols.WORD + " = ? ", new String[] {vocabulary.getWord()});
    }
    private ContentValues getContentValues(Vocabulary vocabulary) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(VocabularyTable.cols.WORD, vocabulary.getWord());
        contentValues.put(VocabularyTable.cols.MEANING, vocabulary.getMeaning());

        return contentValues;
    }
    private VocabularyCursorWrapper queryVocabulary(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(VocabularyTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new VocabularyCursorWrapper(cursor);
    }
}
