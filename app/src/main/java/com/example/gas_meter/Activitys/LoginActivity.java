package com.example.gas_meter.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gas_meter.R;
import com.example.gas_meter.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private String userName;
    private String password;

    private AppCompatButton reg,login;
    private  EditText input_userName,input_password;

    private RelativeLayout image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        image=findViewById(R.id.image);
        reg=findViewById(R.id.register);

        RotateAnimation rotateAnimation=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(5000);
        image.setAnimation(rotateAnimation);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

    }

    public void login (View view){

        try {

            input_userName=findViewById(R.id.username);
            input_password=findViewById(R.id.password);

            userName=input_userName.getText().toString();
            password=input_password.getText().toString();

            if(input_userName!=null&&input_password!=null){
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                DocumentReference documentReference=db.collection("User").document(userName);

                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot=task.getResult();
                            if (documentSnapshot.exists()){
                                UserModel userModel =documentSnapshot.toObject(UserModel.class);
                                String dbpassword= userModel.getPassword();
                                if(dbpassword.equals(password)){
                                    String str =userModel.getFirstName()+" "+userModel.getLastName();
                                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("name",str);
                                    String str2=userModel.getUserName();
                                    intent.putExtra("username",str2);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Hibás jelszó!",Toast.LENGTH_LONG).show();

                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Nincs ilyen felhasználó!",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"A művelet sikertelen!",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(),"Nem adtad meg a felhasználó nevet vagy a jelszót!",Toast.LENGTH_LONG).show();

            }



        }
        catch (Exception e){
            System.err.println("Hiba: "+e);
        }
    }



}