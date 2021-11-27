package com.example.projectnotes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectnotes.Adapter.SectionsAdapter;
import com.example.projectnotes.Fragment.NoteFragment;
import com.example.projectnotes.Model.NoteModel;
import com.example.projectnotes.Model.UserModel;
import com.example.projectnotes.Utils.KeyboardUtil;
import com.example.projectnotes.Utils.LockableViewPager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteFragment.NoteFragmentListener, View.OnClickListener {

    //SharedPreferences sharedPreferences;
    SectionsAdapter sectionsAdapter;
    LockableViewPager viewPager;
    View tabCon;
    View cardTab;
    View noteTab;
    View alarmTab;
    Gson gson;
    String email;
    String uid;
    UserModel userModel;
    FirebaseDatabase database;
    DatabaseReference userRef;
    //List<NoteModel> notemodels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        uid = bundle.getString("uid");
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        gson = new Gson();
        userModel = readUser();
        updateFirebase();
        //userModel.setNotes(readSavedNotes());

        //writeToFirebase(gson.toJson(userModel));
//        sharedPreferences = getSharedPreferences("notes",MODE_PRIVATE);
//        String notes = sharedPreferences.getString("notes","");
//        if(notes.equals("")){
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            String json = gson.toJson(NoteModel.getSampleNotes());
//            editor.putString("notes",json);
//            notes = json;
//        }
//
//        notemodels = Arrays.asList(new GsonBuilder().create().fromJson(notes, NoteModel[].class));

        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //notemodels = NoteModel.getSampleNotes();

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

    private Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    private void saveUser(UserModel user) {
        user.setDate(getCurrentDate());
        String json = gson.toJson(user);
        try {
            File file = new File(getFilesDir(), "User.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(json);
            bufferedWriter.close();
        } catch (IOException e) {
            Log.e("exception sa io", e.getMessage());
        }
    }

    private void writeToFirebase(String json) {
        userRef.setValue(json);
    }

    private void updateFirebase() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
/*                String snap = snapshot.getValue(String.class);
                UserModel dbUser = gson.fromJson(snap,UserModel.class);
                if(dbUser.getDate().compareTo(userModel.getDate())>0){
                    userModel = dbUser;
                }else if(dbUser.getDate().compareTo(userModel.getDate())<0){
                    writeToFirebase(gson.toJson(userModel));
                }*/
                String snap = snapshot.getValue(String.class);
                UserModel dbUser = gson.fromJson(snap, UserModel.class);
                UserModel svUser = userModel;
                if (dbUser == null) {
                    svUser.setDate(getCurrentDate());
                    writeToFirebase(gson.toJson(svUser));
                }else{
                    if(svUser.getDate()==null){
                        saveUser(dbUser);
                        userModel = dbUser;
                        sectionsAdapter.noteFragment().updateNoteFragment();
                    }else{
                        if(dbUser.getDate().compareTo(svUser.getDate())>0){
                            saveUser(dbUser);
                            userModel = dbUser;
                            sectionsAdapter.noteFragment().updateNoteFragment();
                        }else if(dbUser.getDate().compareTo(svUser.getDate())<0){
                            writeToFirebase(gson.toJson(userModel));
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.wtf("etoerror", error.getMessage());
            }
        });
    }

    public String getEmail() {
        return email;
    }

    private UserModel readUser() {
        String user = "";
        try {
            File file = new File(getFilesDir(), "User.json");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            user = stringBuilder.toString();
        } catch (IOException e) {
            Log.e("error", e.getMessage());
        }
        if (!user.equals(""))
            return gson.fromJson(user, UserModel.class);
        else {
            return createNewUser();
        }
    }

    private UserModel createNewUser() {
        return new UserModel(uid, email, NoteModel.getSampleNotes());
    }

    @Override
    public void onEditTextClicked() {
        tabCon.setVisibility(View.GONE);
    }

    @Override
    public void onKeyboardRelease() {
        Log.wtf("maiact", "release");

        //KeyboardUtil.hideKeyboard(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        tabCon.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDataChanged() {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String json = gson.toJson(notemodels);
//        editor.putString("notes",json);
//        editor.apply();
//        Log.v("main","saved");
        saveUser(userModel);
        updateFirebase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    public List<NoteModel> getNoteModels() {
        return userModel.getNotes();
    }
}