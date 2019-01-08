package toutpret.isep.com.toutpret.orderpicker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Commandes;
import toutpret.isep.com.toutpret.models.ProductPanier;
import toutpret.isep.com.toutpret.models.User;

public class OrderPickerRecyclerViewAdapter extends RecyclerView.Adapter<OrderPickerRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Commandes> mData;

    public OrderPickerRecyclerViewAdapter(Context mContext, List<Commandes> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_commande, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.numeroCommande.setText("Commande n°" + mData.get(i).getNumeroCommande());

        int nombreArticles = 0;

        Collection<ProductPanier> values = mData.get(i).getListProducts().values();

        List<ProductPanier> productList = new ArrayList<>(values);

        for (ProductPanier product : productList) {
            nombreArticles += product.getQuantity();
        }

        myViewHolder.nombreArticles.setText("Nombre d'articles : " + nombreArticles);
        myViewHolder.dateCommande.setText("Commandée le " + mData.get(i).getDate());

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
        myRef.orderByChild("userID").equalTo(mData.get(i).getUserId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);

                myViewHolder.nomClient.setText("Client : " + user.getFirstName() + " " + user.getLastName());
                myViewHolder.telClient.setText("Tél : " + user.getPhoneNumber());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myViewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                DialogFragment dialogFragment = new DialogFragmentDetails();

                Bundle extras = new Bundle();
                extras.putSerializable("HashMap", mData.get(i).getListProducts());
                dialogFragment.setArguments(extras);

                dialogFragment.show(manager, "Détails");
            }
        });




        if (mData.get(i).getStatus().equals("En préparation")) {
            myViewHolder.status.setText("A livrer");

            myViewHolder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("commandes").child(mData.get(i).getCommandId());
                    updateData.child("status").setValue("Prête");
                    Toast.makeText(view.getContext(), "Commande prête à être livrée !", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            myViewHolder.status.setText(mData.get(i).getStatus());
            myViewHolder.status.setEnabled(false);
        }
    }


    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView numeroCommande;
        TextView nombreArticles;
        TextView dateCommande;
        TextView nomClient;
        TextView telClient;
        Button details;
        Button status;

        public MyViewHolder(View itemView) {
            super(itemView);

            numeroCommande = itemView.findViewById(R.id.orderpicker_numeroCommande);
            nombreArticles = itemView.findViewById(R.id.orderpicker_nbArticles);
            dateCommande = itemView.findViewById(R.id.orderpicker_dateCommande);
            nomClient = itemView.findViewById(R.id.orderpicker_nom_client);
            telClient = itemView.findViewById(R.id.orderpicker_tel_client);
            details = itemView.findViewById(R.id.orderpicker_button_details);
            status = itemView.findViewById(R.id.orderpicker_button_status);
        }
    }
}
