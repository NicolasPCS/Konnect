package com.example.konnect.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.konnect.R;
import com.example.konnect.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private Button wppDesarrollador, linkedinDesarrollador, instaDesarrollador, facebookDesarrollador, contactar;

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        wppDesarrollador = (Button) root.findViewById(R.id.btnWhatsappDesarrollador);
        linkedinDesarrollador = (Button) root.findViewById(R.id.btnLinkedinDesarrollador);
        instaDesarrollador = (Button) root.findViewById(R.id.btnInstagramDesarrollador);
        facebookDesarrollador = (Button) root.findViewById(R.id.btnFacebookDesarrollador);
        contactar = (Button) root.findViewById(R.id.btnContactarDesarrollador);

        wppDesarrollador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uriWhatsapp;
                    Intent intentWhatsapp;
                    String url = "https://api.whatsapp.com/send/?phone=";
                    String message = "&text=%22Hola+Nicolás+\uD83D\uDE42%21%22&type=phone_number";
                    uriWhatsapp = Uri.parse(url + "918389387".toString() + "");
                    intentWhatsapp = new Intent(Intent.ACTION_VIEW, uriWhatsapp);
                    getContext().startActivity(intentWhatsapp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        linkedinDesarrollador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uriLinkedin;
                    Intent intentLinkedin;
                    uriLinkedin = Uri.parse("https://www.linkedin.com/in/nicolas-caytuiro/");
                    intentLinkedin = new Intent(Intent.ACTION_VIEW, uriLinkedin);
                    getContext().startActivity(intentLinkedin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        instaDesarrollador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uriInstagram;
                    Intent intentInstagram;
                    uriInstagram = Uri.parse("https://www.instagram.com/nicolase.cs/");
                    intentInstagram = new Intent(Intent.ACTION_VIEW, uriInstagram);
                    getContext().startActivity(intentInstagram);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        facebookDesarrollador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uriFacebook;
                    Intent intentFacebook;
                    uriFacebook = Uri.parse("https://www.facebook.com/nicolas.caytuirosilva");
                    intentFacebook = new Intent(Intent.ACTION_VIEW, uriFacebook);
                    getContext().startActivity(intentFacebook);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        contactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uriWhatsapp;
                    Intent intentWhatsapp;
                    String url = "https://api.whatsapp.com/send/?phone=";
                    String message = "&text=%22Hola+Nicolás+\uD83D\uDE42%21%22&type=phone_number";
                    uriWhatsapp = Uri.parse(url + "918389387".toString() + "");
                    intentWhatsapp = new Intent(Intent.ACTION_VIEW, uriWhatsapp);
                    getContext().startActivity(intentWhatsapp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}