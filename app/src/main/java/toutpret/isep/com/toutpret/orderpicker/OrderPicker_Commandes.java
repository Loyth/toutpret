package toutpret.isep.com.toutpret.orderpicker;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.categories.CategoryRecyclerViewAdapter;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;
import toutpret.isep.com.toutpret.models.Category;
import toutpret.isep.com.toutpret.models.Commandes;
import toutpret.isep.com.toutpret.models.Product;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerBeignets;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerBoissons;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerCrepes;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerFruits;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerGaufres;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerGlaces;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCommande.FragmentCommandeEnPreparation;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCommande.FragmentCommandeValidees;
import toutpret.isep.com.toutpret.products.ViewPagerAdapter;

public class OrderPicker_Commandes extends AppCompatActivity {

    private List<Commandes> listCommande;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_picker__commandes);

        TabLayout tablayout = findViewById(R.id.orderpicker_commande_tablayout_id);
        ViewPager viewPager = findViewById(R.id.orderpicker_commande_viewpager_id);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentCommandeEnPreparation(), "A Préparer");
        adapter.AddFragment(new FragmentCommandeValidees(), "Historique");

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_livreur, menu);

        MenuItem logout = menu.findItem(R.id.livreur_action_logout);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                auth.signOut();
                Toast.makeText(getApplicationContext(), "Déconnexion réussie !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            }
        });

        return true;
    }
}
