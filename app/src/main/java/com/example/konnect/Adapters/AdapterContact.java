package com.example.konnect.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.konnect.Entities.Contact;
import com.example.konnect.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ViewHolder> {
    LayoutInflater inflater;
    ArrayList<Contact> model;

    // Listener
    private View.OnClickListener listener;

    public AdapterContact(Context context, ArrayList<Contact> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact_item, parent, false);
        //view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterContact.ViewHolder holder, int position) {
        String nombre = model.get(position).getNombre();
        String carrera = model.get(position).getCarrera();
        String semestre = model.get(position).getSemestre();
        String descripcion = model.get(position).getDescripcion();
        String whatsapp = model.get(position).getWhatsapp();
        String linkedIn = model.get(position).getLinkedIn();
        String instagram = model.get(position).getInstagram();
        String facebook = model.get(position).getFacebook();
        String fotoPerfil = model.get(position).getFotoPerfil();

        holder.nombre.setText(nombre);
        holder.carrera.setText(carrera);
        holder.semestre.setText(semestre);
        holder.descripcion.setText(descripcion);

        //Log.i("Linkedin 1", linkedIn + "");
        //Log.i("Instagram 1", instagram + "");
        //Log.i("Facebook 1", facebook + "");
        //Log.i("Foto 1", fotoPerfil + "           d");

        holder.whatsapp = whatsapp;
        holder.linkedin = linkedIn;
        holder.facebook = facebook;
        holder.instagram = instagram;

        //holder.fotoPerfil.setText(fotoPerfil);

        // Set events
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;

        // Datos
        TextView nombre, carrera, semestre, descripcion, fotoPerfil;
        String whatsapp;
        String linkedin;
        String facebook;
        String instagram;
        String sNuevaStringFotoPerfil;

        // ImageView
        ImageView ivFotoP;

        // Botones
        Button btnWhatsapp, btnLinkedin, btnInstagram, btnFacebook;
        Button btnContactar, btnFormarGrupo;

        // Expandable view
        public LinearLayout expandableView;
        public CardView cardView;
        public Button expandBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            // Datos
            nombre = itemView.findViewById(R.id.tvNombrePersona);
            carrera = itemView.findViewById(R.id.tvCarrera);
            semestre = itemView.findViewById(R.id.tvSemestre);
            descripcion = itemView.findViewById(R.id.tvDescripcion);
            //fotoPerfil = itemView.findViewById(R.id.urlImagen);
            // Btn Datos
            btnWhatsapp = itemView.findViewById(R.id.btnWhatsapp);
            btnLinkedin = itemView.findViewById(R.id.btnLinkedin);
            btnInstagram = itemView.findViewById(R.id.btnInstagram);
            btnFacebook = itemView.findViewById(R.id.btnFacebook);
            // Foto
            //ivFotoP = itemView.findViewById(R.id.fotoPerfilUser);
            //sNuevaStringFotoPerfil = fotoPerfil.getText().toString();
            //Log.i("Descripcion", descripcion.getText().toString() + "           d");
            /*Glide.with(context)
                    .load(sNuevaStringFotoPerfil)
                    .override(110, 110)
                    .into(ivFotoP);*/

            // Botones
            btnContactar = itemView.findViewById(R.id.btnContactar);
            btnFormarGrupo = itemView.findViewById(R.id.btnFormarGrupo);

            // Expandable CardView
            expandableView = (LinearLayout) itemView.findViewById(R.id.expandableLayout);
            expandBtn = (Button) itemView.findViewById(R.id.expandBtn);
            cardView = (CardView) itemView.findViewById(R.id.contact_item);

            //validateIncome(context);
        }

        void setOnClickListeners() {
            btnWhatsapp.setOnClickListener(this);
            btnLinkedin.setOnClickListener(this);
            btnInstagram.setOnClickListener(this);
            btnFacebook.setOnClickListener(this);

            btnContactar.setOnClickListener(this);
            btnFormarGrupo.setOnClickListener(this);
            expandBtn.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btnWhatsapp:
                    if (whatsapp != null) {
                        Uri uriWhatsapp;
                        Intent intentWhatsapp;
                        String url = "https://api.whatsapp.com/send/?phone=";
                        String message = "&text=%22Hola%2C+me+gustaría+que+me+añadas+a+tu+red+\uD83D\uDE42%21%22&type=phone_number";
                        uriWhatsapp = Uri.parse(url + whatsapp + "");
                        intentWhatsapp = new Intent(Intent.ACTION_VIEW, uriWhatsapp);
                        context.startActivity(intentWhatsapp);
                    } else {
                        Toast.makeText(context, "No pudimos encontrar estos datos", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnLinkedin:
                    if (linkedin != null) {
                        Uri uriLinkedin;
                        Intent intentLinkedin;
                        uriLinkedin = Uri.parse(linkedin);
                        intentLinkedin = new Intent(Intent.ACTION_VIEW, uriLinkedin);
                        context.startActivity(intentLinkedin);
                    } else {
                        Toast.makeText(context, "No pudimos encontrar estos datos", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnInstagram:
                    if (instagram != null) {
                        Uri uriInstagram;
                        Intent intentInstagram;
                        uriInstagram = Uri.parse(instagram);
                        intentInstagram = new Intent(Intent.ACTION_VIEW, uriInstagram);
                        context.startActivity(intentInstagram);
                    } else {
                        Toast.makeText(context, "No pudimos encontrar estos datos", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnFacebook:
                    if (facebook != null) {
                        Uri uriFacebook;
                        Intent intentFacebook;
                        uriFacebook = Uri.parse(facebook);
                        intentFacebook = new Intent(Intent.ACTION_VIEW, uriFacebook);
                        context.startActivity(intentFacebook);
                    } else {
                        Toast.makeText(context, "No pudimos encontrar estos datos", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnContactar:
                    Toast.makeText(context, "Hizo click en contactar", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnFormarGrupo:
                    Toast.makeText(context, "Hizo click en formar grupo", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.expandBtn:
                    expandLayout();
                    break;
            }
        }

        void expandLayout() {
            if (expandableView.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                expandableView.setVisibility(View.VISIBLE);
                expandBtn.setText("OCULTAR");
            } else {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                expandableView.setVisibility(View.GONE);
                expandBtn.setText("MARCAR ASISTENCIA");
            }
        }
    }
}
