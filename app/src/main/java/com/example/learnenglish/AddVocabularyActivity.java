package com.example.learnenglish;

import androidx.fragment.app.Fragment;

public class AddVocabularyActivity extends SingleFragmentActivity{

    @Override
    public Fragment createFragment() {
        return new AddVocabularyFragment();
    }
}