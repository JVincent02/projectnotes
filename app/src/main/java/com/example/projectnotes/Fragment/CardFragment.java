package com.example.projectnotes.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnotes.Adapter.CardAdapter;
import com.example.projectnotes.Adapter.NoteAdapter;
import com.example.projectnotes.MainActivity;
import com.example.projectnotes.Model.CardModel;
import com.example.projectnotes.Model.NoteModel;
import com.example.projectnotes.R;

import java.util.List;

public class CardFragment extends Fragment implements NoteAdapter.NoteAdapterListener {

    NoteAdapter noteAdapter;
    RecyclerView cardListRV;
    RecyclerView cardsRV;
    List<NoteModel> noteModels;
    CardAdapter cardAdapter;

    public static CardFragment newInstance() {
        CardFragment fragment = new CardFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(cardAdapter!=null){
            cardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_card, container, false);

        cardListRV = root.findViewById(R.id.cardListRV);
        cardsRV = root.findViewById(R.id.cardsRV);

        //cardListRV.setAdapter(noteAdapter);
        noteModels = ((MainActivity)getActivity()).getNoteModels();
        noteAdapter= new NoteAdapter(this,noteModels);
        cardListRV.setAdapter(noteAdapter);
        cardListRV.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //setCardView();

        return root;

    }

    private void setCardView(int pos){
        cardAdapter = new CardAdapter(this, CardModel.getCardModelFromNoteModel(noteModels.get(pos)));
        cardsRV.setAdapter(cardAdapter);
        cardsRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }


    @Override
    public void onNoteTitleClicked(int pos) {
        cardListRV.setVisibility(View.GONE);
        cardsRV.setVisibility(View.VISIBLE);
        setCardView(pos);
    }
}
