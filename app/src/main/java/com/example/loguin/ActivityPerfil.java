package com.example.loguin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityPerfil extends AppCompatActivity {
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseUser fbUser;
    private EditText etNombreP,etApellidoP,etCorreoP,etNumeroP;
    private ImageView ivFoto;
    private String nombreN,apellidoN,telefonoN;
    private Button btnGuardar,btnInv,btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        etNombreP=(EditText) findViewById(R.id.etNombreP);
        etApellidoP=(EditText) findViewById(R.id.etApellidoP);
        etCorreoP=(EditText) findViewById(R.id.etCorreoP);
        etNumeroP=(EditText) findViewById(R.id.etNumeroP);
        btnEditar=(Button) findViewById(R.id.btnEditar);
        btnGuardar=(Button) findViewById(R.id.btnGuardar);
        ivFoto=(ImageView) findViewById(R.id.ivFoto);

        mAuth = FirebaseAuth.getInstance();
        fbUser= mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Usuarios");



        deshabilitar();
        cargarDatos();

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitar();

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deshabilitar();
                nombreN=etNombreP.getText().toString().trim();
                apellidoN=etApellidoP.getText().toString().trim();
                telefonoN=etNumeroP.getText().toString().trim();
                Usuario newU=new Usuario(nombreN,apellidoN,fbUser.getEmail(),telefonoN);
                mRef.child(fbUser.getUid()).setValue(newU).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ActivityPerfil.this, "Editado Correctamente", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(ActivityPerfil.this, "Todo ha ido mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });
        }
    private void deshabilitar(){
        etNombreP.setEnabled(false);
        etApellidoP.setEnabled(false);
        etCorreoP.setEnabled(false);
        etNumeroP.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnEditar.setEnabled(true);
    }
    private void habilitar(){
        etNombreP.setEnabled(true);
        etApellidoP.setEnabled(true);
        etNumeroP.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnEditar.setEnabled(false);
    }

    private void cargarDatos(){
        String uid=fbUser.getUid();
        mRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario u=snapshot.getValue(Usuario.class);
                etNombreP.setText(u.getNombre());
                etApellidoP.setText(u.getApellido());
                etCorreoP.setText(u.getCorreo());
                etNumeroP.setText(u.getNumero());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}