package com.example.learnenglish.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class DateCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public DateCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Date getDate() throws ParseException {
        String dateString = getString(getColumnIndex(DateDbSchema.DateTable.cols.DATE));
//        return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(dateString);
        return new Date(dateString);
    }
}
