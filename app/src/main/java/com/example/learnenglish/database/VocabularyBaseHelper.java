package com.example.learnenglish.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.learnenglish.database.VocabularyDbSchema.VocabularyTable;

public class VocabularyBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vocabulary.db";
    private static final int VERSION = 1;
    public VocabularyBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlExec = "create table " + VocabularyTable.NAME + "( id integer primary key autoincrement, " +
                VocabularyTable.cols.WORD + ", " +
                VocabularyTable.cols.MEANING +
                ")";
        db.execSQL(sqlExec);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
