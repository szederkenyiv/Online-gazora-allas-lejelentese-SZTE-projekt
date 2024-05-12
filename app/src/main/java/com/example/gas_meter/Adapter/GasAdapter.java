package com.example.gas_meter.Adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gas_meter.Activitys.MainActivity;
import com.example.gas_meter.Fragments.DeleteGasMeterFragment;
import com.example.gas_meter.Fragments.UpdateGasMeterFragment;
import com.example.gas_meter.Model.GasMeterModel;
import com.example.gas_meter.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class GasAdapter extends FirestoreRecyclerAdapter<GasMeterModel,GasAdapter.GasHolder> {
    private MainActivity mainActivity;
    public GasAdapter(@NonNull FirestoreRecyclerOptions<GasMeterModel> options, MainActivity mainActivity) {
        super(options);
        this.mainActivity=mainActivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull GasAdapter.GasHolder holder, int position, @NonNull GasMeterModel model) {
        String finalAdress=model.getGasState()+","+System.getProperty("line.separator")+model.getGasCity()+" "+model.getGasAdress();
        if(model.getQuantity()!=null&&model.getDate()!=null){
            String str2="Utolsó bediktálás: "+model.getDate()+System.getProperty("line.separator")+"Bediktált mennyiség: "+model.getQuantity();
            holder.quantity.setText(str2);

        }
        holder.address.setText(finalAdress);
        holder.gasmetercode.setText("Mérőóra száma: "+model.getGasId());
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(v.getContext(), v);

                popupMenu.inflate(R.menu.gasmenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.menu1) {
                            UpdateGasMeterFragment updateGasMeterFragment=new UpdateGasMeterFragment(model.getGasId());
                            updateGasMeterFragment.show(mainActivity.getSupportFragmentManager(),"a");
                            return true;
                        }
                        else if (itemId == R.id.menu2) {
                            DeleteGasMeterFragment deleteGasMeterFragment= new DeleteGasMeterFragment(model.getGasId());
                            deleteGasMeterFragment.show(mainActivity.getSupportFragmentManager(),"a");
                            return true;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @NonNull
    @Override
    public GasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gas_item,parent,false);
        return new GasHolder(view);
    }
    class GasHolder extends RecyclerView.ViewHolder{
        TextView address,menu,gasmetercode,quantity;


        public GasHolder(View view){
            super(view);
            address=view.findViewById(R.id.address);
            menu=view.findViewById(R.id.menu);
            gasmetercode=view.findViewById(R.id.gasmetercode);
            quantity=view.findViewById(R.id.quantity);
        }

    }
}
