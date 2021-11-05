package com.example.projectnotes.Model;

import java.util.ArrayList;
import java.util.List;

public class CardModel {
    String question;
    String answer;

    public CardModel(String question,String answer){
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion(){
        return question;
    }
    public String getAnswer(){
        return answer;
    }

    public static List<CardModel> getSampleCards(){
        List<CardModel> cardModels = new ArrayList<CardModel>();
        cardModels.add(new CardModel("Is an elementary particle and a fundamental constituent of matter.","Quark"));
        cardModels.add(new CardModel("is the change in frequency of a wave in relation to an observer who is moving relative to the wave source.","Doppler Effect"));
        cardModels.add(new CardModel(" is a physical phenomenon that occurs when a group of particles are generated, interact, or share spatial proximity in a way such that the quantum state of each particle of the group cannot be described independently of the state of the others, including when the particles are separated by a large distance.","Quantum Entanglement"));
        cardModels.add(new CardModel("is a zone of energetic charged particles, most of which originate from the solar wind, that are captured by and held around a planet by that planet's magnetosphere.","Van Allen radiation belt"));
        cardModels.add(new CardModel(" is a method of curve fitting using linear polynomials to construct new data points within the range of a discrete set of known data points.","Linear Interpolation"));
        cardModels.add(new CardModel(" is an electrophysiological monitoring method to record electrical activity on the scalp that has been shown to represent the macroscopic activity of the surface layer of the brain underneath.","Electroencephalography"));
        return cardModels;

    }

    public static List<CardModel> getCardModelFromNoteModel(NoteModel noteModel){
        List<LineModel> lines = noteModel.getLines();
        List<CardModel> cardModels = new ArrayList<CardModel>();

        for(int i =0;i<lines.size();i++){
            if(lines.get(i).getType()=="definition"){
                String[] qa = lines.get(i).getContent().split(",");
                cardModels.add(new CardModel(qa[1],qa[0]));
            }
        }
        return cardModels;
    }

}
