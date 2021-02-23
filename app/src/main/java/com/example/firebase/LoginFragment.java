package com.example.firebase;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;


public class LoginFragment extends Fragment {

    TextView name,email;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);
         name=view.findViewById(R.id.name);
         email=view.findViewById(R.id.email);

         firebaseAuth=FirebaseAuth.getInstance();
         firebaseFirestore=FirebaseFirestore.getInstance();

         String userId=firebaseAuth.getCurrentUser().getUid();

        final DocumentReference documentReference= firebaseFirestore.collection("users").document(userId);
        documentReference.addSnapshotListener((Executor) this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString("fname"));
                email.setText(documentSnapshot.getString("mail"));
            }
        });
        return view;
    }
}