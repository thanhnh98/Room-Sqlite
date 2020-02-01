package com.thanh.room.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.thanh.room.R;
import com.thanh.room.model.NoteModel;
import com.thanh.room.model.room.database.AppDatabase;
import com.thanh.room.ui.dialog.ContentNoteDialog;
import com.thanh.room.ui.dialog.listener.ContentDialogListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NoteCollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<NoteModel> listData;
    private AppDatabase mDB;

    public NoteCollectionAdapter(Context context, List<NoteModel> listData){
        this.context = context;
        this.listData = listData;
        mDB=AppDatabase.getInstance(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==listData.size()){
            return new ViewHolderBottom(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_collection_bottom,parent,false));
        }
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_collection,parent,false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(position<listData.size()){
            ViewHolder viewHolder=(ViewHolder)holder;
            viewHolder.tv_title.setText(listData.get(position).getTitle());
            viewHolder.tv_createAt.setText(listData.get(position).getCreateAt());
            viewHolder.tv_des.setText(listData.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return listData.size()+1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements ContentDialogListener.onClick {
        private TextView tv_title;
        private TextView tv_des;
        private TextView tv_createAt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_createAt=itemView.findViewById(R.id.tv_createAt);
            tv_des=itemView.findViewById(R.id.tv_des);
            itemView.setOnClickListener(v->{
                ContentNoteDialog dialog=ContentNoteDialog.getIntances(context,listData.get(getAdapterPosition()),this);
                dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),dialog.getTag());
            });
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(v->{
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Xóa ghi chú")
                        .setMessage("Xác nhận xóa ghi chú \""+listData.get(getAdapterPosition()).getTitle()+"\"?")
                        .setNegativeButton("Quay lại",((dialog, which) -> dialog.dismiss()))
                        .setPositiveButton("Xóa",((dialog, which) -> {
                            mDB.getNoteDao().deleteNoteById(listData.get(getAdapterPosition()).getId()).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(res->{
                                        if(res>0) {
                                            listData.remove(getAdapterPosition()).getId();
                                            notifyItemRemoved(getAdapterPosition());
                                        }else Toast.makeText(context, "Không thể xóa ghi chú này", Toast.LENGTH_SHORT).show();
                                    },throwable -> throwable.printStackTrace());
                        })).show();
                return true;
            });
        }

        @Override
        public void onClick(NoteModel note) {
            mDB.getNoteDao().insertNote(note);
            mDB.getNoteDao().getListNote().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response->{
                       listData.clear();
                       listData.addAll(response);
                       notifyDataSetChanged();
                    });
        }
    }
    public class ViewHolderBottom extends RecyclerView.ViewHolder implements ContentDialogListener.onClick{
        private ViewHolderBottom(@NonNull View view){
            super(view);
            view.setOnClickListener(v->{
                ContentNoteDialog dialog=ContentNoteDialog.getIntances(context,new NoteModel(),this);
                dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),dialog.getTag());
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(NoteModel note) {
            mDB.getNoteDao().insertNote(note);
            listData.add(note);
            notifyItemInserted(listData.size() - 1);
        }
    }
}
