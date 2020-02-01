package com.thanh.room.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.thanh.room.R;
import com.thanh.room.model.room.database.AppDatabase;
import com.thanh.room.ui.fragment.NoteCollectionFragment;
import com.thanh.room.ui.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {
    public AppDatabase mDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDB=AppDatabase.getInstance(this);
        replaceFragment(getSupportFragmentManager(),new NoteCollectionFragment());
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    @Override
    public void onBack() {
        if(getSupportFragmentManager().getBackStackEntryCount()>1){
            popFragment(getSupportFragmentManager());
            return;
        }
        this.finish();
    }

    @Override
    public void replaceFragment(FragmentManager manager, Fragment fragment) {
        manager.beginTransaction().setCustomAnimations(0,0,0,0)
                .replace(R.id.main_container,fragment).commit();
    }

    @Override
    public void popFragment(FragmentManager fragmentManager) {
        fragmentManager.popBackStack();
    }
}
