package com.thanh.room.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thanh.room.R;
import com.thanh.room.model.NoteModel;
import com.thanh.room.model.room.database.AppDatabase;
import com.thanh.room.ui.activity.MainActivity;
import com.thanh.room.ui.adapter.NoteCollectionAdapter;
import com.thanh.room.ui.presenter.NoteCollectionFragmentPrenter;
import com.thanh.room.ui.presenter.impl.NoteCollectionFragmentPresenterImpl;

import java.util.List;

public class NoteCollectionFragment extends Fragment implements NoteCollectionFragmentPrenter.View {
    private AppDatabase mDB;
    private RecyclerView rcv_listNote;
    private NoteCollectionAdapter adapter;
    private NoteCollectionFragmentPrenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new NoteCollectionFragmentPresenterImpl(getActivity(),this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_collection,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_listNote=view.findViewById(R.id.rcv_list_note);
        presenter.initDataRecyclerView();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mDB=((MainActivity)context).mDB;
    }

    @Override
    public void initAdapter(Context context, List<NoteModel> listData) {
        adapter=new NoteCollectionAdapter(context,listData);
    }

    @Override
    public void initRecyclerView() {
        rcv_listNote.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rcv_listNote.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
