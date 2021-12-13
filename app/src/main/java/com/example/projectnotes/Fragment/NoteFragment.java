package com.example.projectnotes.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnotes.Adapter.LineAdapter;
import com.example.projectnotes.Adapter.NoteAdapter;
import com.example.projectnotes.LoginActivity;
import com.example.projectnotes.MainActivity;
import com.example.projectnotes.Model.LineModel;
import com.example.projectnotes.Model.NoteModel;
import com.example.projectnotes.R;
import com.example.projectnotes.Utils.AnimUtil;
import com.example.projectnotes.Utils.NoteContentTouchHelper;
import com.example.projectnotes.Utils.NoteContentTouchListener;
import com.example.projectnotes.Utils.StoreImagesUtil;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteFragment extends Fragment implements View.OnClickListener, NoteContentTouchListener, LineAdapter.LineAdapterListener, NoteAdapter.NoteAdapterListener {

    ImageView menuBtn;
    ImageView checkBtn;
    View drawerView;
    //View notesCon;
    View drawerCon;
    View accountBtn;
    View appBar;
    View drawerBgView;
    TextView emailDisplayTV;
    TextView noteDisplayTitleTV;
    //for share
    View shareBtn;
    View shareCon;
    EditText emailShareTV;
    View shareConfirmBtn;
    View shareExitBtn;
    TextView shareNoteTitleTV;
    //for add note
    View addNoteBtn;
    View addNewNoteCon;
    EditText addNewNoteTV;
    View addnewNoteConfirmBtn;
    View addnewNoteExitBtn;
    //For keyboard buttons
    View italicsBtn;
    View boldBtn;
    View underlineBtn;
    View keyboardCon;
    //For fab buttons
    View addDefinitionBtn;
    View addContentBtn;
    View addHeaderBtn;
    View addSubtitleBtn;
    View addImageBtn;
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
    MainActivity mainActivity;
    List<NoteModel> noteModels;

    boolean[] keyboardStates = {false,false,false};
    boolean ready=false;
    boolean isFabClicked = false;
    boolean editOnly = false;
    int rvpos=-1;
    int notepos=0;
    public static final int PICK_IMG = 1;

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
        mainActivity = ((MainActivity)getActivity());
        noteModels = mainActivity.getNoteModels();

        menuBtn = root.findViewById(R.id.menuBtn);
        checkBtn = root.findViewById(R.id.checkBtn);
        //notesCon = root.findViewById(R.id.notesCon);
        drawerBgView = root.findViewById(R.id.drawerBgView);
        drawerCon = root.findViewById(R.id.drawerCon);
        accountBtn = root.findViewById(R.id.accountBtn);
        emailDisplayTV = root.findViewById(R.id.emailDisplayTV);
        appBar= root.findViewById(R.id.appBar);
        drawerView = root.findViewById(R.id.drawerView);
        drawerRV= root.findViewById(R.id.drawerRV);
        noteContentRV = root.findViewById(R.id.noteContentRV);
        noteDisplayTitleTV = root.findViewById(R.id.noteDisplayTitleTV);
        //for share
        shareBtn = root.findViewById(R.id.shareBtn);
        shareCon = root.findViewById(R.id.shareCon);
        shareConfirmBtn = root.findViewById(R.id.shareConfirmBtn);
        shareExitBtn = root.findViewById(R.id.shareExitBtn);
        emailShareTV = root.findViewById(R.id.emailShareTV);
        shareNoteTitleTV = root.findViewById(R.id.shareNoteTitleTV);
        //for add new note
        addNewNoteCon = root.findViewById(R.id.addNewNoteCon);
        addNewNoteTV = root.findViewById(R.id.addNewNoteTV);
        addnewNoteConfirmBtn = root.findViewById(R.id.addNewNoteConfirmBtn);
        addnewNoteExitBtn = root.findViewById(R.id.addNewNoteExitBtn);
        addNoteBtn = root.findViewById(R.id.addNoteBtn);
        //for keyboard buttons
        italicsBtn = root.findViewById(R.id.italicsBtn);
        boldBtn = root.findViewById(R.id.boldBtn);
        underlineBtn = root.findViewById(R.id.underlineBtn);
        keyboardCon = root.findViewById(R.id.keyboardCon);
        //for fab buttons
        addDefinitionBtn = root.findViewById(R.id.addDefinitionBtn);
        addContentBtn= root.findViewById(R.id.addContentBtn);
        addHeaderBtn= root.findViewById(R.id.addHeaderBtn);
        addSubtitleBtn= root.findViewById(R.id.addSubtitleBtn);
        addImageBtn =  root.findViewById(R.id.addImageBtn);
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
        accountBtn.setOnClickListener(this);
        //for share
        shareBtn.setOnClickListener(this);
        shareConfirmBtn.setOnClickListener(this);
        shareExitBtn.setOnClickListener(this);
        //for add new note
        addnewNoteExitBtn.setOnClickListener(this);
        addnewNoteConfirmBtn.setOnClickListener(this);
        addNoteBtn.setOnClickListener(this);
        //for keyboard buttons
        italicsBtn.setOnClickListener(this);
        boldBtn.setOnClickListener(this);
        underlineBtn.setOnClickListener(this);
        //for fab buttons
        addDefinitionBtn.setOnClickListener(this);
        addHeaderBtn.setOnClickListener(this);
        addContentBtn.setOnClickListener(this);
        addSubtitleBtn.setOnClickListener(this);
        addImageBtn.setOnClickListener(this);
        fabBtn.setOnClickListener(this);

        confirmBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);

        //notesCon.setOnClickListener(this);

        noteAdapter = new NoteAdapter(this,noteModels,0);
        drawerRV.setAdapter(noteAdapter);
        drawerRV.setLayoutManager(new LinearLayoutManager(this.getContext()));

        noteContentTouchHelper = new NoteContentTouchHelper();
        noteContentTouchHelper.setNoteContentTouchListener(this);

        lineModelList = noteModels.get(notepos).getLines();

        lineAdapter = new LineAdapter(this,lineModelList);
        noteContentRV.setAdapter(lineAdapter);
        noteContentRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        noteContentRV.setItemViewCacheSize(20);
        ItemTouchHelper contentTouchHelper = new ItemTouchHelper(noteContentTouchHelper);
        contentTouchHelper.attachToRecyclerView(noteContentRV);

        noteFragmentListener = (NoteFragmentListener) getActivity();
        String email = ((MainActivity)getActivity()).getEmail().split("@")[0]+"'s Notes";
        emailDisplayTV.setText(email);
        ready=true;
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
                noteFragmentListener.onDataChanged();
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
            case R.id.addImageBtn:
