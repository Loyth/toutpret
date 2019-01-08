package toutpret.isep.com.toutpret.orderpicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerBeignets;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerBoissons;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerCrepes;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerFruits;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerGaufres;
import toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue.FragmentOrderPickerGlaces;
import toutpret.isep.com.toutpret.products.ViewPagerAdapter;

public class OrderPicker_Catalogue extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewPager;

    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;

    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_picker__catalogue);

        tablayout = findViewById(R.id.orderpicker_tablayout_id);
        viewPager = findViewById(R.id.orderpicker_viewpager_id);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentOrderPickerFruits(), "Fruits");
        adapter.AddFragment(new FragmentOrderPickerCrepes(), "Crêpes");
        adapter.AddFragment(new FragmentOrderPickerGaufres(), "Gaufres");
        adapter.AddFragment(new FragmentOrderPickerBeignets(), "Beignets");
        adapter.AddFragment(new FragmentOrderPickerGlaces(), "Glaces");
        adapter.AddFragment(new FragmentOrderPickerBoissons(), "Boissons");

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
