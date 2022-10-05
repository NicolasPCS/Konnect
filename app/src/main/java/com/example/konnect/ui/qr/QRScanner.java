package com.example.konnect.ui.qr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.konnect.MainActivity;
import com.example.konnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class QRScanner extends AppCompatActivity {

    Intent intentAct;

    // Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        mAuth = FirebaseAuth.getInstance();

        intentAct = getIntent();

        IntentIntegrator integrator = new IntentIntegrator(QRScanner.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("SCAN QR");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result =IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lectora cancelada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QRScanner.this, MainActivity.class);
                startActivity(intent);
                this.finish();
            } else {
                Intent intent = new Intent(QRScanner.this, MainActivity.class);

                // Enviar la data a Firebase desde aqui
                //result.getContents();
                // Incoming data of user from firebase
                FirebaseFirestore.getInstance().collection("connections").document(result.getContents().toString())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();

                                    String nombre, carrera, semestre, descripcion, whatsapp, linkedin, instagram, facebook;

                                    nombre = document.getString("nombre");
                                    carrera = document.getString("carrera");
                                    semestre = document.getString("semestre");
                                    descripcion = document.getString("descripcion");
                                    whatsapp = document.getString("whatsapp");
                                    linkedin = document.getString("linkedin");
                                    instagram = document.getString("instagram");
                                    facebook = document.getString("facebook");

                                    String correoContacto = result.getContents().toString();

                                    sendDataToFirebase(nombre, carrera, semestre, descripcion, whatsapp, linkedin, instagram, facebook, correoContacto);
                                } else {
                                    Toast.makeText(QRScanner.this, "No podemos conectarnos con nuestros servidores. Intentalo más tarde.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                // Send data of user to firebase
                //Toast.makeText(QRScanner.this, result.getContents(), Toast.LENGTH_SHORT).show();

                startActivity(intent);
                this.finish();
                //tctResult.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void sendDataToFirebase(String nombre, String carrera, String semestre, String descripcion, String whatsapp, String linkedin, String instagram, String facebook, String correoContacto) {
        // Add a new document with a generated id.
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", nombre);
        data.put("carrera", carrera);
        data.put("semestre", semestre);
        data.put("descripcion", descripcion);
        data.put("whatsapp", whatsapp);
        data.put("linkedin", linkedin);
        data.put("instagram", instagram);
        data.put("facebook", facebook);

        FirebaseFirestore.getInstance().collection("connections").document(mAuth.getCurrentUser().getEmail()).collection("child_connections").document(correoContacto)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(QRScanner.this, "Agregado a tu lista de contactos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QRScanner.this, "No pudimos agregar a tu lista de contactos", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}