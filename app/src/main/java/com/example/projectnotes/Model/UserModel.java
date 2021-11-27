package com.example.projectnotes.Model;

import java.util.Date;
import java.util.List;

public class UserModel {
    String uid;
    String email;
    Date date;
    List<NoteModel> notes;

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
    public List<NoteModel> getNotes(){
        return notes;
    }
    public String getEmail(){
        return email;
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
