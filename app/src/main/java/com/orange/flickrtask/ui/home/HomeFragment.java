package com.orange.flickrtask.ui.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.flickrtask.R;
import com.orange.flickrtask.adapter.PhotoAdapter;
import com.orange.flickrtask.databinding.HomeFragBinding;
import com.orange.flickrtask.model.Photo;
import com.orange.flickrtask.utils.NetworkState;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private PagedList<Photo> photoArrayList;
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HomeViewModel homeViewModel;
    private HomeFragBinding homeFragBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeFragBinding = DataBindingUtil.inflate(inflater,R.layout.home_frag,container,false);
        View view = homeFragBinding.getRoot();
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        getRecentPhotos();
        swipeRefreshLayout = homeFragBinding.swipeLayout;
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecentPhotos();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }


    public void getRecentPhotos() {
        homeViewModel.getPhotoPagedList().observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(PagedList<Photo> photos) {
                photoArrayList = photos;
                showOnRecyclerView();
            }
        });
    }

    private void showOnRecyclerView() {
        recyclerView = homeFragBinding.rvMovies;
        photoAdapter = new PhotoAdapter(getContext());
        photoAdapter.submitList(photoArrayList);
        homeViewModel.getNetworkStateLiveData().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                photoAdapter.setNetworkState(networkState);
            }
        });
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(photoAdapter);
        photoAdapter.notifyDataSetChanged();
    }
}
