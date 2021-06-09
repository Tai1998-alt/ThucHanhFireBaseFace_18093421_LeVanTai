package com.example.ungdungfire18093421levantai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    EditText editEmail, editPassword;
    Button btnSignIn;
    TextView txtForget, txtRegister;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtForget = findViewById(R.id.txtForget);
        txtRegister = findViewById(R.id.txtRegister);
        mAuth= FirebaseAuth.getInstance();
        btnSignIn.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSignIn:
                SignIn();
                break;
            case R.id.txtRegister:
                startActivity(new Intent(MainActivity2.this, MainActivity3.class));
               break;

        }
    }
    public void SignIn()
    {
        String email = String.valueOf(editEmail.getText());
        String password = String.valueOf(editPassword.getText());
        if(email.isEmpty())
        {
            editEmail.setError("Not Empty Email!");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editEmail.setError("Invalid Email!");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editEmail.setError("Not Empty Email!");
            editEmail.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           startActivity(new Intent(MainActivity2.this, MainActivity4.class));
                        } else
                            {
                                new AlertDialog.Builder(MainActivity2.this)
                                        .setTitle("Confirm")
                                        .setMessage("Register Failed!")
                                        .setPositiveButton("YES",null).setNegativeButton("NO",null).show();
                            }
                    }
                });
        editEmail.setText("");
        editPassword.setText("");
    }
}