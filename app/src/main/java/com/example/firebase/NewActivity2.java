package com.example.firebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

public class NewActivity2 extends AppCompatActivity {

    TextView name,email,phone;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new2);

        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        String userId=firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference= firebaseFirestore.collection("users").document(userId);
        documentReference.addSnapshotListener( this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString("name"));
                email.setText(documentSnapshot.getString("email id"));
                phone.setText(documentSnapshot.getString("phone"));
            }
        });
    }
}