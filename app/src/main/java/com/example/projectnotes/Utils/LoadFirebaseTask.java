package com.example.projectnotes.Utils;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projectnotes.Model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;

public class LoadFirebaseTask extends AsyncTask<DatabaseReference, Void, UserModel> {

    UserModel dbUser = null;
    LoadingTaskListener loadingTaskListener;
    @Override
    protected UserModel doInBackground(DatabaseReference... databaseReferences) {
        DatabaseReference ref = databaseReferences[0];
        return getUserModel(ref);
    }

    private UserModel getUserModel(DatabaseReference ref) {
        CountDownLatch done = new CountDownLatch(2);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    dbUser = snapshot.getValue(UserModel.class);
                    done.countDown();
                } catch (DatabaseException e) {
                    Log.wtf("fberror", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        try{
            done.await();
        }catch (InterruptedException e){

        }
        return dbUser;
    }

    @Override
    protected void onPostExecute(UserModel userModel) {
        super.onPostExecute(userModel);
        loadingTaskListener.onDone(userModel);
    }
    public interface LoadingTaskListener{
        void onDone(UserModel userModel);
    }
    public void setLoadingTaskListener(LoadingTaskListener l){
        loadingTaskListener = l;
    }
}
