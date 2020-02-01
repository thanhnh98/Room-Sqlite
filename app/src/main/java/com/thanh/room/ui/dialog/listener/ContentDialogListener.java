package com.thanh.room.ui.dialog.listener;

import com.thanh.room.model.NoteModel;

public interface ContentDialogListener {
    void onClickImpl(NoteModel noteModel);
    interface onClick{
        void onClick(NoteModel note);
    }
}
