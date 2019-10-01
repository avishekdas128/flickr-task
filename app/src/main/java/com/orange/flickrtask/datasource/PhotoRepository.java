package com.orange.flickrtask.datasource;

import android.app.Application;
import android.os.AsyncTask;

import com.orange.flickrtask.model.Photo;
import com.orange.flickrtask.model.PhotoDAO;
import com.orange.flickrtask.model.PhotoDatabase;

import java.util.List;

public class PhotoRepository {

    private PhotoDatabase photoDatabase;
    List<Photo> photos;

    public PhotoRepository(Application context) {
        photoDatabase = PhotoDatabase.getInstance(context);
        photos = photoDatabase.getPhotoDAO().getPhotos();
    }

    public List<Photo> getMovies() {
        return photos;
    }

    public void insert(Photo photo){
        new PhotoRepository.insertAsyncTask(photoDatabase.getPhotoDAO()).execute(photo);
    }

    private static class insertAsyncTask extends AsyncTask<Photo, Void, Void> {

        private PhotoDAO mAsyncTaskDao;

        insertAsyncTask(PhotoDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Photo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
