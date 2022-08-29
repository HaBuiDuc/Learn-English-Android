package com.example.learnenglish.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.learnenglish.Vocabulary;
import com.example.learnenglish.database.DateDbSchema.DateTable;
import com.example.learnenglish.database.VocabularyDbSchema.VocabularyTable;

public class AppBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vocabulary.db";
    private static final int VERSION = 1;
    public AppBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String vocabularyTable = "create table " + VocabularyTable.NAME + "( id integer primary key autoincrement, " +
                VocabularyTable.cols.WORD + ", " +
                VocabularyTable.cols.MEANING + ", " +
                VocabularyTable.cols.DATE +
                ")";
        String dateTable = "create table " + DateTable.NAME + "( id integer primary key autoincrement, " +
                DateTable.cols.DATE +
                ")";
        db.execSQL(vocabularyTable);
        db.execSQL(dateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