/*                Bitmap image = BitmapFactory.decodeResource(getContext().getResources(),
                        R.drawable.testbg);
                addImage(image);*/
                imageSelector();
                isFabClicked = false;
                break;
            case R.id.italicsBtn:
                keyboardStates[0]=!keyboardStates[0];
                updateKeys();
                break;
            case R.id.boldBtn:
                keyboardStates[1]=!keyboardStates[1];
                updateKeys();
                break;
            case R.id.underlineBtn:
                keyboardStates[2]=!keyboardStates[2];
                updateKeys();
                break;
            case R.id.shareBtn:
                shareCon.setVisibility(View.VISIBLE);
                shareNoteTitleTV.setText("Share - "+noteModels.get(notepos).getTitle());
                emailShareTV.setText("");
                break;
            case R.id.shareConfirmBtn:
                noteFragmentListener.onSharedNotes(notepos,emailShareTV.getText().toString());
                shareCon.setVisibility(View.GONE);
                break;
            case R.id.shareExitBtn:
                shareCon.setVisibility(View.GONE);
                break;
            case R.id.addNewNoteConfirmBtn:
                if(addNewNoteTV.getText().toString().length()>0) {
                    focused=addNewNoteTV;
                    clearEditMode();
                    noteModels.add(new NoteModel(addNewNoteTV.getText().toString(),new ArrayList<>()));
                    noteFragmentListener.onDataChanged();
                    noteAdapter.notifyItemInserted(noteModels.size()-1);
                    updateNoteFragment(noteModels.size()-1);
                    addNewNoteCon.setVisibility(View.GONE);
                }else{
                    addNewNoteTV.setError("Title cannot be empty");
                }
                break;
            case R.id.addNewNoteExitBtn:
                focused=addNewNoteTV;
                clearEditMode();
                addNewNoteCon.setVisibility(View.GONE);
                break;
            case R.id.addNoteBtn:
                addNewNoteCon.setVisibility(View.VISIBLE);
                addNewNoteTV.setText("");
                break;
            case R.id.accountBtn:
                AlertDialog asksignout = AskSignOut();
                asksignout.show();
                break;
        }
    }

    public void updateNoteFragment(int pos){
        if(ready) {
            noteModels = mainActivity.getNoteModels();
            noteAdapter = new NoteAdapter(this,noteModels,0);
            drawerRV.setAdapter(null);
            drawerRV.setLayoutManager(null);
            drawerRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
            drawerRV.setAdapter(noteAdapter);

            noteContentTouchHelper = new NoteContentTouchHelper();
            noteContentTouchHelper.setNoteContentTouchListener(this);

            lineModelList = noteModels.get(pos).getLines();
            notepos = pos;
            lineAdapter = new LineAdapter(this,lineModelList);
            noteContentRV.setAdapter(null);
            noteContentRV.setLayoutManager(null);
            noteContentRV.setAdapter(lineAdapter);
            noteContentRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
            //noteContentRV.setItemViewCacheSize(30);
            //ItemTouchHelper contentTouchHelper = new ItemTouchHelper(noteContentTouchHelper);
            //contentTouchHelper.attachToRecyclerView(noteContentRV);

            noteFragmentListener = (NoteFragmentListener) getActivity();
/*            String email = ((MainActivity)getActivity()).getEmail().split("@")[0]+"'s Notes";
            emailDisplayTV.setText(email);*/

            noteDisplayTitleTV.setText(noteModels.get(pos).getTitle());
            Log.wtf("etoerror","pumasoknoob");
        }
    }
    private void updateKeys(){
        String s = lineModelList.get(rvpos).getType().split(",")[0];
        if(keyboardStates[1]&&keyboardStates[2]&&keyboardStates[0]) {
            lineModelList.get(rvpos).setType(s + ",6");
        }else if(keyboardStates[0]&&keyboardStates[1]){
            lineModelList.get(rvpos).setType(s + ",3");
        }else if(keyboardStates[1]&&keyboardStates[2]){
            lineModelList.get(rvpos).setType(s + ",5");
        }else if(keyboardStates[0]&&keyboardStates[2]){
            lineModelList.get(rvpos).setType(s + ",4");
        }else if(keyboardStates[0]){
            lineModelList.get(rvpos).setType(s + ",0");
        }else if(keyboardStates[1]){
            lineModelList.get(rvpos).setType(s + ",1");
        }else if(keyboardStates[2]){
            lineModelList.get(rvpos).setType(s + ",2");
        } else{
            lineModelList.get(rvpos).setType(s);
            lineAdapter.notifyItemChanged(rvpos);
        }
        updateKeyColor();
        Log.wtf("keybstate",lineModelList.get(rvpos).getType());
        lineAdapter.notifyItemChanged(rvpos);
    }
    private void updateKeyColor(){
        if(keyboardStates[0]){
            italicsBtn.setBackground(getResources().getDrawable(R.color.pastel_blue));
        }else{
            italicsBtn.setBackground(getResources().getDrawable(R.color.white));
        }
        if(keyboardStates[1]){
            boldBtn.setBackground(getResources().getDrawable(R.color.pastel_blue));
        }else{
            boldBtn.setBackground(getResources().getDrawable(R.color.white));
        }
        if(keyboardStates[2]){
            underlineBtn.setBackground(getResources().getDrawable(R.color.pastel_blue));
        }else{
            underlineBtn.setBackground(getResources().getDrawable(R.color.white));
        }
    }
    public boolean[] getKeyboardStates(){
        return keyboardStates;
    }
    private void imageSelector(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMG);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(inputStream);
                addImage(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

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
        noteFragmentListener.onDataChanged();
        lineAdapter.notifyItemInserted(lineModelList.size() - 1);
        noteContentRV.scrollToPosition(lineModelList.size()-1);
    }
    private void addImage(Bitmap img){
        lineModelList.add(new LineModel(StoreImagesUtil.saveImage(mainActivity,mainActivity.getUid(),img)));
        noteFragmentListener.onDataChanged();
        lineAdapter.notifyItemInserted(lineModelList.size() - 1);
        noteContentRV.scrollToPosition(lineModelList.size()-1);
        clearEditMode();
    }
    private void clearEditMode(){
        if (focused != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focused.getWindowToken(), 0);
            //lineAdapter.ge
        }
        noteContentRV.clearFocus();
        checkBtn.setVisibility(View.GONE);
        keyboardCon.setVisibility(View.GONE);
    }
    @Override
    public void onItemMove(int fromPos, int toPos) {
        Collections.swap(lineModelList,fromPos,toPos);
        lineAdapter.notifyItemMoved(fromPos,toPos);
        noteFragmentListener.onDataChanged();
    }

    @Override
    public void onItemSwiped(int pos) {
        String key = lineModelList.get(pos).getImgKey();
        if(key!=null){
            StoreImagesUtil.removeImage(mainActivity,mainActivity.getUid(),key);
        }
        lineModelList.remove(pos);
        lineAdapter.notifyItemRemoved(pos);
        noteFragmentListener.onDataChanged();
    }
    private void updateKeyStates(){
        String[] li = lineModelList.get(rvpos).getType().split(",");
        int n=-1;
        if(li.length>1) n = Integer.parseInt(li[1]);
        if(n==0||n==3||n==4||n==6) keyboardStates[0]=true;
        else keyboardStates[0]=false;
        if(n==1||n==3||n==5||n==6) keyboardStates[1]=true;
        else keyboardStates[1]=false;
        if(n==2||n==4||n==5||n==6) keyboardStates[2]=true;
        else keyboardStates[2]=false;
    }
    @Override
    public void onEditTextClicked(View v,String[] content,int pos) {
        Log.v("c","is for clicked");
        Log.wtf("whatisclicked","edittextclickeddaw");
        rvpos=pos;
        updateKeyStates();
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
        keyboardCon.setVisibility(View.VISIBLE);
        focused = v;
        updateKeyColor();
        Log.v("editmodecleared","yes" );
    }

    @Override
    public boolean getFabStatus() {
        return isFabClicked;
    }

    @Override
    public void onRequestFocus(View v,String[] content) {
        Log.wtf("whatisclicked","requestfocusdaw");
        if(content==null) {
            editModeOn(v);
        }

        isFabClicked=false;
    }

    @Override
    public void onNoteTitleClicked(int pos) {
        updateNoteFragment(pos);
        AnimUtil.collapse(drawerView,drawerCon,drawerBgView,300, 0);
        Log.wtf("anoerror",String.valueOf(pos));
    }

    @Override
    public void onNoteDeleteClicked(int pos) {
        AlertDialog deleteBox = AskDeleteNote(pos);
        deleteBox.show();
    }
    private AlertDialog AskDeleteNote(int pos)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getContext())
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete - "+noteModels.get(pos).getTitle()+ " ?")
                .setIcon(R.drawable.delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        noteModels.remove(pos);
                        noteAdapter.notifyItemRemoved(pos);
                        dialog.dismiss();
                        noteFragmentListener.onDataChanged();
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    private AlertDialog AskSignOut()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getContext())
                // set message, title, and icon
                .setTitle("Sign Out")
                .setMessage("Do you want to Sign out?")
                .setIcon(R.drawable.logout)
                .setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }
    public interface NoteFragmentListener{
        void onEditTextClicked();
        void onKeyboardRelease();
        void onDataChanged();
        void onSharedNotes(int pos,String email);
    }

}
