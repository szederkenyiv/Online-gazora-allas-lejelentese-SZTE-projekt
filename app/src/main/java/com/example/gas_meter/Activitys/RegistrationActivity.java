package com.example.gas_meter.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gas_meter.R;
import com.example.gas_meter.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    private String firstnameStr,usernameStr,lastnameStr,passwordStr;
    private EditText firstname,lastname,username, password;
    private AppCompatButton req,cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        req = findViewById(R.id.add);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    firstname = findViewById(R.id.firstname);
                    lastname = findViewById(R.id.lastname);
                    username = findViewById(R.id.usernamereg);
                    password=findViewById(R.id.password);
                    if (firstname != null && lastname != null && username != null) {
                        firstnameStr = firstname.getText().toString();
                        lastnameStr = lastname.getText().toString();
                        usernameStr = username.getText().toString();
                        passwordStr=password.getText().toString();
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        UserModel userModel =new UserModel(firstnameStr,lastnameStr,usernameStr,passwordStr);

                        DocumentReference documentReference=db.collection("User").document(usernameStr);
                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                     if (task.isSuccessful()){
                                         DocumentSnapshot document= task.getResult();
                                         if (document.exists()){

                                             Toast.makeText(getApplicationContext(),"Ez a felhasználónév már foglalt!",Toast.LENGTH_LONG).show();
                                         }
                                         else {
                                             db.collection("User").document(usernameStr).set(userModel);
                                             finish();
                                             Toast.makeText(getApplicationContext(),"Sikeres regisztráció!",Toast.LENGTH_LONG).show();
                                         }
                                     }
                                     else {
                                         Toast.makeText(getApplicationContext(),"A művelet sikertelen!",Toast.LENGTH_LONG).show();
                                     }
                            }
                        });



                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Nem adtál meg minden adatot!",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    System.err.println("Hiba: " + e);
                }

            }
        });

        cancel=findViewById(R.id.back);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
