package com.example.learnenglish.VocabularyActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

public class VocabularyListFragment extends Fragment implements IShowHideDelete {
    private RecyclerView mRecyclerView;
    private ImageButton mAddImageButton;
    private Date date;
    private VocabularyAdapter mAdapter;
    private MenuItem mMenuItem;
    private TextView mNoVocabularyTextView;
    private Button mNoVocabularyButton;
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
//
       mNoVocabularyTextView = view.findViewById(R.id.no_vocabulary_textview);
       mNoVocabularyButton = view.findViewById(R.id.no_vocabulary_add_button);
       mNoVocabularyButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getActivity(), AddVocabularyActivity.class);
                intent.putExtra(AddVocabularyActivity.DATE_EXTRA, date);
                startActivity(intent);
           }
       });

        updateUI();
        return view;
    }
    private void setButtonVisible() {
        if (VocabularyLab.get(getActivity()).getVocabularyList(date).size() == 0) {
            mNoVocabularyButton.setVisibility(View.VISIBLE);
            mNoVocabularyTextView.setVisibility(View.VISIBLE);
            mAddImageButton.setVisibility(View.INVISIBLE);
        } else {
            mNoVocabularyButton.setVisibility(View.INVISIBLE);
            mNoVocabularyTextView.setVisibility(View.INVISIBLE);
            mAddImageButton.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        Log.d("This is a log", "onAttach of VCBL is called");
    }


    public void onDetach() {
        super.onDetach();
        Log.d("This is a log", "onDetach of VCBLF is called");
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
        setButtonVisible();
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
                updateUI();
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
