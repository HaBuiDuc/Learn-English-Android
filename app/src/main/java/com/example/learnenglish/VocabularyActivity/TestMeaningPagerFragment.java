package com.example.learnenglish.VocabularyActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.learnenglish.R;
import com.example.learnenglish.VocabularyPackage.Vocabulary;

import java.io.Serializable;
import java.util.List;

public class TestMeaningPagerFragment extends Fragment {
    private static final String VOCABULARY_EXTRA = "vocabulary_extra";
    private ViewPager2 viewPager2;
    private List<Vocabulary> vocabularyList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_word_pager_activity, container, false);

        vocabularyList = (List<Vocabulary>) getArguments().getSerializable(VOCABULARY_EXTRA);

        FragmentManager fragmentManager = getParentFragmentManager();
        viewPager2 = view.findViewById(R.id.test_meaning_view_pager);
        viewPager2.setAdapter(new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {

                return TestMeaningFragment.newInstance(vocabularyList.get(position));
            }

            @Override
            public int getItemCount() {
                return vocabularyList.size();
            }
        });
        return view;
    }
    public static TestMeaningPagerFragment newInstance(List<Vocabulary> vocabularyList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(VOCABULARY_EXTRA, (Serializable) vocabularyList);
        TestMeaningPagerFragment testMeaningPagerFragment = new TestMeaningPagerFragment();
        testMeaningPagerFragment.setArguments(bundle);
        return testMeaningPagerFragment;
    }
}
