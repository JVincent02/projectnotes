package com.example.projectnotes.Utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnotes.Adapter.LineAdapter;
import com.example.projectnotes.Fragment.NoteFragment;
import com.example.projectnotes.R;

import java.util.Collections;

public class NoteContentTouchHelper extends ItemTouchHelper.Callback {

    NoteContentTouchListener noteContentTouchListener;
    NoteFragment context;
    boolean draggable = true;
    boolean swipable = true;

    public void setNoteContentTouchListener(NoteFragment context) {
        this.noteContentTouchListener = (NoteContentTouchListener) context;
        this.context = context;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return draggable;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        LineAdapter.ViewHolder holder = (LineAdapter.ViewHolder) viewHolder;
        holder.lineCon.setBackground(null);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return swipable;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            LineAdapter.ViewHolder holder = (LineAdapter.ViewHolder) viewHolder;
            holder.lineCon.setBackground(context.getResources().getDrawable(R.drawable.rounded_border_gray));
            vibrate(100);
            //holder.lineTV.setKeyListener(null);
            //setViewBackgroundWithoutResettingPadding(holder.lineCon,R.drawable.rounded_border_gray);
        }

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int fromPos = viewHolder.getAdapterPosition();
        int toPos = target.getAdapterPosition();
        //Collections.swap(lineModelList,fromPos,toPos);
        noteContentTouchListener.onItemMove(fromPos, toPos);
        recyclerView.getAdapter().notifyItemMoved(fromPos, toPos);
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Log.v("dir",String.valueOf(direction));
    }

    public static void setViewBackgroundWithoutResettingPadding(final View v, final int backgroundResId) {
        final int paddingBottom = v.getPaddingBottom(), paddingLeft = v.getPaddingLeft();
        final int paddingRight = v.getPaddingRight(), paddingTop = v.getPaddingTop();
        v.setBackgroundResource(backgroundResId);
        v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public void setDraggable(boolean b) {
        draggable = b;
    }
    public void setSwipable(boolean b) {
        swipable = b;
    }

    public void vibrate(int t) {
        Vibrator v = (Vibrator) context.getActivity().getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(t, VibrationEffect.EFFECT_HEAVY_CLICK));
        } else {
            //deprecated in API 26
            v.vibrate(t);
        }
    }

}
