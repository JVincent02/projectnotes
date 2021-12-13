package com.example.projectnotes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnotes.Model.NoteModel;
import com.example.projectnotes.R;

import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<NoteModel> noteModelList;
    NoteAdapterListener noteAdapterListener;
    int flag=0;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView noteTitleTV;
        View noteTitleCon;
        View noteDeleteBtn;
        public ViewHolder(View itemView){
            super(itemView);
            noteTitleTV = itemView.findViewById(R.id.noteTitleTV);
            noteTitleCon = itemView.findViewById(R.id.noteTitleCon);
            noteDeleteBtn = itemView.findViewById(R.id.noteDeleteBtn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(getAdapterPosition());
        }
    }
    public NoteAdapter(Fragment fragment, List<NoteModel> noteModelList,int flag){
        this.noteModelList = noteModelList;
        noteAdapterListener = (NoteAdapterListener) fragment;
        this.flag = flag;
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
        if(flag==0) {
            if (position == 0) {
                holder.noteDeleteBtn.setVisibility(View.GONE);
            } else {
                holder.noteDeleteBtn.setVisibility(View.VISIBLE);
            }
        }else{
            holder.noteDeleteBtn.setVisibility(View.GONE);
        }
        holder.noteTitleCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteAdapterListener.onNoteTitleClicked(holder.getAdapterPosition());
            }
        });
        holder.noteDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteAdapterListener.onNoteDeleteClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }

    public interface NoteAdapterListener{
        void onNoteTitleClicked(int pos);
        void onNoteDeleteClicked(int pos);
    }
}
