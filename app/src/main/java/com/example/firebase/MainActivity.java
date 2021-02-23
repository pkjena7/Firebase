package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView create_account, already_signup;
    TextInputEditText name, email, password;
    Button button;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create_account = findViewById(R.id.create_account);
        already_signup = findViewById(R.id.already);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        button = findViewById(R.id.button);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        already_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mail = email.getText().toString().trim();
                String passkey = password.getText().toString().trim();
                final String fname = name.getText().toString().trim();
                if (TextUtils.isEmpty(mail)) {
                    email.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(passkey)) {
                    password.setError("password is required");
                    return;
                }
                if (passkey.length() < 6) {
                    password.setError("password must be of 6 character");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(mail, passkey).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("name",fname);
                            user.put("email id",mail);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess:user is created for " +userId);
                                }
                            });
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {

                        }
                    }

                });
            }

        });
    }
}