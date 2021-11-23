package com.example.projectnotes.Model;

import java.util.List;

public class UserModel {
    String username;
    List<NoteModel> notes;

    public UserModel(String username,List<NoteModel> notes){
        this.username=username;
        this.notes=notes;
    }
}
