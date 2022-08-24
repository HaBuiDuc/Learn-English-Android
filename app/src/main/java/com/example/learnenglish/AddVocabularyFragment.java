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

public class AddVocabularyFragment extends Fragment {
    private Button addWordButton;
    private EditText addWordEditText;
    private EditText addMeaningEditText;
    private VocabularyLab mVocabularyLab = VocabularyLab.get(getActivity());
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_vocabulary_fragment, container, false);

        addWordButton = view.findViewById(R.id.add_vocabulary_button);
        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vocabulary vocabulary = new Vocabulary();
                String word = addWordEditText.getText().toString();
                String meaning = addMeaningEditText.getText().toString();
                vocabulary.setWord(word);
                vocabulary.setMeaning(meaning);

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
}
