package com.orange.flickrtask.ui.search;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import com.orange.flickrtask.R;
import com.orange.flickrtask.adapter.PhotoAdapter;
import com.orange.flickrtask.databinding.SearchFragBinding;
import com.orange.flickrtask.model.Photo;
import com.orange.flickrtask.utils.NetworkState;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchFragment extends Fragment {

    private PagedList<Photo> photoArrayList;
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private SearchViewModel searchViewModel;
    private SearchFragBinding searchFragBinding;
    private CompositeDisposable disposable = new CompositeDisposable();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        searchFragBinding = DataBindingUtil.inflate(inflater, R.layout.search_frag, container, false);
        View view = searchFragBinding.getRoot();
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        disposable.add(RxTextView.textChangeEvents(searchFragBinding.searchEditText).skipInitialValue().distinctUntilChanged()
                .debounce(1000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TextViewTextChangeEvent>() {
                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                        getRecentPhotos(textViewTextChangeEvent.getText().toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        return view;
    }


    public void getRecentPhotos(final String string) {
        searchViewModel.getPhotoPagedList(string).observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(PagedList<Photo> photos) {
                photoArrayList = photos;
                showOnRecyclerView(string);
            }
        });
    }

    private void showOnRecyclerView(final String string) {
        photoAdapter = new PhotoAdapter(getContext());
        searchViewModel.getNetworkStateLiveData().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                photoAdapter.setNetworkState(networkState);
                if (!(networkState == NetworkState.LOADED || networkState == NetworkState.LOADING)) {
                    Snackbar.make(recyclerView, "No network!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getRecentPhotos(string);
                        }
                    }).show();
                }
            }
        });
        recyclerView = searchFragBinding.rvPhotos;
        photoAdapter.submitList(photoArrayList);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(photoAdapter);
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
