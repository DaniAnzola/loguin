package com.example.loguin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Recuperar extends AppCompatActivity {
private EditText etCorreO;
private Button btnEnviar;
    private FirebaseAuth mAuth;
private String correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        mAuth=FirebaseAuth.getInstance();
        etCorreO=findViewById(R.id.etCorreoO);
        btnEnviar=findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo=etCorreO.getText().toString().trim();
                mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent i=new Intent(Recuperar.this,MainActivity.class);
                            startActivity(i);
                        }

                    }
                });
            }
        });

    }
}