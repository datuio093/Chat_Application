package com.example.demoapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.demoapp.Fragments.CallsFragment;
import com.example.demoapp.Fragments.ChatsFragment;
import com.example.demoapp.Fragments.ContactsFragment;
import com.example.demoapp.Fragments.StatusFragment;

public class FragmentsAdapter extends FragmentPagerAdapter {
    public FragmentsAdapter (@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
                case 0: return new ChatsFragment();
                case 1: return new ContactsFragment();
                default: return new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        if(position == 0)
        {
            title = "CHATS";
        }

        if(position == 1)
        {
            title = "FRIENDS";
        }
        return title;
    }
}
