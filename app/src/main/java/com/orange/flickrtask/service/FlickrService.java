package com.orange.flickrtask.service;

import com.orange.flickrtask.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {
    @GET("/services/rest/?method=flickr.photos.getRecent&format=json&nojsoncallback=1")
    Call<Example> getRecentPhotosWithPaging(@Query("api_key") String apiKey,@Query("page") long page);

    @GET("/services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    Call<Example> getSearchedPhotosWithPaging(@Query("api_key") String apiKey, @Query("text") String searchText, @Query("page") long page);
}
