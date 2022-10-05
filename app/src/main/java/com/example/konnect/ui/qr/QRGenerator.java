package com.example.konnect.ui.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.konnect.MainActivity;
import com.example.konnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRGenerator extends AppCompatActivity {

    // Firebase
    private FirebaseAuth mAuth;

    Button regresar;
    ImageView ivQRGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);

        mAuth = FirebaseAuth.getInstance();

        regresar = (Button) findViewById(R.id.btnBack);
        ivQRGenerator = (ImageView) findViewById(R.id.ivQRGenerator);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(mAuth.getCurrentUser().getEmail().toString(), BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivQRGenerator.setImageBitmap(bitmap);

            // Toast.makeText(QRGenerator.this, ivQRGenerator.getMaxHeight() + " " + ivQRGenerator.getMaxWidth() + " " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QRGenerator.this, MainActivity.class));
                finish();
            }
        });
    }
}