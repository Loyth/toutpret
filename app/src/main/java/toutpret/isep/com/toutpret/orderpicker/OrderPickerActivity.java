package toutpret.isep.com.toutpret.orderpicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
}
