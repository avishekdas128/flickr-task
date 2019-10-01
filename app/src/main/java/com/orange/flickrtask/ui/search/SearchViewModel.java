package com.orange.flickrtask.ui.search;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.orange.flickrtask.datasource.SearchDataSource;
import com.orange.flickrtask.datasource.SearchDataSourceFactory;
import com.orange.flickrtask.model.Photo;
import com.orange.flickrtask.service.FlickrService;
import com.orange.flickrtask.service.RetrofitInstance;
import com.orange.flickrtask.utils.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchViewModel extends AndroidViewModel  {

    private LiveData<SearchDataSource> searchDataSourceLiveData;
    private LiveData<NetworkState> networkStateLiveData;
    private Executor executor;
    private LiveData<PagedList<Photo>> photoPagedList;
    SearchDataSourceFactory searchDataSourceFactory;
    FlickrService flickrService;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        flickrService = RetrofitInstance.getService();
        searchDataSourceFactory = new SearchDataSourceFactory(flickrService,application);
    }

    public LiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }

    public LiveData<PagedList<Photo>> getPhotoPagedList(String searchText) {
        searchDataSourceFactory.setSearchText(searchText);
        searchDataSourceLiveData = searchDataSourceFactory.getMutableLiveData();
        networkStateLiveData = Transformations.switchMap(searchDataSourceLiveData, new Function<SearchDataSource, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(SearchDataSource dataSource) {
                return dataSource.getNetworkState();
            }
        });
        PagedList.Config config = (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10).setPageSize(4).setPrefetchDistance(4).build();
        executor = Executors.newFixedThreadPool(5);
        photoPagedList = (new LivePagedListBuilder<Long,Photo>(searchDataSourceFactory,config)).setFetchExecutor(executor).build();
        return photoPagedList;
    }
}
