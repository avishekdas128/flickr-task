package com.orange.flickrtask.ui.search;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orange.flickrtask.R;
import com.orange.flickrtask.adapter.PhotoAdapter;
import com.orange.flickrtask.databinding.SearchFragBinding;
import com.orange.flickrtask.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private ArrayList<Photo> photoArrayList;
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private SearchViewModel searchViewModel;
    private SearchFragBinding searchFragBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        searchFragBinding = DataBindingUtil.inflate(inflater,R.layout.search_frag,container,false);
        View view = searchFragBinding.getRoot();
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchFragBinding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                getRecentPhotos();
            }
        });
        return view;
    }


    public void getRecentPhotos() {
        searchViewModel.getAllSearchedPhotos(searchFragBinding.searchEditText.getText().toString()).observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> moviesFromLiveData) {
                photoArrayList = (ArrayList<Photo>) moviesFromLiveData;
                showOnRecyclerView();
            }
        });
    }

    private void showOnRecyclerView() {
        recyclerView = searchFragBinding.rvPhotos;
        photoAdapter = new PhotoAdapter(getContext(), photoArrayList);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(photoAdapter);
        photoAdapter.notifyDataSetChanged();
    }

    public void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),0);
    }

}
