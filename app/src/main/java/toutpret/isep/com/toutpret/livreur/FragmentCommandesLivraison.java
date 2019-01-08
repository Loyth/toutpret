package toutpret.isep.com.toutpret.livreur;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Commandes;

public class FragmentCommandesLivraison extends Fragment {
    View view;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private List<Commandes> listCommandes;
    private LivreurRecyclerViewAdapter myAdapter;

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_commandes_livraison, container, false);

        listCommandes = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        RecyclerView myrv = view.findViewById(R.id.livreur_commande_livraison_recyclerview_id);
        myAdapter = new LivreurRecyclerViewAdapter(getContext(), listCommandes);
        myrv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        myrv.setAdapter(myAdapter);

        getCommandes();

        return view;
    }

    public void syncCommandes() {
        listCommandes = new ArrayList<>();

        getCommandes();
    }

    private void getCommandes() {
        DatabaseReference myRef = mDatabase.getReference("commandes");

        myRef.orderByChild("livreurId").equalTo(auth.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Commandes newCommande = dataSnapshot.getValue(Commandes.class);
                newCommande.setCommandId(dataSnapshot.getKey());

                if (newCommande.getStatus().equals("En livraison")) {
                    listCommandes.add(newCommande);

                    Collections.sort(listCommandes, new Comparator<Commandes>() {
                        public int compare(Commandes o1, Commandes o2) {
                            return o1.getDate().compareTo(o2.getDate());
                        }
                    });

                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Commandes commandesChanged = dataSnapshot.getValue(Commandes.class);
                commandesChanged.setCommandId(dataSnapshot.getKey());

                for (Commandes commandes : listCommandes) {
                    if (commandes.getNumeroCommande().equals(commandesChanged.getNumeroCommande())) {
                        int position = listCommandes.indexOf(commandes);

                        if (commandesChanged.getStatus().equals("En livraison")) {
                            listCommandes.set(position, commandesChanged);
                        } else {
                            listCommandes.remove(position);
                        }

                        myAdapter.notifyDataSetChanged();

                        return;
                    }
                }

                if (commandesChanged.getStatus().equals("En livraison")) {
                    listCommandes.add(commandesChanged);

                    Collections.sort(listCommandes, new Comparator<Commandes>() {
                        public int compare(Commandes o1, Commandes o2) {
                            return o2.getDate().compareTo(o1.getDate());
                        }
                    });

                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}

