package com.orange.flickrtask.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Photo.class}, version = 1)
public abstract class PhotoDatabase extends RoomDatabase {

    private static PhotoDatabase INSTANCE;
    private static final Object sLock = new Object();

    public abstract PhotoDAO getPhotoDAO();

    public static PhotoDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context);
            }
            return INSTANCE;
        }
    }

    private static PhotoDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                PhotoDatabase.class,
                "photo.db").build();
    }
}
