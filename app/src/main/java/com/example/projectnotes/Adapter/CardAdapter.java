package com.example.projectnotes.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnotes.Model.CardModel;
import com.example.projectnotes.Model.NoteModel;
import com.example.projectnotes.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<CardModel> cardModelList;


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cardAnswerTV;
        TextView cardQuestionTV;
        EasyFlipView cardFlipView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardAnswerTV = itemView.findViewById(R.id.cardAnswerTV);
            cardQuestionTV = itemView.findViewById(R.id.cardQuestionTV);
            cardFlipView = itemView.findViewById(R.id.cardFlipView);
        }
    }

    public CardAdapter(Fragment fragment, List<CardModel> cardModelList) {
        this.cardModelList = cardModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cardView = inflater.inflate(R.layout.adapter_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardModel c = cardModelList.get(position);
        holder.cardQuestionTV.setText(c.getQuestion());
        holder.cardAnswerTV.setText(c.getAnswer());
        holder.cardFlipView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_DOWN == event.getAction()) {
                    return true;
                }else if (MotionEvent.ACTION_UP == event.getAction()){
                    holder.cardFlipView.flipTheView();
                }
                Log.v("event",String.valueOf(event.getAction()));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardModelList.size();
    }

}


