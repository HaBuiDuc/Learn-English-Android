package com.example.learnenglish.DateList;

import androidx.fragment.app.Fragment;

import com.example.learnenglish.SingleFragmentActivity;

public class DateListActivity extends SingleFragmentActivity {


    @Override
    public Fragment createFragment() {
        return new DateListFragment();
    }
}
