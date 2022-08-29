package com.example.learnenglish;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Date;

public class AddVocabularyFragment extends Fragment {
    public static final String DATE_EXTRA = "date_extra";
    private Button addWordButton;
    private EditText addWordEditText;
    private EditText addMeaningEditText;
    private VocabularyLab mVocabularyLab = VocabularyLab.get(getActivity());
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_vocabulary_fragment, container, false);

        Date date = (Date) getArguments().getSerializable(DATE_EXTRA);

        addWordButton = view.findViewById(R.id.add_vocabulary_button);
        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vocabulary vocabulary = new Vocabulary();
                String word = addWordEditText.getText().toString();
                String meaning = addMeaningEditText.getText().toString();
                vocabulary.setWord(word);
                vocabulary.setMeaning(meaning);
                vocabulary.setDate(date);

                if (word.matches("") || meaning.matches("")) {
                    Toast.makeText(getActivity(), "Word and meaning can't blank", Toast.LENGTH_SHORT).show();
                } else {
                    mVocabularyLab.addVocabulary(vocabulary);
                    requireActivity().finish();
                }
            }
        });

        addWordEditText = view.findViewById(R.id.add_word_edit_text);

        addMeaningEditText = view.findViewById(R.id.add_meaning_edit_text);

        return view;
    }
    public static AddVocabularyFragment newInstance(Date date) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATE_EXTRA, date);
        AddVocabularyFragment addVocabularyFragment = new AddVocabularyFragment();
        addVocabularyFragment.setArguments(bundle);
        return addVocabularyFragment;
    }
}
