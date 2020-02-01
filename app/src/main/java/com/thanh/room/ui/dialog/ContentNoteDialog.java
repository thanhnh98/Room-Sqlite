package com.thanh.room.ui.dialog;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.thanh.room.R;
import com.thanh.room.model.NoteModel;
import com.thanh.room.ui.dialog.listener.ContentDialogListener;
import com.thanh.room.ui.dialog.listener.impl.ContentDialogListenerImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContentNoteDialog extends DialogFragment {
    private static Context context;
    private static NoteModel noteModel;
    private static ContentDialogListener.onClick onClick;
    private ContentDialogListener listener;
    private EditText edt_title;
    private EditText edt_content;
    NoteModel noteModelNew = new NoteModel();
    public static ContentNoteDialog getIntances(Context context, NoteModel noteModel, ContentDialogListener.onClick onClick){

        ContentNoteDialog.context = context;
        ContentNoteDialog.noteModel = noteModel;
        ContentNoteDialog.onClick = onClick;

        return new ContentNoteDialog();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener=new ContentDialogListenerImpl(context,onClick);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_content_note,container,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_content=view.findViewById(R.id.edt_content);
        edt_title=view.findViewById(R.id.edt_title);
        if(noteModel!=null){
            noteModelNew=noteModel;
            edt_title.setText(noteModelNew.getTitle()!=null?noteModelNew.getTitle():"");
            edt_content.setText(noteModelNew.getDescription()!=null?noteModelNew.getDescription():"");
        }

        view.findViewById(R.id.btn_done).setOnClickListener(v->{
            noteModelNew.setAuth("Thanh");
            noteModelNew.setTitle(edt_title.getText().toString().trim());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            noteModelNew.setCreateAt(dtf.format(now));
            noteModelNew.setDescription(edt_content.getText().toString().trim());
            noteModelNew.setStatus(0);
            listener.onClickImpl(noteModel);
            dismiss();
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(v->{
            dismiss();
        });
    }
}
