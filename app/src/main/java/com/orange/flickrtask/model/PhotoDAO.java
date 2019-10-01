package com.orange.flickrtask.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface PhotoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Photo photo);

    @Transaction
    @Query("SELECT * FROM photo")
    List<Photo> getPhotos();
}
