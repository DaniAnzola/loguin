package com.example.loguin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText etCorreo, etContrasenia;
    private FirebaseAuth mAuth;
    private TextView tvRegistro,tvOlvidar;
    private Button btnInicio;
    private String correo, contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOlvidar=findViewById(R.id.tvOlvidar);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etContrasenia = (EditText) findViewById(R.id.etContrasenia);
        mAuth=FirebaseAuth.getInstance();


        tvRegistro = (TextView) findViewById(R.id.tvRegistro);

        btnInicio = (Button) findViewById(R.id.btnInicio);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                correo = etCorreo.getText().toString().trim();
                contrasenia = etContrasenia.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(correo,contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent i=new Intent(MainActivity.this, ActivityPerfil.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(MainActivity.this,"Revisa los campos introducidos",Toast.LENGTH_SHORT).show();


                        }

                    }
                });


            }
        });


        tvRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);


            }
        });
        tvOlvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Recuperar.class);
                startActivity(i);

            }
        });
    }
}