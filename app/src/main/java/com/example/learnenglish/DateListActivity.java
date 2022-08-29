package com.example.learnenglish;

import androidx.fragment.app.Fragment;

public class DateListActivity extends SingleFragmentActivity{


    @Override
    public Fragment createFragment() {
        return new DateListFragment();
    }
}
