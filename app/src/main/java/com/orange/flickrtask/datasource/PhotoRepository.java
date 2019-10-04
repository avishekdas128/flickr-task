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
        new PhotoRepository.ViewAsyncTask(photoDatabase.getPhotoDAO(),photos).execute();
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

    private static class ViewAsyncTask extends AsyncTask<Void, Void, Void> {

        private PhotoDAO mAsyncTaskDao;
        private List<Photo> photo;

        ViewAsyncTask(PhotoDAO dao, List<Photo> photo) {
            mAsyncTaskDao = dao;
            this.photo = photo;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            photo = mAsyncTaskDao.getPhotos();
            return  null;
        }
    }
}
