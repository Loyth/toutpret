package toutpret.isep.com.toutpret.orderpicker;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import toutpret.isep.com.toutpret.ViewPagerAdapter;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;
import toutpret.isep.com.toutpret.models.Category;
import toutpret.isep.com.toutpret.products.FragmentBeignets;
import toutpret.isep.com.toutpret.products.FragmentBoissons;
import toutpret.isep.com.toutpret.products.FragmentCrepes;
import toutpret.isep.com.toutpret.products.FragmentFruits;
import toutpret.isep.com.toutpret.products.FragmentGaufres;
import toutpret.isep.com.toutpret.products.FragmentGlaces;

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

        return true;
    }
}
