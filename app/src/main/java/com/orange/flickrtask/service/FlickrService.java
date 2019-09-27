package com.orange.flickrtask.service;

import com.orange.flickrtask.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {
    @GET("/services/rest/?method=flickr.photos.getRecent&format=json&nojsoncallback=1")
    Call<Example> getRecentPhotos(@Query("api_key") String apiKey);

    @GET("/services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    Call<Example> getSearchedPhotos(@Query("api_key") String apiKey, @Query("text") String searchText);
}
