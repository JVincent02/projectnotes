package com.example.projectnotes;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectnotes.Adapter.SectionsAdapter;
import com.example.projectnotes.Fragment.NoteFragment;
import com.example.projectnotes.Model.NoteModel;
import com.example.projectnotes.Utils.LockableViewPager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteFragment.NoteFragmentListener, View.OnClickListener {

    SectionsAdapter sectionsAdapter;
    LockableViewPager viewPager;
    View tabCon;
    View cardTab;
    View noteTab;
    View alarmTab;

    List<NoteModel> notemodels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        notemodels = NoteModel.getSampleNotes();

        viewPager = findViewById(R.id.sectionVP);
        tabCon = findViewById(R.id.tabCon);
        cardTab = findViewById(R.id.cardTab);
        noteTab = findViewById(R.id.noteTab);
        alarmTab = findViewById(R.id.alarmTab);

        cardTab.setOnClickListener(this);
        noteTab.setOnClickListener(this);
        alarmTab.setOnClickListener(this);

        viewPager.setSwipeable(false);
        sectionsAdapter = new SectionsAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(sectionsAdapter);
        viewPager.setCurrentItem(1);

    }

    @Override
    public void onEditTextClicked() {
        tabCon.setVisibility(View.GONE);
    }

    @Override
    public void onKeyboardRelease() {
        tabCon.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.noteTab:
                viewPager.setCurrentItem(1);
                break;
            case R.id.alarmTab:
                viewPager.setCurrentItem(2);
                break;
            case R.id.cardTab:
                viewPager.setCurrentItem(0);
                break;
        }
    }
    public List<NoteModel> getNoteModels(){
        return notemodels;
    }
}