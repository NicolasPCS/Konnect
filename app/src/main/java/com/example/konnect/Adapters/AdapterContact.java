package com.example.konnect.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.konnect.Entities.Contact;
import com.example.konnect.R;

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

        holder.nombre.setText(nombre);
        holder.carrera.setText(carrera);
        holder.semestre.setText(semestre);
        holder.descripcion.setText(descripcion);

        holder.whatsapp = whatsapp;
        holder.linkedin = linkedIn;
        holder.facebook = instagram;
        holder.instagram = facebook;


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
        TextView nombre, carrera, semestre, descripcion;
        String whatsapp, linkedin, facebook, instagram;
        // BotonesDatos
        Button btnWhatsapp, btnLinkedin, btnInstagram, btnFacebook;

        // Botones
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
            // Btn Datos
            btnWhatsapp = itemView.findViewById(R.id.btnWhatsapp);
            btnLinkedin = itemView.findViewById(R.id.btnLinkedin);
            btnInstagram = itemView.findViewById(R.id.btnInstagram);
            btnFacebook = itemView.findViewById(R.id.btnFacebook);

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

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnWhatsapp:
                    Toast.makeText(context, "Hizo click en whatsapp " + whatsapp, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnLinkedin:
                    Toast.makeText(context, "Hizo click en linkedin " + linkedin, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnInstagram:
                    Toast.makeText(context, "Hizo click en instagram " + instagram, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnFacebook:
                    Toast.makeText(context, "Hizo click en facebook " + facebook, Toast.LENGTH_SHORT).show();
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
