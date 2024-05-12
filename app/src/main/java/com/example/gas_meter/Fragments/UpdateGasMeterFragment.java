package com.example.gas_meter.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.gas_meter.Activitys.MainActivity;
import com.example.gas_meter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateGasMeterFragment extends AppCompatDialogFragment {
    private MainActivity mainActivity;
    private EditText input;

    private String quantity;
    private String document;

    public UpdateGasMeterFragment(String document) {
        this.document = document;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mainActivity= (MainActivity) getActivity();
        AlertDialog.Builder builder=new AlertDialog.Builder(mainActivity);
        LayoutInflater inflater=mainActivity.getLayoutInflater();
        View v=inflater.inflate(R.layout.fragment_update_gas_meter,null);
        input=v.findViewById(R.id.input);


        builder.setView(v).setTitle("Új bediktálás").setMessage("Kérlek add meg a mérőórán kijelzett számot!")
                    .setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    }).setPositiveButton("Hozzáadás", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            quantity=input.getText().toString();
                            if(!quantity.equals("")){
                                FirebaseFirestore db=FirebaseFirestore.getInstance();
                                DocumentReference documentReference= db
                                        .collection("GasMeter").document(document);
                                documentReference.update("quantity",quantity).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(v.getContext(),"Sikeres bediktálás!",Toast.LENGTH_LONG).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(),"Sikertelen bediktálás!",Toast.LENGTH_LONG).show();

                                    }
                                });
                                Date date = Calendar.getInstance().getTime();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
                                String strDate= dateFormat.format(date);
                                documentReference.update("date",strDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(v.getContext(),"Sikeres bediktálás!",Toast.LENGTH_LONG).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(),"Sikertelen bediktálás!",Toast.LENGTH_LONG).show();

                                    }
                                });

                            }


                        }
                    });




        return builder.create();
    }
}
