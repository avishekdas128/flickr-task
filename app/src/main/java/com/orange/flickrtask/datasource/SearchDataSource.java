package com.orange.flickrtask.datasource;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.orange.flickrtask.R;
import com.orange.flickrtask.model.Example;
import com.orange.flickrtask.model.Photo;
import com.orange.flickrtask.model.PhotoDBResponse;
import com.orange.flickrtask.service.FlickrService;
import com.orange.flickrtask.service.RetrofitInstance;
import com.orange.flickrtask.utils.NetworkState;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDataSource extends PageKeyedDataSource<Long, Photo> {

    private FlickrService flickrService;
    private String searchText;
    private Application application;
    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<NetworkState> initialLoading;

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public SearchDataSource(FlickrService flickrService, Application application, String searchText) {
        this.flickrService = flickrService;
        this.application = application;
        this.searchText = searchText;
        networkState = new MutableLiveData<>();
        initialLoading = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Photo> callback) {
        networkState.postValue(NetworkState.LOADING);
        initialLoading.postValue(NetworkState.LOADING);
        flickrService = RetrofitInstance.getService();
        Call<Example> call = flickrService.getSearchedPhotosWithPaging(application.getApplicationContext().getString(R.string.api_key),searchText,1);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example example = response.body();
                PhotoDBResponse photoDBResponse = example.getPhotos();
                ArrayList<Photo> photos;
                photos = (ArrayList<Photo>) photoDBResponse.getPhoto();
                callback.onResult(photos,null,(long)2);
                initialLoading.postValue(NetworkState.LOADED);
                networkState.postValue(NetworkState.LOADED);
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Photo> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Photo> callback) {
        networkState.postValue(NetworkState.LOADING);
        flickrService = RetrofitInstance.getService();
        Call<Example> call = flickrService.getSearchedPhotosWithPaging(application.getApplicationContext().getString(R.string.api_key),searchText,params.key);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example example = response.body();
                PhotoDBResponse photoDBResponse = example.getPhotos();
                ArrayList<Photo> photos;
                if (photoDBResponse != null && photoDBResponse.getPhoto() != null) {
                    photos = (ArrayList<Photo>) photoDBResponse.getPhoto();
                    callback.onResult(photos,params.key+1);
                }
                networkState.postValue(NetworkState.LOADED);
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }
}
