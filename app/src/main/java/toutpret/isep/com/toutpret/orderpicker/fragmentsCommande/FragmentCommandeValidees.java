package toutpret.isep.com.toutpret.orderpicker.fragmentsCommande;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Commandes;
import toutpret.isep.com.toutpret.orderpicker.OrderPickerRecyclerViewAdapter;

public class FragmentCommandeValidees extends Fragment {
    View view;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private List<Commandes> listCommande;
    private OrderPickerRecyclerViewAdapter myAdapter;
    private TextView text;

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
        view = inflater.inflate(R.layout.fragment_orderpicker_commandes_validees, container, false);

        listCommande = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        RecyclerView myrv = view.findViewById(R.id.orderpicker_commande_validees_recyclerview_id);
        myAdapter = new OrderPickerRecyclerViewAdapter(getContext(), listCommande);
        myrv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        myrv.setAdapter(myAdapter);

        text = view.findViewById(R.id.orderpicker_commande_validees_text_id);

        text.setVisibility(View.VISIBLE);

        getCommandes();

        return view;
    }

    private void getCommandes() {
        DatabaseReference myRef = mDatabase.getReference("commandes");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Commandes newCommande = dataSnapshot.getValue(Commandes.class);
                newCommande.setCommandId(dataSnapshot.getKey());

                if (!newCommande.getStatus().equals("En préparation")) {
                    listCommande.add(newCommande);

                    Collections.sort(listCommande, new Comparator<Commandes>() {
                        public int compare(Commandes o1, Commandes o2) {
                            return o2.getDate().compareTo(o1.getDate());
                        }
                    });

                    text.setVisibility(View.GONE);

                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Commandes commandesChanged = dataSnapshot.getValue(Commandes.class);
                commandesChanged.setCommandId(dataSnapshot.getKey());

                for (Commandes commandes : listCommande) {
                    if (commandes.getNumeroCommande().equals(commandesChanged.getNumeroCommande())) {
                        int position = listCommande.indexOf(commandes);

                        if (!commandesChanged.getStatus().equals("En préparation")) {
                            listCommande.set(position, commandesChanged);
                        } else {
                            listCommande.remove(position);
                        }

                        text.setVisibility(View.GONE);

                        myAdapter.notifyDataSetChanged();

                        return;
                    }
                }

                if (!commandesChanged.getStatus().equals("En préparation")) {
                    listCommande.add(commandesChanged);

                    Collections.sort(listCommande, new Comparator<Commandes>() {
                        public int compare(Commandes o1, Commandes o2) {
                            return o1.getDate().compareTo(o2.getDate());
                        }
                    });

                    text.setVisibility(View.GONE);

                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

