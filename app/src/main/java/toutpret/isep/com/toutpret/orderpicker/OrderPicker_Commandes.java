package toutpret.isep.com.toutpret.orderpicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.categories.CategoryRecyclerViewAdapter;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;
import toutpret.isep.com.toutpret.models.Category;
import toutpret.isep.com.toutpret.models.Commandes;
import toutpret.isep.com.toutpret.models.Product;

public class OrderPicker_Commandes extends AppCompatActivity {

    private List<Commandes> listCommande;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private OrderPickerRecyclerViewAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_picker__commandes);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        listCommande = new ArrayList<>();

        getCommandes("1");

        RecyclerView myrv = findViewById(R.id.orderpicker_commande_recyclerview_id);
        myAdapter = new OrderPickerRecyclerViewAdapter(this, listCommande);
        myrv.setLayoutManager(new GridLayoutManager(this, 1));
        myrv.setAdapter(myAdapter);
    }

    private void getCommandes(String numeroCommande) {
        DatabaseReference myRef = mDatabase.getReference("commandes");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Commandes newCommandes = dataSnapshot.getValue(Commandes.class);

                listCommande.add(newCommandes);
                Log.v("Commande1", "index=" + newCommandes.getNumeroCommande());
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

                Commandes commandesChanged = dataSnapshot.getValue(Commandes.class);

                for (Commandes commandes : listCommande) {
                    if (commandes.getNumeroCommande().equals(commandesChanged.getNumeroCommande())) {
                        int position = listCommande.indexOf(commandes);

                        listCommande.set(position, commandesChanged);
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
}
