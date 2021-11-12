package com.example.projectnotes.Adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnotes.Fragment.NoteFragment;
import com.example.projectnotes.Model.LineModel;
import com.example.projectnotes.R;

import java.util.List;

public class LineAdapter extends RecyclerView.Adapter<LineAdapter.ViewHolder> {

    List<LineModel> lineModelList;
    NoteFragment context;
    LinearLayout.LayoutParams lp =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);



    public class ViewHolder extends RecyclerView.ViewHolder {

        public EditText lineTV;
        public View lineCon;
        public String[] content;

        public ViewHolder(View itemView) {
            super(itemView);
            lineTV = itemView.findViewById(R.id.lineTV);
            lineCon = itemView.findViewById(R.id.lineCon);
        }

    }

    public LineAdapter(NoteFragment context,List<LineModel> lineModelList){
        this.context = context;
        this.lineModelList = lineModelList;
    }

    @NonNull
    @Override
    public LineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View lineView = inflater.inflate(R.layout.adapter_line, parent, false);
        LineAdapter.ViewHolder viewHolder = new LineAdapter.ViewHolder(lineView);
        return viewHolder;
    }


    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if(context.getFabStatus()) {
            holder.lineTV.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(holder.lineTV, InputMethodManager.SHOW_IMPLICIT);
            context.onRequestFocus(holder.lineTV,holder.content);
        }
        //context.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Log.v("How many times",context.getFabStatus()?"yes":"no");
    }

    @Override
    public void onBindViewHolder(@NonNull LineAdapter.ViewHolder holder, int position) {
        LineModel lineModel = lineModelList.get(position);
        populateLine(lineModel,holder);
/*        holder.lineTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.onEditTextClicked();
*//*                holder.lineTV.setFocusable(true);
                holder.lineTV.setClickable(true);
                holder.lineTV.requestFocus();*//*
                Log.v("clicky","clicked");
            }
        });*/
        holder.lineTV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()){
                    context.onEditTextClicked(v,holder.content,holder.getAdapterPosition());
                    Log.v("clicky","clicked");
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return lineModelList.size();
    }


    public void populateLine(LineModel lineModel, ViewHolder holder){
        EditText lineTV = holder.lineTV;
        View lineCon = holder.lineCon;

        if(lineModel.getType().equals("header")){
            //lineTV.setTextAppearance(R.style.TextAppearance_AppCompat_HeaderContent);
            TextViewCompat.setTextAppearance(lineTV,R.style.TextAppearance_AppCompat_HeaderContent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                lineTV.setTextCursorDrawable(context.getResources().getDrawable(R.drawable.cursor_header));
            }
            int p0 = (int) context.getResources().getDimension(R.dimen.line_header_paddingleft)/2;
            int p1 = (int) context.getResources().getDimension(R.dimen.line_header_paddingtop)/2;
            int p2 = (int) context.getResources().getDimension(R.dimen.line_header_paddingright)/2;
            int p3 = (int) context.getResources().getDimension(R.dimen.line_header_paddingbottom)/2;
            lineCon.setPadding(p0,p1,p2,p3);
            lp.setMargins(p0,p1,p2,p3);
            lineCon.setLayoutParams(lp);
            lineTV.setText(lineModel.getContent());
        }else if(lineModel.getType().equals("content")){
            //lineTV.setTextAppearance(R.style.TextAppearance_AppCompat_Content);
            TextViewCompat.setTextAppearance(lineTV,R.style.TextAppearance_AppCompat_Content);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                lineTV.setTextCursorDrawable(context.getResources().getDrawable(R.drawable.cursor_content));
            }
            int p0 = (int) context.getResources().getDimension(R.dimen.line_content_paddingleft)/2;
            int p1 = (int) context.getResources().getDimension(R.dimen.line_content_paddingtop)/2;
            int p2 = (int) context.getResources().getDimension(R.dimen.line_content_paddingright)/2;
            int p3 = (int) context.getResources().getDimension(R.dimen.line_content_paddingbottom)/2;
            lineCon.setPadding(p0,p1,p2,p3);
            lp.setMargins(p0,p1,p2,p3);
            lineCon.setLayoutParams(lp);
            lineTV.setText(lineModel.getContent());
        }else if(lineModel.getType().equals("subheader")){
            //lineTV.setTextAppearance(R.style.TextAppearance_AppCompat_Content);
            TextViewCompat.setTextAppearance(lineTV,R.style.TextAppearance_AppCompat_SubHeader);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                lineTV.setTextCursorDrawable(context.getResources().getDrawable(R.drawable.cursor_content));
            }
            int p0 = (int) context.getResources().getDimension(R.dimen.line_subheader_paddingleft)/2;
            int p1 = (int) context.getResources().getDimension(R.dimen.line_subheader_paddingtop)/2;
            int p2 = (int) context.getResources().getDimension(R.dimen.line_subheader_paddingright)/2;
            int p3 = (int) context.getResources().getDimension(R.dimen.line_subheader_paddingbottom)/2;
            lineCon.setPadding(p0,p1,p2,p3);
            lp.setMargins(p0,p1,p2,p3);
            lineCon.setLayoutParams(lp);
            lineTV.setText(lineModel.getContent());
        }else if(lineModel.getType().equals("definition")){
            //lineTV.setTextAppearance(R.style.TextAppearance_AppCompat_Content);
            lineTV.setClickable(false);
            lineTV.setFocusable(false);
            TextViewCompat.setTextAppearance(lineTV,R.style.TextAppearance_AppCompat_Content);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                lineTV.setTextCursorDrawable(context.getResources().getDrawable(R.drawable.cursor_content));
            }
            int p0 = (int) context.getResources().getDimension(R.dimen.line_content_paddingleft)/2;
            int p1 = (int) context.getResources().getDimension(R.dimen.line_content_paddingtop)/2;
            int p2 = (int) context.getResources().getDimension(R.dimen.line_content_paddingright)/2;
            int p3 = (int) context.getResources().getDimension(R.dimen.line_content_paddingbottom)/2;
            lineCon.setPadding(p0,p1,p2,p3);
            lp.setMargins(p0,p1,p2,p3);
            lineCon.setLayoutParams(lp);
            String[] qa = lineModel.getContent().split(",");
            lineTV.setText(qa[0]+": "+qa[1]);
            holder.content = new String[]{qa[0], qa[1]};
        }

    }

    public interface LineAdapterListener{
        void onEditTextClicked(View v,String[] content,int pos);
        boolean getFabStatus();
        void onRequestFocus(View v,String[] content);
    }

}
