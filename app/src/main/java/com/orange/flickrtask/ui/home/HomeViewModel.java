package com.orange.flickrtask.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.orange.flickrtask.datasource.PhotoDataSource;
import com.orange.flickrtask.datasource.PhotoDataSourceFactory;
import com.orange.flickrtask.model.Photo;
import com.orange.flickrtask.service.FlickrService;
import com.orange.flickrtask.service.RetrofitInstance;
import com.orange.flickrtask.utils.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel {

    private LiveData<PhotoDataSource> photoDataSourceLiveData;
    private LiveData<NetworkState> networkStateLiveData;
    private Executor executor;
    private LiveData<PagedList<Photo>> photoPagedList;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        init(application);
    }

    public void init(Application application){
        FlickrService flickrService = RetrofitInstance.getService();
        PhotoDataSourceFactory photoDataSourceFactory = new PhotoDataSourceFactory(flickrService,application);
        photoDataSourceLiveData = photoDataSourceFactory.getMutableLiveData();
        networkStateLiveData = Transformations.switchMap(photoDataSourceLiveData, new Function<PhotoDataSource, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(PhotoDataSource dataSource) {
                return dataSource.getNetworkState();
            }
        });
        PagedList.Config config = (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10).setPageSize(4).setPrefetchDistance(4).build();
        executor = Executors.newFixedThreadPool(5);
        photoPagedList = (new LivePagedListBuilder<Long,Photo>(photoDataSourceFactory,config)).setFetchExecutor(executor).build();
    }

    public LiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }

    public LiveData<PagedList<Photo>> getPhotoPagedList() {
        return photoPagedList;
    }
}
