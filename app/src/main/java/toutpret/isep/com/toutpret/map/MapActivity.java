package toutpret.isep.com.toutpret.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.commandes.CommandesActivity;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;
import toutpret.isep.com.toutpret.models.Commandes;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private static Commandes commande;

    public static void setCommande(Commandes commande) {
        MapActivity.commande = commande;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, "pk.eyJ1IjoiaGljaGVtYWRkIiwiYSI6ImNqcWw4aGN4ZjB6NG40M25uMmgxYjNzZzIifQ.h8eqs-0JnQoOFVYIOzl4dw");

        setContentView(R.layout.activity_map);

        getSupportActionBar().setTitle("Suivre votre commande");

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        IconFactory iconFactory = IconFactory.getInstance(this);
        Icon iconClient = iconFactory.fromResource(R.drawable.towel_small);

        Icon iconLivreur = iconFactory.fromResource(R.drawable.logo_map);

        int clientLatitude = Double.valueOf(commande.getUserLat()).intValue();
        int clientLongitude = Double.valueOf(commande.getUserLng()).intValue();

        int livreurLatitude = Double.valueOf(commande.getLivreurLat()).intValue();
        int livreurLongitude = Double.valueOf(commande.getLivreurLng()).intValue();

        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(livreurLatitude, livreurLongitude))
                .zoom(17)
                .build();

        mapboxMap.setCameraPosition(position);

        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(clientLatitude, clientLongitude))
                .icon(iconClient));

        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(livreurLatitude, livreurLongitude))
                .icon(iconLivreur));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem logout = menu.findItem(R.id.action_logout);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Déconnexion réussie !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            }
        });

        MenuItem commandes = menu.findItem(R.id.action_take_away);

        commandes.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(getApplicationContext(), CommandesActivity.class));
                return true;
            }
        });

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
