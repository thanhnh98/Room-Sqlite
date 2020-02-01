package com.thanh.room.ui.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.thanh.room.model.NoteModel;
import com.thanh.room.model.room.database.AppDatabase;
import com.thanh.room.ui.presenter.NoteCollectionFragmentPrenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NoteCollectionFragmentPresenterImpl implements NoteCollectionFragmentPrenter {
    private final Context context;
    private final View view;
    private AppDatabase mDB;
    List<NoteModel> listData=new ArrayList<>();

    public NoteCollectionFragmentPresenterImpl(Context context, View view){

        this.context = context;
        this.view = view;
        mDB=AppDatabase.getInstance(context);
    }
    @Override
    public void initDataRecyclerView() {
        //if(mDB.getNoteDao().getListNote()!=null) listData.addAll(mDB.getNoteDao().getListNote());
        mDB.getNoteDao().getListNote().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    //Toast.makeText(context, ""+response.size(), Toast.LENGTH_SHORT).show();
                    view.initAdapter(context,response);
                    view.initRecyclerView();
                });

    }
}
