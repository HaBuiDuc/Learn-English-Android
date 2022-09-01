package com.example.learnenglish.AppAdapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnenglish.IShowHideDelete;
import com.example.learnenglish.VocabularyActivity.MainActivity;
import com.example.learnenglish.R;
import com.example.learnenglish.VocabularyPackage.VocabularyLab;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyHolder> {
    Activity mActivity;
    private List<Date> mDateList;
    private List<Integer> mPositionSelected;
    private List<Date> mDateSelected;
    private IShowHideDelete mIShowHideDelete;
    private boolean isEnabled;
    public DateAdapter(List<Date> dateList, Activity activity, Fragment fragment) {
        mPositionSelected = new ArrayList<>();
        mDateSelected = new ArrayList<>();
        this.mDateList = dateList;
        mActivity = activity;
        isEnabled = false;
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
        Date date = mDateList.get(position);
        holder.bind(date);
    }

    @Override
    public int getItemCount() {
        return mDateList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView mTextView;
        private ImageView mDateSelectImageView;
        public MyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_date_list_view, parent, false));
            mTextView = itemView.findViewById(R.id.date_text_view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            mDateSelectImageView = itemView.findViewById(R.id.date_select_image_view);
            mDateSelectImageView.setVisibility(View.INVISIBLE);
        }
        Date date;
        private void bind(Date date) {
            this.date = date;
            mTextView.setText(String.valueOf(date));
            if (!mPositionSelected.contains(this.getAdapterPosition())) {
                mDateSelectImageView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            if (!isEnabled) {
                Intent intent = MainActivity.newIntent(mActivity, date);
                mActivity.startActivity(intent);
            } else {
                if (mPositionSelected.contains(this.getAdapterPosition())) {
                    mPositionSelected.remove(mPositionSelected.indexOf(this.getAdapterPosition()));
                    mDateSelectImageView.setVisibility(View.INVISIBLE);
                    if (mPositionSelected.size() == 0) {
                        isEnabled = false;
                        mIShowHideDelete.showHideDelete(false);
                    }
                } else {
                    mPositionSelected.add(this.getAdapterPosition());
                    mDateSelectImageView.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (!isEnabled) {
                isEnabled = true;
                mIShowHideDelete.showHideDelete(true);
                mPositionSelected.add(this.getAdapterPosition());
                mDateSelectImageView.setVisibility(View.VISIBLE);
            }
            return true;
        }
    }
    public void setDateList(List<Date> dateList) {
            this.mDateList = dateList;
    }
    public void deleteSelected() {
        VocabularyLab vocabularyLab = VocabularyLab.get(mActivity);
        for (Integer i : mPositionSelected) {
            mDateSelected.add(vocabularyLab.getDateList().get(i));
        }
        for (Date date : mDateSelected) {
            vocabularyLab.deleteDate(date);
        }
        mDateSelected.clear();
        mPositionSelected.clear();
        this.setDateList(vocabularyLab.getDateList());
        this.notifyDataSetChanged();
        isEnabled = false;
        mIShowHideDelete.showHideDelete(false);
    }
}
