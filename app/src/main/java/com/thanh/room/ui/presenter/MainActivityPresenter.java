package com.thanh.room.ui.presenter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public interface MainActivityPresenter {
    interface View{
        void onBack();
        void replaceFragment(FragmentManager manager,Fragment fragment);
        void popFragment(FragmentManager fragmentManager);
    }
}
