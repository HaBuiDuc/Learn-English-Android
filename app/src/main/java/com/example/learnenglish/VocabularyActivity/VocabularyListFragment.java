package com.example.learnenglish.VocabularyActivity;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnenglish.AddVocabulary.AddVocabularyActivity;
import com.example.learnenglish.AppAdapter.VocabularyAdapter;
import com.example.learnenglish.IShowHideDelete;
import com.example.learnenglish.R;
import com.example.learnenglish.VocabularyPackage.Vocabulary;
import com.example.learnenglish.VocabularyPackage.VocabularyLab;

import java.util.Date;
import java.util.List;

public class VocabularyListFragment extends Fragment implements IShowHideDelete {
    private RecyclerView mRecyclerView;
    private ImageButton mAddImageButton;
    private Date date;
    private VocabularyAdapter mAdapter;
    private MenuItem mMenuItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vocabulary_list_fragment, container, false);
        Log.d("This is a log", "onCreateView is called");
        assert getArguments() != null;
        date = (Date) getArguments().getSerializable(MainActivity.DATE_EXTRA);

        mRecyclerView = view.findViewById(R.id.vocabulary_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setHasOptionsMenu(true);

        mAddImageButton = view.findViewById(R.id.add_vocabulary_button);
        mAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddVocabularyActivity.class);
                intent.putExtra(AddVocabularyActivity.DATE_EXTRA, date);
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
        List<Vocabulary> vocabularies = VocabularyLab.get(getActivity()).getVocabularyList(date);
        if (mAdapter == null) {
            mAdapter = new VocabularyAdapter(vocabularies, getActivity(), date, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setVocabularyList(VocabularyLab.get(getActivity()).getVocabularyList(date));
            mAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void showDeleteMenu(boolean show) {
        mMenuItem.setVisible(show);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d("This is a log", "onCreateOptionMenu is called");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_custom_menu, menu);
        mMenuItem = menu.findItem(R.id.delete_item);
        showDeleteMenu(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_item: {
                mAdapter.deleteSelected();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void showHideDelete(boolean show) {
        showDeleteMenu(show);
    }
}
