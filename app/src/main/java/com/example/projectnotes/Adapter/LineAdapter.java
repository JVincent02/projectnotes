package com.example.projectnotes.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnotes.Fragment.NoteFragment;
import com.example.projectnotes.Model.LineModel;
import com.example.projectnotes.R;
import com.example.projectnotes.Utils.StringBitmapUtil;

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
        public ImageView lineIV;

        public ViewHolder(View itemView) {
            super(itemView);
            lineTV = itemView.findViewById(R.id.lineTV);
            lineIV = itemView.findViewById(R.id.lineIV);
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
        if(context.getFabStatus()&&!lineModelList.get(holder.getAdapterPosition()).getType().equals("image")) {
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
        if(!lineModelList.get(holder.getAdapterPosition()).getType().equals("image")) {
            holder.lineTV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEvent.ACTION_UP == event.getAction()) {
                        context.onEditTextClicked(v, holder.content, holder.getAdapterPosition());
                        Log.v("clicky", "clicked");
                    }
                    return false;
                }
            });
            holder.lineTV.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    lineModel.setContent(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
/*            holder.lineTV.addTextChangedListener(new TextWatcher() {
                String prev = holder.lineTV.getText().toString();
                Spannable spannable = new SpannableStringBuilder(holder.lineTV.getText());
                //int pcursor = holder.lineTV.getSelectionStart();
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    //lineModel.setContent(holder.lineTV.getText().toString());
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!prev.equals(s.toString())){


                    int direction =s.toString().length()-prev.length();
                    prev = s.toString();
                    if(direction>0) {
                        //Log.wtf("clicked",s.charAt(s.length()-1)+","+(s.toString().length()-1)+","+s.toString().length()+","+start+","+before+","+count);
                        boolean[] keyboardStates = context.getKeyboardStates();
                        Spannable newLetter = new SpannableStringBuilder(String.valueOf(s.charAt(s.length()-1)));
                        if (keyboardStates[1]) {
                            newLetter.setSpan(new StyleSpan(Typeface.BOLD), 0, newLetter.length(), 0);
                        }
                        Spannable newText = new SpannableStringBuilder(TextUtils.concat(spannable,newLetter));
                        spannable=newText;
                        holder.lineTV.setText(newText);
                        //holder.lineTV.invalidate();
                        holder.lineTV.setSelection(newText.length());

                    }
                    //pcursor = holder.lineTV.getSelectionStart();
                }

                    //holder.lineTV.setText(spannable);
                    //lineModel.setContent(holder.lineTV.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });*/
        }
    }

    @Override
    public int getItemCount() {
        return lineModelList.size();
    }


    public void populateLine(LineModel lineModel, ViewHolder holder){
        EditText lineTV = holder.lineTV;
        ImageView lineIV = holder.lineIV;
        View lineCon = holder.lineCon;
        String[] type = lineModel.getType().split(",");

        if(type[0].equals("header")){
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
        }else if(type[0].equals("image")){
            lineTV.setVisibility(View.GONE);
            lineIV.setVisibility(View.VISIBLE);
            lineIV.setImageBitmap(StringBitmapUtil.getBitmapFromString(lineModel.getImageString()));
        }else if(type[0].equals("content")){
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
        }else if(type[0].equals("subheader")){
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
        }else if(type[0].equals("definition")){
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
        switchTextType(lineTV,type);

    }
    private void switchTextType(EditText lineTV,String[] type){
        lineTV.setBackgroundColor(Color.WHITE);
        if(type.length>1){
            switch (Integer.parseInt(type[1])){
                case 1:
                    lineTV.setTypeface(null,Typeface.BOLD);
                    break;
                case 0:
                    lineTV.setTypeface(null,Typeface.ITALIC);
                    break;
                case 2:
                    lineTV.setBackgroundColor(Color.YELLOW);
                    break;
                case 3:
                    lineTV.setTypeface(null,Typeface.BOLD_ITALIC);
                    break;
                case 4:
                    lineTV.setTypeface(null,Typeface.ITALIC);
                    lineTV.setBackgroundColor(Color.YELLOW);
                    break;
                case 5:
                    lineTV.setTypeface(null,Typeface.BOLD);
                    lineTV.setBackgroundColor(Color.YELLOW);
                    break;
                case 6:
                    lineTV.setTypeface(null,Typeface.BOLD_ITALIC);
                    lineTV.setBackgroundColor(Color.YELLOW);
                    break;
            }
        }
        lineTV.setSelection(lineTV.getText().length());
    }
    public interface LineAdapterListener{
        void onEditTextClicked(View v,String[] content,int pos);
        boolean getFabStatus();
        void onRequestFocus(View v,String[] content);
    }

}
