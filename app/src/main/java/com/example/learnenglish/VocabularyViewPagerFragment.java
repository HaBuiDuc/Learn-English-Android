package com.example.learnenglish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Date;
import java.util.List;

public class VocabularyViewPagerFragment extends Fragment{
    public static final String VOCABULARY_INTENT = "vocabulary_intent";
    private ViewPager2 mViewPager2;
    private Date date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vocabulary_view_pager, container, false);
        date = (Date) getArguments().getSerializable(MainActivity.DATE_EXTRA);
        VocabularyLab vocabularyLab = VocabularyLab.get(getActivity());
        List<Vocabulary> vocabularyList = vocabularyLab.getVocabularyList(date);
        assert getArguments() != null;

        mViewPager2 = view.findViewById(R.id.vocabulary_view_pager);
        mViewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return ViewPagerFragment.newInstance(vocabularyList.get(position));
            }

            @Override
            public int getItemCount() {
                return VocabularyLab.get(getActivity()).getVocabularyList(date).size();
            }
        });
        mViewPager2.setSaveEnabled(false);
        return view;
    }

}

