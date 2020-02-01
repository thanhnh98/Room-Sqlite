package com.thanh.room.model.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.thanh.room.model.NoteModel;
import com.thanh.room.model.room.dao.NoteDao;

@Database(version = 1,entities = {NoteModel.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase=null;
    private static Context context;
    private static String DB_NAME="NOTE";
    public abstract NoteDao getNoteDao();
    public static AppDatabase getInstance(Context context){
        AppDatabase.context = context;
        if(appDatabase==null){
            init();
        }
        return appDatabase;
    }

    private static void init() {
        appDatabase= Room.databaseBuilder(context,AppDatabase.class,DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }
}
