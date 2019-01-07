package toutpret.isep.com.toutpret.livreur;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.map.DialogFragmentMap;
import toutpret.isep.com.toutpret.models.Commandes;
import toutpret.isep.com.toutpret.models.ProductPanier;
import toutpret.isep.com.toutpret.models.User;
import toutpret.isep.com.toutpret.panier.Panier;

public class LivreurRecyclerViewAdapter extends RecyclerView.Adapter<LivreurRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Commandes> mData;

    public LivreurRecyclerViewAdapter(Context mContext, List<Commandes> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_commandes_livreur, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.numero_commande.setText("Commande n°" + mData.get(i).getNumeroCommande());

        int nombreArticles = 0;

        Collection<ProductPanier> values = mData.get(i).getListProducts().values();

        List<ProductPanier> productList = new ArrayList<>(values);

        for (ProductPanier product : productList) {
            nombreArticles += product.getQuantity();
        }

        myViewHolder.nombre_articles.setText("Nombre d'articles : " + nombreArticles);

        myViewHolder.date_commande.setText("Commandée le " + mData.get(i).getDate());

        switch (mData.get(i).getStatus()) {
            case "Prête":
                myViewHolder.status.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9E9E9E")));
                myViewHolder.status.setText("Prendre");
                myViewHolder.status.setEnabled(true);

                myViewHolder.status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("commandes").child(mData.get(i).getCommandId());

                        // get coordonnee gps of livreur
                        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                            Criteria criteria = new Criteria();
                            String provider = locationManager.getBestProvider(criteria, false);

                            Location location = locationManager.getLastKnownLocation(provider);

                            if (location != null) {
                                String latitude = String.valueOf(location.getLatitude());
                                String longitude = String.valueOf(location.getLongitude());

                                updateData.child("livreurLat").setValue(latitude);
                                updateData.child("livreurLng").setValue(longitude);

                                updateData.child("status").setValue("En livraison");
                                updateData.child("livreurId").setValue(FirebaseAuth.getInstance().getUid());

                                Toast.makeText(mContext, "Commande prise !", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(mContext, "Position GPS indisponible !", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                break;
            case "En livraison":
                myViewHolder.status.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                myViewHolder.status.setText("Y aller");
                myViewHolder.status.setEnabled(true);

                myViewHolder.status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // open the MAP

                    }
                });
                break;
            case "Livrée":
                myViewHolder.status.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                myViewHolder.status.setText("Livrée");
                myViewHolder.status.setEnabled(false);
                break;
        }

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
        myRef.orderByChild("userID").equalTo(mData.get(i).getUserId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);

                myViewHolder.nom_client.setText("Client : " + user.getFirstName() + " " + user.getLastName());
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView numero_commande;
        TextView nom_client;
        TextView nombre_articles;
        TextView date_commande;
        Button status;

        public MyViewHolder(View itemView) {
            super(itemView);

            numero_commande = itemView.findViewById(R.id.livreur_commande_numero);
            nom_client = itemView.findViewById(R.id.livreur_commande_nom);
            nombre_articles = itemView.findViewById(R.id.livreur_commande_nombre_articles);
            date_commande = itemView.findViewById(R.id.livreur_commande_date);
            status = itemView.findViewById(R.id.livreur_commande_status);
        }
    }
}
