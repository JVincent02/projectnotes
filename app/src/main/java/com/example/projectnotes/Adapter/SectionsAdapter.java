package com.example.projectnotes.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.projectnotes.Fragment.AlarmFragment;
import com.example.projectnotes.Fragment.CardFragment;
import com.example.projectnotes.Fragment.NoteFragment;


public class SectionsAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    CardFragment cardFragment;
    NoteFragment noteFragment;
    AlarmFragment alarmFragment;
    public SectionsAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        cardFragment = CardFragment.newInstance();
        noteFragment = NoteFragment.newInstance();
        alarmFragment = AlarmFragment.newInstance();
    }
    public CardFragment cardFragment(){
        return cardFragment;
    }
    public NoteFragment noteFragment(){
        return noteFragment;
    }
    public AlarmFragment alarmFragment(){
        return alarmFragment;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return cardFragment;
            case 1:
                return noteFragment;
            case 2:
                return alarmFragment;
            default:
                return noteFragment;
        }
    }


    @Override
    public int getCount() {
        return 3;
    }

}