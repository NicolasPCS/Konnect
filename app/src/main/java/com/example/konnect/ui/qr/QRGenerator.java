package com.example.konnect.ui.qr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class QRGenerator extends AppCompatActivity {

    // Firebase
    private FirebaseAuth mAuth;

    Button regresar, compartir;
    ImageView ivQRGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);

        mAuth = FirebaseAuth.getInstance();

        regresar = (Button) findViewById(R.id.btnBack);
        compartir = (Button) findViewById(R.id.btnShare);

        ivQRGenerator = (ImageView) findViewById(R.id.ivQRGenerator);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        Bitmap bitmap2 = null;

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(mAuth.getCurrentUser().getEmail().toString(), BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            bitmap2 = bitmap;
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

        Bitmap finalBitmap = bitmap2;
        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(finalBitmap);
            }
        });
    }

    private void shareImage(Bitmap bitmap) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        Uri bmpUri;
        String textToShare = "Mi código QR";
        bmpUri = saveImage(bitmap, getApplicationContext());
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.putExtra(Intent.EXTRA_STREAM, bmpUri);
        share.putExtra(Intent.EXTRA_SUBJECT, "Konnect");
        share.putExtra(Intent.EXTRA_TEXT, textToShare);
        startActivity(Intent.createChooser(share, "Compartir QR"));
    }

    private static Uri saveImage(Bitmap image, Context context) {
        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_images.jpg");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()), "com.example.konnect"+".provider", file);
        } catch (IOException e) {
            Log.d("TAG", "Exception" + e.getMessage());
        }
        return uri;
    }
}