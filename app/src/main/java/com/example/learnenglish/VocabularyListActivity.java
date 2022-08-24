package com.example.learnenglish;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

public class VocabularyListActivity extends SingleFragmentActivity{

    @Override
    public Fragment createFragment() {
        return new VocabularyListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}