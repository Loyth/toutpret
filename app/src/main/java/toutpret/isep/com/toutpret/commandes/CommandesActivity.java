package toutpret.isep.com.toutpret.commandes;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

import toutpret.isep.com.toutpret.Profil.ProfilActivity;
import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;
import toutpret.isep.com.toutpret.map.DialogFragmentMap;
import toutpret.isep.com.toutpret.models.Commandes;

public class CommandesActivity extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private List<Commandes> listCommandes;
    private RecyclerViewCommandesAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        listCommandes = new ArrayList<>();

        getCommandes();

        RecyclerView myrv = findViewById(R.id.commande_recyclerview_id);
        myAdapter = new RecyclerViewCommandesAdapter(this, listCommandes, getSupportFragmentManager());
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);
    }

    private void getCommandes() {
        DatabaseReference myRef = mDatabase.getReference("commandes");

        myRef.orderByChild("userId").equalTo(auth.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Commandes newCommande = dataSnapshot.getValue(Commandes.class);
                newCommande.setCommandId(dataSnapshot.getKey());

                listCommandes.add(newCommande);

                Collections.sort(listCommandes, new Comparator<Commandes>() {
                    public int compare(Commandes o1, Commandes o2) {
                        return o2.getDate().compareTo(o1.getDate());
                    }
                });

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Commandes commandesChanged = dataSnapshot.getValue(Commandes.class);
                commandesChanged.setCommandId(dataSnapshot.getKey());

                for (Commandes commandes : listCommandes) {
                    if (commandes.getNumeroCommande().equals(commandesChanged.getNumeroCommande())) {
                        int position = listCommandes.indexOf(commandes);

                        listCommandes.set(position, commandesChanged);
                        myAdapter.notifyItemChanged(position);

                        return;
                    }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem logout = menu.findItem(R.id.action_logout);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                auth.signOut();
                Toast.makeText(getApplicationContext(), "Déconnexion réussie !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            }
        });

        MenuItem profile = menu.findItem(R.id.action_sun);

        profile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                return true;
            }
        });

        return true;
    }
}
