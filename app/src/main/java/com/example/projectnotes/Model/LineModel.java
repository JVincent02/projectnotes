package com.example.projectnotes.Model;

import java.util.ArrayList;
import java.util.List;

public class LineModel {
    int line;
    String type;
    String content;

    public LineModel(String type,String content){
        this.type = type;
        this.content = content;
    }

    public String getType(){
        return this.type;
    }
    public String getContent(){
        return this.content;
    }

    public static List<LineModel> getSampleLines(){
        List<LineModel> newLineModel = new ArrayList<LineModel>();
        newLineModel.add(new LineModel("header","Getting Started on Mobile"));
        newLineModel.add(new LineModel("content","Welcome to Project Notes!"));
        newLineModel.add(new LineModel("content",""));
        newLineModel.add(new LineModel("content","Tap anywhere and start typing"));
        newLineModel.add(new LineModel("content","Drag sections to reorder"));
        return newLineModel;
    }
}
