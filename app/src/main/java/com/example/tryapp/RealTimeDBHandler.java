package com.example.tryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RealTimeDBHandler {

    private final Context context;

    public RealTimeDBHandler(Context context)
    {
        this.context = context;
    }

    public String onCreateReference(Object object){
        String returnId = null;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        if(object instanceof SOS)
        {
            ref = ref.child("SOS")
                    .push();
            String key = ref.getKey();
//            Log.d("mytag","matched sos");
            SOS newObject = (SOS) object;
            newObject.setSOSID(key);
            ref.setValue(newObject)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context,"SOS SeNT",Toast.LENGTH_SHORT).show();
                        }
                    });
            returnId = key;
        }
        if(object instanceof User)
        {
            ref = ref.child("USERS");
            User newObject = (User) object;
            String key = FirebaseAuth.getInstance().getCurrentUser().getUid();
            newObject.setUserID(key);
            ref.child(key).setValue(newObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
//                    Log.d("mytag","data saved");
                }
            });
            returnId = key;
        }
        if(object instanceof Child)
        {
//            Log.d("mytag","in chid if");
            ref = ref.child("CHILD")
                    .push();
            String key = ref.getKey();
//            Log.d("mytag","matched user");
            Child newObject = (Child) object;
            newObject.setChildID(key);
            newObject.setUserID(FirebaseAuth.getInstance().getUid());
            ref.setValue(newObject)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context,"data saved",Toast.LENGTH_SHORT).show();
                        }
                    });
            returnId = key;
//            Log.d("mytag","child created");
        }
        if(object instanceof Parent)
        {
            ref = ref.child("PARENT")
                    .push();
            String key = ref.getKey();
//            Log.d("mytag","matched user");
            Parent newObject = (Parent) object;
            newObject.setParentID(key);
            newObject.setUserID(FirebaseAuth.getInstance().getUid());
            ref.setValue(newObject)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context,"data saved",Toast.LENGTH_SHORT).show();
                        }
                    });
            returnId = key;
        }
        return returnId;
    }

    public void updateValueToChild(String path, String id) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
            Map<String, Object> myUpdate = new HashMap<>();
            myUpdate.put("parentID", id);
            ref.updateChildren(myUpdate);

    }

    public void updateValueToParent(String path,String id,String email)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ref.child("childID")
                .child(id)
                .setValue(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("mytag","done");
                    }
                });
    }
}
