package com.example.learnenglish.AppAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnenglish.IShowHideDelete;
import com.example.learnenglish.R;
import com.example.learnenglish.VocabularyPackage.Vocabulary;
import com.example.learnenglish.VocabularyPackage.VocabularyLab;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.MyHolder> {
    private List<Vocabulary> mVocabularyList;
    private List<Integer> mPositonSelectedList;
    private List<Vocabulary> mVocabularySelectedList;
    private IShowHideDelete mIShowHideDelete;


    public boolean isEnabled() {
        return isEnabled;
    }

    private boolean isEnabled;
    private Activity mActivity;
    private Date mDate;
    public VocabularyAdapter(List<Vocabulary> vocabularies, Activity activity, Date date, Fragment fragment){
        mVocabularySelectedList = new ArrayList<>();
        mPositonSelectedList = new ArrayList<>();
        mVocabularyList = vocabularies;
        isEnabled = false;
        mActivity = activity;
        mDate = date;
        mIShowHideDelete = (IShowHideDelete) fragment;
    }
    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        Vocabulary vocabulary = mVocabularyList.get(position);
        holder.bind(vocabulary);
    }

    @Override
    public int getItemCount() {
        return mVocabularyList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private TextView wordTextView;
        private TextView meaningTextView;
        private ImageView checkImageView;
        public MyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_vocabulary_list_view, parent, false));
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);

            wordTextView = itemView.findViewById(R.id.word_text_view);
            meaningTextView = itemView.findViewById(R.id.meaning_text_view);
            checkImageView = itemView.findViewById(R.id.check_image_view);
            checkImageView.setVisibility(View.INVISIBLE);
        }
        private Vocabulary mVocabulary;
        @SuppressLint("SetTextI18n")
        public void bind(Vocabulary vocabulary) {
            mVocabulary = vocabulary;
            wordTextView.setText("Word: ".concat(mVocabulary.getWord()));
            meaningTextView.setText("Meaning: ".concat(mVocabulary.getMeaning()));
            if (!mPositonSelectedList.contains(this.getAdapterPosition())) {
                checkImageView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (!isEnabled) {
                mPositonSelectedList.add(this.getAdapterPosition());
                checkImageView.setVisibility(View.VISIBLE);
                isEnabled = true;
                mIShowHideDelete.showHideDelete(true);
            }
            return true;
        }

        @Override
        public void onClick(View view) {
            if (isEnabled) {
                if (!mPositonSelectedList.contains(this.getAdapterPosition())) {
                    mPositonSelectedList.add(this.getAdapterPosition());
                    checkImageView.setVisibility(View.VISIBLE);
                } else {
                    mPositonSelectedList.remove(mPositonSelectedList.indexOf(this.getAdapterPosition()));
                    checkImageView.setVisibility(View.INVISIBLE);
                    if (mPositonSelectedList.size() == 0) {
                        isEnabled = false;
                        mIShowHideDelete.showHideDelete(false);
                    }
                }
            }
            Log.d("This is a log", String.valueOf( mPositonSelectedList.size()) + isEnabled);
        }
    }
    public void setVocabularyList(List<Vocabulary> vocabularies) {
            mVocabularyList = vocabularies;
    }
    public void deleteSelected() {
        VocabularyLab vocabularyLab = VocabularyLab.get(mActivity);
        for (Integer i : mPositonSelectedList) {
            mVocabularySelectedList.add(vocabularyLab.getVocabularyList(mDate).get(i));
        }
        for (Vocabulary vocabulary : mVocabularySelectedList) {
            vocabularyLab.deleteVocabulary(vocabulary);
        }
        mPositonSelectedList.clear();
        mVocabularySelectedList.clear();
        this.setVocabularyList(vocabularyLab.getVocabularyList(mDate));
        this.notifyDataSetChanged();
        isEnabled = false;
        mIShowHideDelete.showHideDelete(false);
    }
}
