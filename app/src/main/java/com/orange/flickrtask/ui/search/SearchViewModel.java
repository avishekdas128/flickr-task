package com.orange.flickrtask.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.orange.flickrtask.model.Photo;
import com.orange.flickrtask.model.SearchRepository;

import java.util.List;

public class SearchViewModel extends AndroidViewModel  {

    private SearchRepository searchRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        searchRepository = new SearchRepository(application);
    }

    public LiveData<List<Photo>> getAllSearchedPhotos(String text){
        return searchRepository.getMutableLiveData(text);
    }
}
