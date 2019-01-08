package toutpret.isep.com.toutpret.orderpicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;

public class OrderPickerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_picker);

        Button button_commande = findViewById(R.id.button_commande);
        Button button_catalogue = findViewById(R.id.button_catalogue);

        button_commande.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent commande = new Intent(getApplicationContext(), OrderPicker_Commandes.class);
                startActivity(commande);
            }

        });

        button_catalogue.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent catalogue = new Intent(getApplicationContext(), OrderPicker_Catalogue.class);
                startActivity(catalogue);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_livreur, menu);

        MenuItem logout = menu.findItem(R.id.livreur_action_logout);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Déconnexion réussie !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            }
        });

        return true;
    }
}
