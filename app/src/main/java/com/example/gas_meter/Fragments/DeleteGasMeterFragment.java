package com.example.gas_meter.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.gas_meter.Activitys.MainActivity;
import com.example.gas_meter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteGasMeterFragment extends AppCompatDialogFragment {

    private String document;

    public DeleteGasMeterFragment(String document) {
        this.document = document;
    }

    private MainActivity mainActivity;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mainActivity= (MainActivity) getActivity();
        AlertDialog.Builder builder=new AlertDialog.Builder(mainActivity);
        LayoutInflater inflater=mainActivity.getLayoutInflater();
        View v=inflater.inflate(R.layout.fragment_delet_gas_meter,null);


        builder.setView(v).setTitle("Törlés").setMessage("Biztosan kitörli a mérőállást?").setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Törlés", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                db.collection("GasMeter").document(document).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(v.getContext(),"Sikeres törlés!",Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(v.getContext(),"Sikertelen törlés!",Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });
        return builder.create();
    }
}
