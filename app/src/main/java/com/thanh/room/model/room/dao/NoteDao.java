package com.thanh.room.model.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thanh.room.model.NoteModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface NoteDao {
    @Insert(onConflict = REPLACE)
    void insertNote(NoteModel noteModel);
    @Query("SELECT * FROM NOTE")
    Flowable<List<NoteModel>> getListNote();
    @Query("DELETE FROM NOTE WHERE NOTE.id=:id")
    Single<Integer> deleteNoteById(int id);
}
