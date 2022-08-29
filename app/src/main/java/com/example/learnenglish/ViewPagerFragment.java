package com.example.learnenglish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewPagerFragment extends Fragment {
    private static final String EXTRA_VOCABULARY = "extra_vocabulary";
    private TextView mWordTextView;
    private TextView mMeaningTextView;
    private Button mShowMeaningButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_fragment, container, false);
        assert getArguments() != null;
        Vocabulary vocabulary = (Vocabulary) getArguments().getSerializable(EXTRA_VOCABULARY);

        mWordTextView = view.findViewById(R.id.word_pager_text_view);
        mWordTextView.setText(vocabulary.getWord());

        mMeaningTextView = view.findViewById(R.id.meaning_pager_text_view);
        mMeaningTextView.setText(vocabulary.getMeaning());
        mMeaningTextView.setVisibility(View.INVISIBLE);

        mShowMeaningButton = view.findViewById(R.id.show_meaning_button);
        mShowMeaningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeaningTextView.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    public static ViewPagerFragment newInstance(Vocabulary vocabulary) {
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_VOCABULARY, vocabulary);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }
}
