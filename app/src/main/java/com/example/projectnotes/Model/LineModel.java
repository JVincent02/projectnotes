package com.example.projectnotes.Model;

import android.graphics.Bitmap;

import com.example.projectnotes.Utils.StringBitmapUtil;

import java.util.ArrayList;
import java.util.List;

public class LineModel {
    int line;
    String type;
    String content;
    String bitImg;
    String imgKey;
    //Bitmap img;

    public LineModel(String type,String content){
        this.type = type;
        this.content = content;
    }
    public LineModel(String type,Bitmap img){
        this.type = type;
        //this.img = img;
        this.bitImg = StringBitmapUtil.getStringFromBitmap(img);
    }
    public LineModel(){

    }
    public LineModel(String imgKey){
        type = "image";
        this.imgKey = imgKey;
    }
    public String getImgKey(){
        return imgKey;
    }
    public String getType(){
        return this.type;
    }
    public String getContent(){
        return this.content;
    }
    //public Bitmap getImage(){return this.img;}
    public String getImageString(){return this.bitImg;}

    public void setContent(String content){
        this.content = content;
    }
    public void setType(String type){
        this.type = type;
    }

    public static List<LineModel> getSampleLines(){
        List<LineModel> newLineModel = new ArrayList<LineModel>();
        newLineModel.add(new LineModel("header","Getting Started on Mobile"));
        newLineModel.add(new LineModel("content,0","Welcome to NoteFAS!"));
        newLineModel.add(new LineModel("content","A Note-Taking and Flashcard App with Alarm and Sharing Feature"));
        newLineModel.add(new LineModel("content",""));
        newLineModel.add(new LineModel("content","Tap anywhere and start typing"));
        newLineModel.add(new LineModel("content","Drag sections to reorder"));
        newLineModel.add(new LineModel("subheader","Here are some sample definitions:"));
        newLineModel.add(new LineModel("definition","안녕하세요!,The korean phrase for Good Day!"));
        newLineModel.add(new LineModel("definition","すごい,The japanese word for incredible"));
        newLineModel.add(new LineModel("definition","2021,Is the year this app is created"));
        return newLineModel;
    }
    public static List<LineModel> getSampleLines2(){
        List<LineModel> newLineModel = new ArrayList<LineModel>();
        newLineModel.add(new LineModel("header","Second Page"));
        newLineModel.add(new LineModel("content,0","Teeeeeeeeeeeeeeeeeeeeeeest"));
        return newLineModel;
    }
}
