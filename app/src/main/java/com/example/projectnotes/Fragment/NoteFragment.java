package com.example.projectnotes.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnotes.Adapter.LineAdapter;
import com.example.projectnotes.Adapter.NoteAdapter;
import com.example.projectnotes.MainActivity;
import com.example.projectnotes.Model.LineModel;
import com.example.projectnotes.Model.NoteModel;
import com.example.projectnotes.R;
import com.example.projectnotes.Utils.AnimUtil;
import com.example.projectnotes.Utils.NoteContentTouchHelper;
import com.example.projectnotes.Utils.NoteContentTouchListener;
import com.example.projectnotes.Utils.RecyclerItemClickListener;

import java.util.Collections;
import java.util.List;

public class NoteFragment extends Fragment implements View.OnClickListener, NoteContentTouchListener, LineAdapter.LineAdapterListener {

    ImageView menuBtn;
    ImageView checkBtn;
    View drawerView;
    View notesCon;
    View appBar;
    RecyclerView drawerRV;
    RecyclerView noteContentRV;
    List<LineModel> lineModelList;
    NoteContentTouchHelper noteContentTouchHelper;
    NoteAdapter noteAdapter;
    LineAdapter lineAdapter;
    View focused;
    NoteFragmentListener noteFragmentListener;

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_note, container, false);

        menuBtn = root.findViewById(R.id.menuBtn);
        checkBtn = root.findViewById(R.id.checkBtn);
        notesCon = root.findViewById(R.id.notesCon);
        appBar= root.findViewById(R.id.appBar);
        drawerView = root.findViewById(R.id.drawerView);
        drawerRV= root.findViewById(R.id.drawerRV);
        noteContentRV = root.findViewById(R.id.noteContentRV);

        menuBtn.setOnClickListener(this);
        checkBtn.setOnClickListener(this);
        notesCon.setOnClickListener(this);

        noteAdapter = new NoteAdapter( NoteModel.getSampleNotes());
        drawerRV.setAdapter(noteAdapter);
        drawerRV.setLayoutManager(new LinearLayoutManager(this.getContext()));

        lineModelList = LineModel.getSampleLines();
        noteContentTouchHelper = new NoteContentTouchHelper();
        noteContentTouchHelper.setNoteContentTouchListener(this);


        lineAdapter = new LineAdapter(this,lineModelList);
        noteContentRV.setAdapter(lineAdapter);
        noteContentRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ItemTouchHelper contentTouchHelper = new ItemTouchHelper(noteContentTouchHelper);
        contentTouchHelper.attachToRecyclerView(noteContentRV);

        noteFragmentListener = (NoteFragmentListener) getActivity();

        return root;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuBtn:
                    //appBar.setAlpha(0.5f);
                    //notesCon.setAlpha(0.5f);
                    AnimUtil.expand(drawerView,notesCon,appBar,300, 800);
                break;
            case R.id.notesCon:
                    AnimUtil.collapse(drawerView,notesCon,appBar,300, 0);
                break;
            case R.id.checkBtn:
                checkBtn.setVisibility(View.GONE);
                if (focused != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(focused.getWindowToken(), 0);
                }
                noteFragmentListener.onKeyboardRelease();
                noteContentRV.clearFocus();
                noteContentTouchHelper.setDraggable(true);
                break;
        }
    }

    @Override
    public void onItemMove(int fromPos, int toPos) {
        Collections.swap(lineModelList,fromPos,toPos);
    }

    @Override
    public void onEditTextClicked(View v) {
        noteFragmentListener.onEditTextClicked();
        noteContentTouchHelper.setDraggable(false);
        checkBtn.setVisibility(View.VISIBLE);
        focused = v;
    }

    public interface NoteFragmentListener{
        void onEditTextClicked();
        void onKeyboardRelease();
    }
}
