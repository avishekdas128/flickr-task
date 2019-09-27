package com.orange.flickrtask.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.orange.flickrtask.model.Photo;
import com.orange.flickrtask.model.PhotoRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private PhotoRepository photoRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        photoRepository = new PhotoRepository(application);
    }

    public LiveData<List<Photo>> getAllPhotos(){
        return photoRepository.getMutableLiveData();
    }
}
