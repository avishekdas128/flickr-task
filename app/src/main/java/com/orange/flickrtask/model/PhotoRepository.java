package com.orange.flickrtask.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.orange.flickrtask.R;
import com.orange.flickrtask.service.FlickrService;
import com.orange.flickrtask.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoRepository {

    private ArrayList<Photo> movies=new ArrayList<>();
    private MutableLiveData<List<Photo>> mutableLiveData=new MutableLiveData<>();
    private Application application;

    public PhotoRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Photo>> getMutableLiveData() {
        final FlickrService movieDataService = RetrofitInstance.getService();
        Call<Example> call = movieDataService.getRecentPhotos(application.getApplicationContext().getString(R.string.api_key));
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example example = response.body();
                PhotoDBResponse photoDBResponse = example.getPhotos();
                if (photoDBResponse != null && photoDBResponse.getPhoto() != null) {
                    movies = (ArrayList<Photo>) photoDBResponse.getPhoto();
                    mutableLiveData.setValue(movies);
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
            }
        });
        return mutableLiveData;
    }
}
