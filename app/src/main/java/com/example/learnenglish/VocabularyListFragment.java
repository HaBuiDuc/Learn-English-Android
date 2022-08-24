package com.example.learnenglish;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VocabularyListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ImageButton mAddImageButton;
    private MyAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vocabulary_list_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.vocabulary_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddImageButton = view.findViewById(R.id.add_vocabulary_button);
        mAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddVocabularyActivity.class);
                startActivity(intent);
            }
        });

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateUI() {
        List<Vocabulary> vocabularies = VocabularyLab.get(getActivity()).getVocabularyList();
        if (mAdapter == null) {
            mAdapter = new MyAdapter(vocabularies);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setVocabularyList(VocabularyLab.get(getActivity()).getVocabularyList());
            mAdapter.notifyDataSetChanged();
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView wordTextView;
        private TextView meaningTextView;
        public MyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list_view, parent, false));
            itemView.setOnClickListener(this);

            wordTextView = itemView.findViewById(R.id.word_text_view);
            meaningTextView = itemView.findViewById(R.id.meaning_text_view);
        }
        private Vocabulary mVocabulary;
        @SuppressLint("SetTextI18n")
        private void bind(Vocabulary vocabulary) {
            mVocabulary = vocabulary;
            wordTextView.setText("Word: ".concat(mVocabulary.getWord()));
            meaningTextView.setText("Meaning: ".concat(mVocabulary.getMeaning()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = VocabularyViewPagerActivity.newIntent(getActivity(), mVocabulary);
            startActivity(intent);
        }
    }
    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        private List<Vocabulary> mVocabularyList;
        public MyAdapter(List<Vocabulary> vocabularies) {
            mVocabularyList = vocabularies;
        }
        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new MyHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            Vocabulary vocabulary = mVocabularyList.get(position);
            holder.bind(vocabulary);
        }

        @Override
        public int getItemCount() {
            return mVocabularyList.size();
        }
        public void setVocabularyList(List<Vocabulary> vocabularies) {
            mVocabularyList = vocabularies;
        }
    }
}
