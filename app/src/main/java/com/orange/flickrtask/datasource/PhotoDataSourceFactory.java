package com.orange.flickrtask.datasource;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.orange.flickrtask.service.FlickrService;

public class PhotoDataSourceFactory extends DataSource.Factory {

    private PhotoDataSource photoDataSource;
    private FlickrService flickrService;
    private Application application;
    private MutableLiveData<PhotoDataSource> mutableLiveData;

    public PhotoDataSourceFactory(FlickrService flickrService, Application application) {
        this.flickrService = flickrService;
        this.application = application;
        mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        photoDataSource = new PhotoDataSource(flickrService,application);
        mutableLiveData.postValue(photoDataSource);
        return photoDataSource;
    }

    public MutableLiveData<PhotoDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
