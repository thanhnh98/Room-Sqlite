package com.thanh.room.ui.dialog.listener.impl;

import android.content.Context;

import com.thanh.room.model.NoteModel;
import com.thanh.room.ui.dialog.listener.ContentDialogListener;

public class ContentDialogListenerImpl implements ContentDialogListener {


    private final Context context;
    private final ContentDialogListener.onClick onClick;

    public ContentDialogListenerImpl(Context context, onClick onClick){

        this.context = context;
        this.onClick = onClick;
    }
    @Override
    public void onClickImpl(NoteModel noteModel) {
        onClick.onClick(noteModel);
    }
}
