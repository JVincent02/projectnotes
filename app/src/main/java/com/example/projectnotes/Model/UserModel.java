package com.example.projectnotes.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserModel {
    String uid;
    String email;
    Date date;
    List<NoteModel> notes;
    List<NoteModel> sharedNotes = new ArrayList<NoteModel>();

    public UserModel(String uid,String email){
        this.uid=uid;
        this.email = email;
    }
    public UserModel(String uid,String email,List<NoteModel>notes){
        this.uid=uid;
        this.notes=notes;
        this.email=email;
    }
    public UserModel(String uid,String email,Date date,List<NoteModel>notes){
        this.uid=uid;
        this.notes=notes;
        this.email=email;
        this.date=date;
    }
    public UserModel(){

    }

    public List<NoteModel> getNotes(){
        return notes;
    }
    public List<NoteModel> getSharedNotes(){
        return sharedNotes;
    }
    public void addNote(NoteModel noteModel){
        notes.add(noteModel);
    }
    public void addNotes(List<NoteModel> noteModels){
        notes.addAll(noteModels);
    }
    public void addSharedNote(NoteModel noteModel){
        sharedNotes.add(noteModel);
    }
    public void clearSharedNotes(){
        sharedNotes = new ArrayList<NoteModel>();
    }
    public String getEmail(){
        return email;
    }
    public String getUid(){
        return uid;
    }
    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }

    public void setNotes(List<NoteModel> notes){
        this.notes = notes;
    }
}
