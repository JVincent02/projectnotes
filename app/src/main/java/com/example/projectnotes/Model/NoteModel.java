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

    public String getTitle() {
        return title;
    }

    public List<LineModel> getLines() {
        return lines;
    }

    public static List<NoteModel> getSampleNotes() {
        List<NoteModel> newNoteModel = new ArrayList<NoteModel>();
        newNoteModel.add(new NoteModel("Test1", null));
        newNoteModel.add(new NoteModel("Test2", null));
        newNoteModel.add(new NoteModel("Test3", null));
        newNoteModel.add(new NoteModel("Test4", null));
        return newNoteModel;
    }
}
