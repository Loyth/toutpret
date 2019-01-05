package toutpret.isep.com.toutpret;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import toutpret.isep.com.toutpret.categories.CategoriesActivity;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;
import toutpret.isep.com.toutpret.models.User;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
        } else {
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
        }
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
