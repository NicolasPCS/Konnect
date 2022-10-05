package com.example.konnect.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.konnect.Entities.Contact;
import com.example.konnect.MainActivity;
import com.example.konnect.R;
import com.example.konnect.databinding.FragmentSlideshowBinding;
import com.example.konnect.login_signup.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SlideshowFragment extends Fragment {

    // Firebase
    private FirebaseAuth mAuth;

    private TextView tvNombre, tvCarrera, tvSemestre;
    private EditText etSobreMi, etWhatsapp, etLinkedin, etFacebook, etInstagram;
    private Button btnUpdateAccount;

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tvNombre = (TextView) root.findViewById(R.id.tvNombreEdit);
        tvCarrera = (TextView) root.findViewById(R.id.tvCarreraEdit);
        tvSemestre = (TextView) root.findViewById(R.id.tvSemestreEdit);

        etSobreMi = (EditText) root.findViewById(R.id.editarDescripcion);
        etWhatsapp = (EditText) root.findViewById(R.id.editarWhatsapp);
        etLinkedin = (EditText) root.findViewById(R.id.editarLinkedin);
        etFacebook = (EditText) root.findViewById(R.id.editarFacebook);
        etInstagram = (EditText) root.findViewById(R.id.editarInstagram);

        btnUpdateAccount = (Button) root.findViewById(R.id.btnUpdateAccount);

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        cargarInfoDesdeFirebase();

        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarDataFirebase();
            }
        });

        return root;
    }

    private void actualizarDataFirebase() {
        FirebaseFirestore.getInstance().collection("connections").document(mAuth.getCurrentUser().getEmail())
                .update(
                    "descripcion", etSobreMi.getText().toString(),
                        "whatsapp", etWhatsapp.getText().toString(),
                        "linkedin", etWhatsapp.getText().toString(),
                        "facebook", etFacebook.getText().toString(),
                        "instagram", etInstagram.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getContext(), "No pudimos actualizar tus datos. Intentalo más tarde.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void cargarInfoDesdeFirebase() {
        FirebaseFirestore.getInstance().collection("connections").document(mAuth.getCurrentUser().getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                tvNombre.setText(document.getString("nombre"));
                                tvCarrera.setText(document.getString("carrera"));
                                tvSemestre.setText(document.getString("semestre"));

                                etSobreMi.setText(document.getString("descripcion"));
                                etWhatsapp.setText(document.getString("whatsapp"));
                                etLinkedin.setText(document.getString("linkedin"));
                                etFacebook.setText(document.getString("facebook"));
                                etInstagram.setText(document.getString("instagram"));
                            } else {
                                Log.d("LOGGER", "No such document");
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}