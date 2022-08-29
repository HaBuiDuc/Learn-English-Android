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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TestMeaningFragment extends Fragment {
    private static final String VOCABULARY_EXTRA = "vocabulary_extra";
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private TextView vocabularyTextView;
    private Button checkButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_word_pager_fragment, container, false);

        assert getArguments() != null;
        Vocabulary vocabulary = (Vocabulary) getArguments().getSerializable(VOCABULARY_EXTRA);

        vocabularyTextView = view.findViewById(R.id.vocabulary_test_text_view);
        vocabularyTextView.setText(vocabulary.getMeaning());

        textInputLayout = view.findViewById(R.id.vocabulary_input_layout);
        textInputLayout.setError(getString(R.string.wrong_answer));

        textInputEditText = view.findViewById(R.id.vocabulary_text_edit_text);

        checkButton = view.findViewById(R.id.check_button);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ()
            }
        });

        return view;
    }
    public static TestMeaningFragment newInstance(Vocabulary vocabulary) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(VOCABULARY_EXTRA, vocabulary);
        TestMeaningFragment testMeaningFragment = new TestMeaningFragment();
        testMeaningFragment.setArguments(bundle);
        return testMeaningFragment;
    }
}
