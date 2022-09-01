package com.example.learnenglish.DateList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learnenglish.AppAdapter.DateAdapter;
import com.example.learnenglish.DatePickerFragment;
import com.example.learnenglish.IShowHideDelete;
import com.example.learnenglish.R;
import com.example.learnenglish.VocabularyPackage.VocabularyLab;

import java.util.Date;
import java.util.List;

public class DateListFragment extends Fragment implements IShowHideDelete {
    private static final String DIALOG_DATE = "dialog_date";
    private static final int REQUEST_DATE = 0;
    private RecyclerView mRecyclerView;
//    private MyAdapter myAdapter;
    private ImageButton mAddDateButton;
    private DateAdapter myAdapter;
    private MenuItem mMenuItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date_list_fragment, container, false);

        mAddDateButton = view.findViewById(R.id.add_date_button);
        mAddDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setTargetFragment(DateListFragment.this, REQUEST_DATE );
                datePickerFragment.show(fragmentManager, DIALOG_DATE);
            }
        });

        setHasOptionsMenu(true);

        mRecyclerView = view.findViewById(R.id.date_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }
    private void updateUI() {
        VocabularyLab vocabularyLab = VocabularyLab.get(getActivity());
        List<Date> dateList = vocabularyLab.getDateList();
        if (myAdapter == null) {
            myAdapter = new DateAdapter(dateList, getActivity(), this);
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
    private void showDeleteMenu(boolean show) {
        mMenuItem.setVisible(show);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_custom_menu, menu);
        mMenuItem = menu.findItem(R.id.delete_item);
        showDeleteMenu(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_item) {
            myAdapter.deleteSelected();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showHideDelete(boolean show) {
        showDeleteMenu(show);
    }
}
