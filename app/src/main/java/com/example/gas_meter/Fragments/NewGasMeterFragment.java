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

import com.example.gas_meter.Model.GasMeterModel;
import com.example.gas_meter.Activitys.MainActivity;
import com.example.gas_meter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class NewGasMeterFragment extends AppCompatDialogFragment {

    protected EditText gasId_in,gasState_in,gasCity_in,gasAdress_in;
    private MainActivity mainActivity;
    private String gasId,gasState,gasCity,gasAdress,userName;
    private boolean notempty;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mainActivity= (MainActivity) getActivity();
        AlertDialog.Builder builder=new AlertDialog.Builder(mainActivity);
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.fragment_new_gas_meter,null);


            gasId_in =view.findViewById(R.id.gasId);
            gasState_in=view.findViewById(R.id.gasState);
            gasCity_in=view.findViewById(R.id.gasCity);
            gasAdress_in=view.findViewById(R.id.gasAdress);
            userName=mainActivity.getUsernameStr();




        builder.setView(view).setCancelable(false).setTitle("Új mérőóra regisztrációja").setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        }).setPositiveButton("Mentés", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    gasId=gasId_in.getText().toString();
                    gasState=gasState_in.getText().toString();
                    gasCity=gasCity_in.getText().toString();
                    gasAdress=gasAdress_in.getText().toString();
                    if(!Objects.equals(gasId, "")&& !Objects.equals(gasState, "") && !Objects.equals(gasCity, "") && !gasAdress.equals("")){
                        FirebaseFirestore db= FirebaseFirestore.getInstance();
                        GasMeterModel gasMeterModel=new GasMeterModel(userName,gasId,gasState,gasCity,gasAdress);

                        DocumentReference documentReference=db.collection("GasMeter").document(gasId);
                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot documentSnapshot= task.getResult();
                                    if(documentSnapshot.exists()){
                                        Toast.makeText(mainActivity.getApplicationContext(),"Ez a mérőóraszám már regisztrálva lett!",Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        db.collection("GasMeter").document(gasId).set(gasMeterModel);
                                        //kilépés
                                        Toast.makeText(mainActivity.getApplicationContext(),"Sikeres méróra regisztráció!",Toast.LENGTH_LONG).show();

                                    }

                                }
                                else {
                                    Toast.makeText(mainActivity.getApplicationContext(),"Sikertelen mérőóra regisztráció!",Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(mainActivity.getApplicationContext(),"Sikertelen méróra regisztráció!",Toast.LENGTH_LONG).show();

                    }
                }catch (Exception e){
                    System.err.println("Hiba: "+ e);
                }


            }
        });
        return builder.create();
    }
}
