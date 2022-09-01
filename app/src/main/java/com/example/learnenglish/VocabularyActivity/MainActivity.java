package com.example.learnenglish.VocabularyActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.learnenglish.R;
import com.example.learnenglish.VocabularyPackage.VocabularyLab;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String DATE_EXTRA = "date_extra";
    private BottomNavigationView bottomNavigationView;
    private VocabularyListFragment vocabularyListFragment;
    private VocabularyViewPagerFragment vocabularyViewPagerFragment;
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = (Date) getIntent().getSerializableExtra(DATE_EXTRA);

        bottomNavigationView = findViewById(R.id.app_navigation);
        vocabularyViewPagerFragment = new VocabularyViewPagerFragment();
        vocabularyListFragment = new VocabularyListFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setSelectedItemId(R.id.list_item_nav);
        FragmentManager fragmentManager = getSupportFragmentManager();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_item_nav: {
                Bundle bundle = new Bundle();
                bundle.putSerializable(DATE_EXTRA, date);
                vocabularyListFragment.setArguments(bundle);
                startFragment(vocabularyListFragment);
                return true;
            }
            case R.id.learn_nav: {
                Bundle bundle = new Bundle();
                bundle.putSerializable(DATE_EXTRA, date);
                vocabularyViewPagerFragment.setArguments(bundle);
                startFragment(vocabularyViewPagerFragment);
                return true;
            }
            case R.id.test_meaning_nav: {
                TestMeaningPagerFragment testMeaningPagerFragment = TestMeaningPagerFragment.newInstance(VocabularyLab.get(MainActivity.this).getVocabularyList(date));
                startFragment(testMeaningPagerFragment);
                return true;
            }
        }
        return false;
    }
    private void startFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
        }
    }
    public static Intent newIntent(Context context, Date date) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(DATE_EXTRA, date);
        return intent;
    }
}