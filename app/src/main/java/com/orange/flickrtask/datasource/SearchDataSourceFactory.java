package com.orange.flickrtask.datasource;


import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.orange.flickrtask.service.FlickrService;

public class SearchDataSourceFactory extends DataSource.Factory {

    private SearchDataSource searchDataSource;
    private String searchText;
    private FlickrService flickrService;
    private Application application;
    private MutableLiveData<SearchDataSource> mutableLiveData;

    public SearchDataSourceFactory(FlickrService flickrService, Application application,String searchText) {
        this.flickrService = flickrService;
        this.application = application;
        this.searchText = searchText;
        mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        searchDataSource = new SearchDataSource(flickrService,application,searchText);
        mutableLiveData.postValue(searchDataSource);
        return searchDataSource;
    }

    public MutableLiveData<SearchDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
