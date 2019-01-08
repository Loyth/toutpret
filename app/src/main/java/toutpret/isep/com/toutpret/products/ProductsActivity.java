package toutpret.isep.com.toutpret.products;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


import toutpret.isep.com.toutpret.Profil.ProfilActivity;
import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.commandes.CommandesActivity;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;
import toutpret.isep.com.toutpret.panier.DialogFragmentPanier;

public class ProductsActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewPager;

    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;

    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        tablayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tout Prêt");
        actionBar.setElevation(0);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentFruits(), "Fruits");
        adapter.AddFragment(new FragmentCrepes(), "Crêpes");
        adapter.AddFragment(new FragmentGaufres(), "Gaufres");
        adapter.AddFragment(new FragmentBeignets(), "Beignets");
        adapter.AddFragment(new FragmentGlaces(), "Glaces");
        adapter.AddFragment(new FragmentBoissons(), "Boissons");

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);

        String category = getIntent().getStringExtra("category");

        switch (category) {
            case "Fruits":
                tablayout.getTabAt(0).select();
                break;
            case "Crêpes":
                tablayout.getTabAt(1).select();
                break;
            case "Gaufres":
                tablayout.getTabAt(2).select();
                break;
            case "Beignets":
                tablayout.getTabAt(3).select();
                break;
            case "Glaces":
                tablayout.getTabAt(4).select();
                break;
            case "Boissons":
                tablayout.getTabAt(5).select();
                break;
            default:
                break;
        }

        FloatingActionButton panier = findViewById(R.id.products_panier);

        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                DialogFragment dialogFragment = new DialogFragmentPanier();
                dialogFragment.show(fm, "Mon panier");
            }
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

        MenuItem commandes = menu.findItem(R.id.action_take_away);
        MenuItem profile = menu.findItem(R.id.action_sun);

        commandes.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(getApplicationContext(), CommandesActivity.class));
                return true;
            }
        });

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
