package com.thanh.room.ui.presenter;

import android.content.Context;

import com.thanh.room.model.NoteModel;

import java.util.List;

public interface NoteCollectionFragmentPrenter {
    void initDataRecyclerView();
    interface View{
        void initAdapter(Context context, List<NoteModel> listData);
        void initRecyclerView();
    }
}
