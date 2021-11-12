package com.example.projectnotes.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

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

import java.util.Collections;
import java.util.List;

public class NoteFragment extends Fragment implements View.OnClickListener, NoteContentTouchListener, LineAdapter.LineAdapterListener, NoteAdapter.NoteAdapterListener {

    ImageView menuBtn;
    ImageView checkBtn;
    View drawerView;
    //View notesCon;
    View drawerCon;
    View appBar;
    View drawerBgView;

    //For fab buttons
    View addDefinitionBtn;
    View addContentBtn;
    View addHeaderBtn;
    View addSubtitleBtn;
    View fabBtn;

    View addDefCon;
    View confirmBtn;
    View exitBtn;
    EditText termTV;
    EditText definitionTV;

    RecyclerView drawerRV;
    RecyclerView noteContentRV;
    List<LineModel> lineModelList;
    NoteContentTouchHelper noteContentTouchHelper;
    NoteAdapter noteAdapter;
    LineAdapter lineAdapter;
    View focused;
    NoteFragmentListener noteFragmentListener;

    List<NoteModel> noteModels;

    boolean isFabClicked = false;
    boolean editOnly = false;
    int rvpos=-1;

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

        noteModels = ((MainActivity)getActivity()).getNoteModels();

        menuBtn = root.findViewById(R.id.menuBtn);
        checkBtn = root.findViewById(R.id.checkBtn);
        //notesCon = root.findViewById(R.id.notesCon);
        drawerBgView = root.findViewById(R.id.drawerBgView);
        drawerCon = root.findViewById(R.id.drawerCon);
        appBar= root.findViewById(R.id.appBar);
        drawerView = root.findViewById(R.id.drawerView);
        drawerRV= root.findViewById(R.id.drawerRV);
        noteContentRV = root.findViewById(R.id.noteContentRV);
        //for fab buttons
        addDefinitionBtn = root.findViewById(R.id.addDefinitionBtn);
        addContentBtn= root.findViewById(R.id.addContentBtn);
        addHeaderBtn= root.findViewById(R.id.addHeaderBtn);
        addSubtitleBtn= root.findViewById(R.id.addSubtitleBtn);
        fabBtn= root.findViewById(R.id.fabBtn);
        // add def
        addDefCon = root.findViewById(R.id.addDefCon);
        confirmBtn = root.findViewById(R.id.addDefConfirmBtn);
        exitBtn = root.findViewById(R.id.addDefExitBtn);
        termTV = root.findViewById(R.id.termTV);
        definitionTV = root.findViewById(R.id.definitionTV);

        menuBtn.setOnClickListener(this);
        checkBtn.setOnClickListener(this);
        drawerCon.setOnClickListener(this);
        //for fab buttons
        addDefinitionBtn.setOnClickListener(this);
        addHeaderBtn.setOnClickListener(this);
        addContentBtn.setOnClickListener(this);
        addSubtitleBtn.setOnClickListener(this);
        fabBtn.setOnClickListener(this);

        confirmBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);

        //notesCon.setOnClickListener(this);

        noteAdapter = new NoteAdapter(this,noteModels);
        drawerRV.setAdapter(noteAdapter);
        drawerRV.setLayoutManager(new LinearLayoutManager(this.getContext()));

        noteContentTouchHelper = new NoteContentTouchHelper();
        noteContentTouchHelper.setNoteContentTouchListener(this);

        lineModelList = noteModels.get(0).getLines();
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
                    AnimUtil.expand(drawerView,drawerCon,drawerBgView,300, 800);
                clearEditMode();
                    drawerCon.requestFocus();
                break;
            case R.id.drawerCon:

                    AnimUtil.collapse(drawerView,drawerCon,drawerBgView,300, 0);
                break;
            case R.id.checkBtn:
                clearEditMode();
                noteFragmentListener.onKeyboardRelease();
                noteContentTouchHelper.setDraggable(true);
                break;
            //For fab buttons
            case R.id.addContentBtn:
                addLine("content","");
                break;
            case R.id.addDefinitionBtn:
                termTV.setText("");
                definitionTV.setText("");
                addDefCon.setVisibility(View.VISIBLE);
                break;
            case R.id.addHeaderBtn:
                addLine("subheader","");
                break;
            case R.id.addSubtitleBtn:
                addLine("header","");
                break;
            case R.id.fabBtn:
                isFabClicked=true;
                break;
            case R.id.addDefConfirmBtn:
                if(!editOnly) addLine("definition",getDef());
                else editLine();
                addDefCon.setVisibility(View.GONE);
                focused = addDefCon;
                clearEditMode();
                editOnly=false;
                break;
            case R.id.addDefExitBtn:
                addDefCon.setVisibility(View.GONE);
                clearEditMode();
                break;
        }
    }
    private void editLine(){
        lineModelList.get(rvpos).setContent(getDef());
        lineAdapter.notifyItemChanged(rvpos);
    }
    private String getDef(){
        String o="";
        o = termTV.getText().toString()+","+definitionTV.getText().toString();
        termTV.setText("");
        definitionTV.setText("");
        return o;
    }
    private void addLine(String type,String con){
        lineModelList.add(new LineModel(type,con));
        lineAdapter.notifyItemInserted(lineModelList.size() - 1);
        noteContentRV.scrollToPosition(lineModelList.size()-1);
    }
    private void clearEditMode(){
        if (focused != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focused.getWindowToken(), 0);
        }
        noteContentRV.clearFocus();
        checkBtn.setVisibility(View.GONE);
    }
    @Override
    public void onItemMove(int fromPos, int toPos) {
        Collections.swap(lineModelList,fromPos,toPos);
    }

    @Override
    public void onEditTextClicked(View v,String[] content,int pos) {
        Log.v("c","is for clicked");
        rvpos=pos;
        if(content==null) {
            editModeOn(v);
        }
        else{
            editOnly = true;
            addDefCon.setVisibility(View.VISIBLE);
            termTV.setText(content[0]);
            definitionTV.setText(content[1]);
        }

    }

    private void  editModeOn(View v){
        noteFragmentListener.onEditTextClicked();
        noteContentTouchHelper.setDraggable(false);
        checkBtn.setVisibility(View.VISIBLE);
        focused = v;
        Log.v("editmodecleared","yes" );
    }

    @Override
    public boolean getFabStatus() {
        return isFabClicked;
    }

    @Override
    public void onRequestFocus(View v,String[] content) {
        if(content==null) {
            editModeOn(v);
        }
        isFabClicked=false;
    }

    @Override
    public void onNoteTitleClicked(int pos) {

    }

    public interface NoteFragmentListener{
        void onEditTextClicked();
        void onKeyboardRelease();
    }

}
