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

    public SearchDataSourceFactory(FlickrService flickrService, Application application) {
        this.flickrService = flickrService;
        this.application = application;
        mutableLiveData = new MutableLiveData<>();
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    @Override
    public DataSource create() {
        searchDataSource = new SearchDataSource(flickrService,application,getSearchText());
        mutableLiveData.postValue(searchDataSource);
        return searchDataSource;
    }

    public MutableLiveData<SearchDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
