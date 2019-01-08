package toutpret.isep.com.toutpret.livreur;

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
import toutpret.isep.com.toutpret.commandes.RecyclerViewCommandesAdapter;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;
import toutpret.isep.com.toutpret.models.Commandes;
import toutpret.isep.com.toutpret.products.FragmentBeignets;
import toutpret.isep.com.toutpret.products.FragmentBoissons;
import toutpret.isep.com.toutpret.products.FragmentCrepes;
import toutpret.isep.com.toutpret.products.FragmentFruits;
import toutpret.isep.com.toutpret.products.FragmentGaufres;
import toutpret.isep.com.toutpret.products.FragmentGlaces;
import toutpret.isep.com.toutpret.products.ViewPagerAdapter;

public class LivreurActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewPager;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private List<Commandes> listCommandes;
    private ViewPagerAdapter myAdapter;
    private FragmentCommandesPretes commandesPretes;
    private FragmentCommandesLivraison commandesLivraison;
    private FragmentCommandesLivrees commandesLivrees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livreur);

        tablayout = findViewById(R.id.livreur_tablayout_id);
        viewPager = findViewById(R.id.livreur_viewpager_id);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Les commandes");
        actionBar.setElevation(0);

        myAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        commandesLivraison = new FragmentCommandesLivraison();
        commandesLivrees = new FragmentCommandesLivrees();
        commandesPretes = new FragmentCommandesPretes();

        myAdapter.AddFragment(commandesPretes, "Prêtes");
        myAdapter.AddFragment(commandesLivraison, "En livraison");
        myAdapter.AddFragment(commandesLivrees, "Livrées");

        viewPager.setAdapter(myAdapter);
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
