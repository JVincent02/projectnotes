package com.example.projectnotes.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.projectnotes.Fragment.CardFragment;
import com.example.projectnotes.Fragment.NoteFragment;


public class SectionsAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                CardFragment.newInstance();
                break;
            case 1:
                NoteFragment.newInstance();
                break;
            default:
                return NoteFragment.newInstance();
        }
    }


    @Override
    public int getCount() {
        return 3;
    }
}