package com.example.learnenglish.VocabularyActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.learnenglish.R;
import com.example.learnenglish.VocabularyPackage.Vocabulary;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

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

        textInputEditText = view.findViewById(R.id.vocabulary_text_edit_text);
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Objects.requireNonNull(textInputEditText.getText()).toString().equals("")) {
                    textInputLayout.setError(null);
                }
            }
        });

        checkButton = view.findViewById(R.id.check_button);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                String input = textInputEditText.getText().toString();
                if (Objects.equals(input, vocabulary.getWord())) {
                    int color = getResources().getColor(R.color.teal_200);
                    textInputLayout.setErrorTextColor(ColorStateList.valueOf(color));
                    textInputLayout.setError("Correct");
                    textInputLayout.setErrorIconDrawable(getResources().getDrawable(R.drawable.ic_check));
                    textInputLayout.setErrorIconTintList(ColorStateList.valueOf(color));
                    textInputEditText.setEnabled(false);
                } else {
                    textInputLayout.setError("Wrong");
                }
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
