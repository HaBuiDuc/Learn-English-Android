package com.example.learnenglish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class VocabularyViewPagerActivity extends AppCompatActivity {
    private static final String VOCABULARY_INTENT = "vocabulary_intent";
    private ViewPager2 mViewPager2;
    private VocabularyLab mVocabularyLab = VocabularyLab.get(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabulary_view_pager);
        Vocabulary vocabulary = (Vocabulary) getIntent().getSerializableExtra(VOCABULARY_INTENT);
        List<Vocabulary> vocabularyList = mVocabularyLab.getVocabularyList();

        mViewPager2 = findViewById(R.id.vocabulary_view_pager);
        mViewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return ViewPagerFragment.newInstance(vocabularyList.get(position));
            }

            @Override
            public int getItemCount() {
                return VocabularyLab.get(VocabularyViewPagerActivity.this).getVocabularyList().size();
            }
        });
        for (int i = 0; i < vocabularyList.size(); i++) {
            if (vocabularyList.get(i).getWord().equals(vocabulary.getWord())) {
                mViewPager2.setCurrentItem(i);
                break;
            }
        }
    }
    public static Intent newIntent(Context context, Vocabulary vocabulary) {
        Intent intent = new Intent(context, VocabularyViewPagerActivity.class);
        intent.putExtra(VOCABULARY_INTENT, vocabulary);
        return intent;
    }
}

