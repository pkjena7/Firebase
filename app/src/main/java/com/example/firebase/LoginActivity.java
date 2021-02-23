package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView login;
    TextInputEditText email2,password2;
    Button button2;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.login);
        email2=findViewById(R.id.email_id2);
        password2=findViewById(R.id.password2);
        button2=findViewById(R.id.button2);

        firebaseAuth=FirebaseAuth.getInstance();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email2.getText().toString().trim();
                String passkey = password2.getText().toString().trim();

                if (TextUtils.isEmpty(mail)) {
                    email2.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(passkey)) {
                    password2.setError("password is required");
                    return;
                }
                if (passkey.length() < 6) {
                    password2.setError("password must be of 6 character");
                    return;
                }


                firebaseAuth.signInWithEmailAndPassword(mail, passkey).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(LoginActivity.this, "login unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                Intent intent=new Intent(LoginActivity.this,NewActivity2.class);
                startActivity(intent);
            }
        });

    }
}