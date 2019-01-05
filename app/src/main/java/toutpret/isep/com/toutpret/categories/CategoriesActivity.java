package toutpret.isep.com.toutpret.categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;
import toutpret.isep.com.toutpret.models.Category;

public class CategoriesActivity extends AppCompatActivity {
    List<Category> listCategory;
    FirebaseDatabase mDatabase;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        listCategory = new ArrayList<>();
        listCategory.add(new Category("Pomme", new ArrayList(), R.drawable.apple));
        listCategory.add(new Category("Poire", new ArrayList(), R.drawable.apple));
        listCategory.add(new Category("Poire", new ArrayList(), R.drawable.apple));
        listCategory.add(new Category("Poire", new ArrayList(), R.drawable.apple));
        listCategory.add(new Category("Poire", new ArrayList(), R.drawable.apple));

        RecyclerView myrv = findViewById(R.id.category_recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, listCategory);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
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
