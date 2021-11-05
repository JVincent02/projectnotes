package com.example.projectnotes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnotes.Model.NoteModel;
import com.example.projectnotes.R;

import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<NoteModel> noteModelList;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView noteTitleTV;

        public ViewHolder(View itemView){
            super(itemView);
            noteTitleTV = itemView.findViewById(R.id.noteTitleTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(getAdapterPosition());
        }
    }
    public NoteAdapter(List<NoteModel> noteModelList){
        this.noteModelList = noteModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.adapter_note, parent, false);
        ViewHolder viewHolder = new ViewHolder(noteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteModel noteModel = noteModelList.get(position);
        TextView noteTitleTV = holder.noteTitleTV;
        noteTitleTV.setText(noteModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }

}
