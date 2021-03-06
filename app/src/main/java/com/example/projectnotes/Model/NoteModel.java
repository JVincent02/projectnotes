package com.example.projectnotes.Model;

import java.util.ArrayList;
import java.util.List;

public class NoteModel {

    String title;
    List<LineModel> lines;

    public NoteModel(String t, List<LineModel> ls) {
        title = t;
        lines = ls;
    }
    public NoteModel(){

    }

    public String getTitle() {
        return title;
    }

    public List<LineModel> getLines() {
        return lines;
    }

    public static List<NoteModel> getSampleNotes() {
        List<NoteModel> newNoteModel = new ArrayList<NoteModel>();
        newNoteModel.add(new NoteModel("Home", LineModel.getSampleLines()));
        newNoteModel.add(new NoteModel("First Note", LineModel.getSampleLines2()));
/*        newNoteModel.add(new NoteModel("Test2", null));
        newNoteModel.add(new NoteModel("Test3", null));
        newNoteModel.add(new NoteModel("Test4", null));*/
        return newNoteModel;
    }
}
