package com.example.ungdungfire18093421levantai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaCodec;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {

    EditText editUserName, editEmail, editPasswordTypeNew, editPasswordType;
     Button btnRegister;
    TextView txtSignIn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mAuth = FirebaseAuth.getInstance();
        AnhXa();
        btnRegister.setOnClickListener(this);
        txtSignIn.setOnClickListener(this);

    }
    public  void AnhXa()
    {
        editEmail = findViewById(R.id.editEmail);
        editUserName = findViewById(R.id.editUserName);
        editPasswordType = findViewById(R.id.editPasswordType);
        editPasswordTypeNew = findViewById(R.id.editPasswordTypeNew);
        btnRegister = findViewById(R.id.btnRegister);
        txtSignIn = findViewById(R.id.txtSignIn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnRegister:
                Register();
                break;

            case R.id.txtSignIn:
                startActivity(new Intent(MainActivity3.this, MainActivity2.class));
                break;
        }
    }
    public void Register()
    {
        String userName =String.valueOf(editUserName.getText());
        String email = String.valueOf(editEmail.getText());
        String passwordNew = String.valueOf(editPasswordType.getText());
        String passwordOld = String.valueOf(editPasswordType.getText());

        if(userName.isEmpty())
        {
            editUserName.setError("Please not empty");
            editUserName.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            editEmail.setError("Please not empty");
            editEmail.requestFocus();
            return;
        }
        if(passwordNew.isEmpty())
        {
            editPasswordTypeNew.setError("Please not empty");
            editPasswordTypeNew.requestFocus();
            return;
        }
        if(passwordOld.isEmpty())
        {
            editPasswordType.setError("Please not empty");
            editPasswordType.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editEmail.setError("Invalid Email!");
            editEmail.requestFocus();
            return;
        }
        if(editPasswordType.length()<6)
        {
            editPasswordType.setError("Length minimum is six character!");
            editPasswordType.requestFocus();
            return;
        }
        if(editPasswordTypeNew.length()<6)
        {
            editPasswordTypeNew.setError("Length minimum is six character!");
            editPasswordTypeNew.requestFocus();
            return;
        }
        if(passwordNew.equalsIgnoreCase(passwordOld))
        {
            mAuth.createUserWithEmailAndPassword(email,passwordNew)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                User user = new User(userName,email);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                new AlertDialog.Builder(MainActivity3.this)
                                                        .setTitle("Confirm!")
                                                        .setMessage("Register Successful!")
                                                        .setPositiveButton("YES", null).setNegativeButton("NO", null).show();
                                            }
                                            else
                                                {
                                                    new AlertDialog.Builder(MainActivity3.this)
                                                            .setTitle("Confirm!")
                                                            .setMessage("Register User Failed!")
                                                            .setPositiveButton("YES", null).setNegativeButton("NO", null).show();
                                                }
                                    }
                                });
                            } else
                                {
                                    new AlertDialog.Builder(MainActivity3.this)
                                            .setTitle("Confirm!")
                                            .setMessage("Register Failed!")
                                            .setPositiveButton("YES", null).setNegativeButton("NO", null).show();
                                }
                        }
                    });
        }
        editEmail.setText("");
        editEmail.setText("");
        editPasswordType.setText("");
        editPasswordTypeNew.setText("");
    }
}