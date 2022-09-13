package com.example.konnect.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.konnect.MainActivity;
import com.example.konnect.R;
import com.example.konnect.ui.qr.QRScanner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterSecondActivity extends AppCompatActivity {

    private Button btnCreateAccount;
    private EditText etNombre, etCarrera, etSemestre, etDescripcion, etWhatsapp, etLinkedin, etFacebook, etInstagram;

    // Alert dialog
    private AlertDialog.Builder builder;

    // Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        etNombre = (EditText) findViewById(R.id.registerNombre);
        etCarrera = (EditText) findViewById(R.id.registerCarrera);
        etSemestre = (EditText) findViewById(R.id.registerSemestre);
        etDescripcion = (EditText) findViewById(R.id.registerDescripcion);
        etWhatsapp = (EditText) findViewById(R.id.registerWhatsapp);
        etLinkedin = (EditText) findViewById(R.id.registerLinkedin);
        etFacebook = (EditText) findViewById(R.id.registerFacebook);
        etInstagram = (EditText) findViewById(R.id.registerInstagram);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    public void validateData() {
        // Get data
        String nombre, carrera, semestre, descripcion, whatsapp, linkedin, facebook, instagram;
        nombre = etNombre.getText().toString().trim();
        carrera = etCarrera.getText().toString().trim();
        semestre = etSemestre.getText().toString().trim();
        descripcion = etDescripcion.getText().toString().trim();
        whatsapp = etWhatsapp.getText().toString().trim();
        linkedin = etLinkedin.getText().toString().trim();
        facebook = etFacebook.getText().toString().trim();
        instagram = etInstagram.getText().toString().trim();

        // Validate data
        if (nombre.isEmpty()) {
            etNombre.setError("Debe completar este campo");
            etNombre.requestFocus();
        } else if (carrera.isEmpty()) {
            etCarrera.setError("Debe completar este campo");
            etCarrera.requestFocus();
        } else if (semestre.isEmpty()) {
            etSemestre.setError("Debe completar este campo");
            etSemestre.requestFocus();
        } else if (descripcion.isEmpty()) {
            etDescripcion.setError("Debe completar este campo");
            etDescripcion.requestFocus();
        } else if (whatsapp.isEmpty()) {
            etWhatsapp.setError("Debe completar este campo");
            etWhatsapp.requestFocus();
        } else if (whatsapp.length() != 9) {
            etWhatsapp.setError("Número de Whatsapp incorrecto");
            etWhatsapp.requestFocus();
        } else {
            firebaseCreateAccount(nombre, carrera, semestre, descripcion, whatsapp, linkedin, facebook, instagram);
        }
    }

    public void firebaseCreateAccount(String nombre, String carrera, String semestre, String descripcion, String whatsapp, String linkedin, String facebook, String instagram) {
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", nombre);
        data.put("carrera", carrera);
        data.put("semestre", semestre);
        data.put("descripcion", descripcion);
        data.put("whatsapp", whatsapp);
        data.put("linkedin", linkedin);
        data.put("instagram", instagram);
        data.put("facebook", facebook);

        FirebaseFirestore.getInstance().collection("connections").document(mAuth.getCurrentUser().getEmail())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        /*builder = new AlertDialog.Builder(RegisterSecondActivity.this);
                        builder.setTitle("Cuenta creada exitosamente");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", null);
                        builder.show();*/

                        Toast.makeText(RegisterSecondActivity.this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegisterSecondActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        builder = new AlertDialog.Builder(RegisterSecondActivity.this);
                        builder.setTitle("No pudimos crear esta cuenta");
                        builder.setMessage("Comunicate con nuestro equipo de soporte");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar", null);
                        builder.show();

                        finish();
                    }
                });
    }
}