package com.example.learnenglish.AddVocabulary;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.learnenglish.SingleFragmentActivity;

import java.util.Date;

public class AddVocabularyActivity extends SingleFragmentActivity {
    public static final String DATE_EXTRA = "date_extra";
    private Date date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Fragment createFragment() {

        date = (Date) getIntent().getSerializableExtra(DATE_EXTRA);
        return AddVocabularyFragment.newInstance(date);
    }
}
