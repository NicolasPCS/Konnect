package com.example.konnect.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.konnect.Adapters.AdapterContact;
import com.example.konnect.Entities.Contact;
import com.example.konnect.R;
import com.example.konnect.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private LinearLayout expandableView;
    private Button expandBtn;
    private CardView cardView;

    // Firebase
    private FirebaseAuth mAuth;


    // Adapter para inflar el CardView
    private AdapterContact adapterContact;
    private RecyclerView recyclerViewContacts;
    private ArrayList<Contact> contactArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        expandableView = (LinearLayout) root.findViewById(R.id.expandableLayout);
        expandBtn = (Button) root.findViewById(R.id.expandBtn);
        cardView = (CardView) root.findViewById(R.id.contact_item);

        recyclerViewContacts = root.findViewById(R.id.recyclerViewContacts);
        contactArrayList = new ArrayList<>();

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Cargar y mostrar lista
        cargarListaFromFirebase(new myCallBack() {
            @Override
            public void onCallback(ArrayList<Contact> contactList) {
                mostrarData();
            }
        });
        /*cargarListaFromFirebase(new myCallBack() {
            @Override
            public void onCallback(ArrayList<Contact> contactList) {
                mostrarData();
            }
        });*/

        return root;
    }

    private void inicializarFirebase() {

    }

    private void mostrarData() {
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterContact = new AdapterContact(getContext(), contactArrayList);
        recyclerViewContacts.setAdapter(adapterContact);

        Log.d("Size", String.valueOf(contactArrayList.size()));

        /*for (int i = 0; i < courseArrayList.size(); i++) {
            Log.d("Course", courseArrayList.get(i).getAula());
            Log.d("Course", courseArrayList.get(i).getHorario());
            Log.d("Course", courseArrayList.get(i).getNombrecurso());
            Log.d("Course", courseArrayList.get(i).getNombredocente());
            Log.d("Course", courseArrayList.get(i).getToken());
        }*/
    }

    /*private void cargarLista(myCallBack mCallBack) {
        contactArrayList.add(new Contact("Nicol??s Caytuiro Silva", "Ingenier??a de Sistemas", "Octavo semestre", "Hola como est??s, mi nombre es Nicol??s y me gustar??a unirme a tu red", "+51 918389387", "https://www.linkedin.com/feed/", "https://www.instagram.com/", "https://www.facebook.com/"));
        contactArrayList.add(new Contact("Nicol??s Caytuiro Silva", "Ingenier??a de Sistemas", "Octavo semestre", "Hola como est??s, mi nombre es Nicol??s y me gustar??a unirme a tu red", "+51 918389387", "https://www.linkedin.com/feed/", "https://www.instagram.com/", "https://www.facebook.com/"));
        contactArrayList.add(new Contact("Nicol??s Caytuiro Silva", "Ingenier??a de Sistemas", "Octavo semestre", "Hola como est??s, mi nombre es Nicol??s y me gustar??a unirme a tu red", "+51 918389387", "https://www.linkedin.com/feed/", "https://www.instagram.com/", "https://www.facebook.com/"));
        contactArrayList.add(new Contact("Nicol??s Caytuiro Silva", "Ingenier??a de Sistemas", "Octavo semestre", "Hola como est??s, mi nombre es Nicol??s y me gustar??a unirme a tu red", "+51 918389387", "https://www.linkedin.com/feed/", "https://www.instagram.com/", "https://www.facebook.com/"));
        contactArrayList.add(new Contact("Nicol??s Caytuiro Silva", "Ingenier??a de Sistemas", "Octavo semestre", "Hola como est??s, mi nombre es Nicol??s y me gustar??a unirme a tu red", "+51 918389387", "https://www.linkedin.com/feed/", "https://www.instagram.com/", "https://www.facebook.com/"));
        mCallBack.onCallback(contactArrayList);
    }*/

    private void cargarListaFromFirebase(myCallBack mCallBack) {
        FirebaseFirestore.getInstance().collection("connections").document(mAuth.getCurrentUser().getEmail()).collection("child_connections")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                Contact aux = new Contact();
                                aux.setNombre(document.getString("nombre"));
                                aux.setCarrera(document.getString("carrera"));
                                aux.setSemestre(document.getString("semestre"));
                                aux.setDescripcion(document.getString("descripcion"));
                                aux.setWhatsapp(document.getString("whatsapp"));
                                aux.setLinkedIn(document.getString("linkedin"));
                                aux.setInstagram(document.getString("instagram"));
                                aux.setFacebook(document.getString("facebook"));
                                aux.setFotoPerfil(document.getString("foto_perfil"));
                                contactArrayList.add(aux);
                            }
                            mCallBack.onCallback(contactArrayList);
                        } else {
                            Toast.makeText(getContext(), "Lo sentimos, no pudimos encontrar conexiones. Estamos trabajando en ello.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public interface myCallBack {
        void onCallback(ArrayList<Contact> contactList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}