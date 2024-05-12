package com.example.gas_meter.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.gas_meter.Fragments.NewGasMeterFragment;
import com.example.gas_meter.Adapter.GasAdapter;
import com.example.gas_meter.Model.GasMeterModel;
import com.example.gas_meter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    private GasAdapter gasAdapter;
    private Context context;
    public String getUsernameStr() {
        return usernameStr;
    }

    private String usernameStr;

    private NewGasMeterFragment gasMeterFragment;
    AppCompatButton newGasMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_main);
        TextView username=findViewById(R.id.greet);
        Intent intent=getIntent();
        String wellcome=intent.getStringExtra("name");
        usernameStr=intent.getStringExtra("username");
        Animation animation=new AlphaAnimation(1.0f,0.0f);
        animation.setStartOffset(600);
        animation.setDuration(400);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                username.setText("Üdv " + wellcome+"!");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                username.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        username.setAnimation(animation);



        RecyclerSetup();

        newGasMeter=findViewById(R.id.newgasmeter);

        newGasMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gasMeterFragment=new NewGasMeterFragment();
                gasMeterFragment.show(getSupportFragmentManager(),"a");
            }
        });
    }

    private void RecyclerSetup(){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Query query= db.collection("GasMeter").whereEqualTo("userName",usernameStr);
        FirestoreRecyclerOptions <GasMeterModel> options=new FirestoreRecyclerOptions.Builder<GasMeterModel>()
                .setQuery(query,GasMeterModel.class)
                .build();
        gasAdapter=new GasAdapter(options,this);

        RecyclerView recyclerView= findViewById(R.id.recyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(gasAdapter);
    }
    @Override
    protected void onStart(){
        super.onStart();
        gasAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        gasAdapter.stopListening();
    }
}