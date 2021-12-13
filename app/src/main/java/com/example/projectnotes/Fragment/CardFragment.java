package com.example.projectnotes.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class CardFragment extends Fragment implements NoteAdapter.NoteAdapterListener, View.OnClickListener {

    NoteAdapter noteAdapter;
    RecyclerView cardListRV;
    RecyclerView cardsRV;
    List<NoteModel> noteModels;
    CardAdapter cardAdapter;
    MainActivity mainActivity;
    View cardBackBtn;

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
        mainActivity = (MainActivity)getActivity();
        cardListRV = root.findViewById(R.id.cardListRV);
        cardsRV = root.findViewById(R.id.cardsRV);
        cardBackBtn = root.findViewById(R.id.cardBackBtn);
        //cardListRV.setAdapter(noteAdapter);
        noteModels = mainActivity.getNoteModels();
        noteAdapter= new NoteAdapter(this,noteModels,1);
        cardListRV.setAdapter(noteAdapter);
        cardListRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        cardBackBtn.setOnClickListener(this);
        //setCardView();

        return root;

    }

    private boolean setCardView(int pos){
        List<CardModel> cardModels = CardModel.getCardModelFromNoteModel(noteModels.get(pos));
        Log.wtf("emptyba",String.valueOf(cardModels.size()));
        cardAdapter = new CardAdapter(this,cardModels );
        cardsRV.setAdapter(cardAdapter);
        cardsRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return !cardModels.isEmpty();
    }


    @Override
    public void onNoteTitleClicked(int pos) {
        if(setCardView(pos)) {
            cardListRV.setVisibility(View.GONE);
            cardsRV.setVisibility(View.VISIBLE);
            cardBackBtn.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(mainActivity, "No definitions found on "+noteModels.get(pos).getTitle()+".", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNoteDeleteClicked(int pos) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardBackBtn:
                cardListRV.setVisibility(View.VISIBLE);
                cardsRV.setVisibility(View.GONE);
                cardBackBtn.setVisibility(View.GONE);
                break;
        }
    }
}
