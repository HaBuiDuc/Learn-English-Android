package com.example.learnenglish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class DateListFragment extends Fragment {
    private static final String DIALOG_DATE = "dialog_date";
    private static final int REQUEST_DATE = 0;
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private ImageButton mAddDateButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date_list_fragment, container, false);

        mAddDateButton = view.findViewById(R.id.add_date_button);
        mAddDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Button is pressed", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getParentFragmentManager();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setTargetFragment(DateListFragment.this, REQUEST_DATE );
                datePickerFragment.show(fragmentManager, DIALOG_DATE);
            }
        });

        mRecyclerView = view.findViewById(R.id.date_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }
    private void updateUI() {
        VocabularyLab vocabularyLab = VocabularyLab.get(getActivity());
        List<Date> dateList = vocabularyLab.getDateList();
        if (myAdapter == null) {
            myAdapter = new MyAdapter(dateList);
            mRecyclerView.setAdapter(myAdapter);
        } else {
            myAdapter.setDateList(dateList);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("This is a log", "On resume is called");
        updateUI();
    }

    private class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextView;
        public MyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_date_list_view, parent, false));
            mTextView = itemView.findViewById(R.id.date_text_view);
            itemView.setOnClickListener(this);
        }
        Date date;
        private void bind(Date date) {
            this.date = date;
            mTextView.setText(String.valueOf(date));
        }

        @Override
        public void onClick(View v) {
            Intent intent = MainActivity.newIntent(getActivity(), date);
            startActivity(intent);
        }
    }
    private class MyAdapter extends RecyclerView.Adapter <MyHolder> {
        private List<Date> mDateList;
        public MyAdapter(List<Date> dateList) {
            this.mDateList = dateList;
        }
        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new MyHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            Date date = mDateList.get(position);
            holder.bind(date);
        }

        @Override
        public int getItemCount() {
            return mDateList.size();
        }
        public void setDateList(List<Date> dateList) {
            this.mDateList = dateList;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            assert data != null;
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            VocabularyLab.get(getActivity()).addDate(date);
            List<Date> dateList = VocabularyLab.get(getActivity()).getDateList();
            myAdapter.setDateList(dateList);
            myAdapter.notifyItemInserted(dateList.size()-1);
        }
    }
}
