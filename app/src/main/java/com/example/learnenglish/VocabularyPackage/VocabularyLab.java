package com.example.learnenglish.VocabularyPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.learnenglish.database.AppBaseHelper;
import com.example.learnenglish.database.DateCursorWrapper;
import com.example.learnenglish.database.DateDbSchema.DateTable;
import com.example.learnenglish.database.VocabularyCursorWrapper;
import com.example.learnenglish.database.VocabularyDbSchema.VocabularyTable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        mDatabase = new AppBaseHelper(mContext).getWritableDatabase();
    }
    public List<Vocabulary> getVocabularyList(Date date) {
        List<Vocabulary> vocabularyList = new ArrayList<>();
        VocabularyCursorWrapper cursorWrapper = queryVocabulary(null, null);
        try {
            if (cursorWrapper.moveToFirst()) {
                while (!cursorWrapper.isAfterLast()) {
                    if (cursorWrapper.getVocabulary().getDate().equals(date)) {
                        vocabularyList.add(cursorWrapper.getVocabulary());
                    }
                    cursorWrapper.moveToNext();
                }
            }
        } finally {
            cursorWrapper.close();
        }
        return vocabularyList;
    }
    public List<Date> getDateList() {
        List<Date> dateList = new ArrayList<>();
        DateCursorWrapper cursorWrapper = queryDate(null, null);
        try {
            if (cursorWrapper.moveToFirst()) {
                while (!cursorWrapper.isAfterLast()) {
                    dateList.add(cursorWrapper.getDate());
                    cursorWrapper.moveToNext();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            cursorWrapper.close();
        }
        return dateList;
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
        contentValues.put(VocabularyTable.cols.DATE, vocabulary.getDate().toString());

        return contentValues;
    }
    public void addDate(Date date) {
        ContentValues contentValues = getContentValues(date);
        mDatabase.insert(DateTable.NAME, null, contentValues);
    }
    public void deleteDate(Date date) {
        VocabularyCursorWrapper cursorWrapper = queryVocabulary(null, null);
        try {
            if (cursorWrapper.moveToFirst()) {
                while (!cursorWrapper.isAfterLast()) {
                    if (cursorWrapper.getVocabulary().getDate().equals(date)) {
                        mDatabase.delete(VocabularyTable.NAME, VocabularyTable.cols.WORD + " = ? ", new String[] {
                                cursorWrapper.getVocabulary().getWord()
                        });
                    }
                    cursorWrapper.moveToNext();
                }
            }
        } finally {
            cursorWrapper.close();
        }
        mDatabase.delete(DateTable.NAME, DateTable.cols.DATE + " = ? ", new String[]{date.toString()});
    }
    private ContentValues getContentValues(Date date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DateTable.cols.DATE, String.valueOf(date));

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
    private DateCursorWrapper queryDate(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(DateTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new DateCursorWrapper(cursor);
    }
}
