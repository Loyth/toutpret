package toutpret.isep.com.toutpret.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import toutpret.isep.com.toutpret.Profil.ProfilActivity;
import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.commandes.CommandesActivity;
import toutpret.isep.com.toutpret.login_sinup.LoginActivity;
import toutpret.isep.com.toutpret.models.Commandes;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private static Commandes commande = null;
    private Marker markeurLivreur;
    private Marker markeurClient;

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

        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListenerGPS);
        }

    }

    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            if (commande != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                String userType = getIntent().getStringExtra("userType");

                DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("commandes").child(commande.getCommandId());

                if (userType.equals("client")) {
                    updateData.child("userLat").setValue(latitude);
                    updateData.child("userLng").setValue(longitude);
                } else {
                    updateData.child("livreurLat").setValue(latitude);
                    updateData.child("livreurLng").setValue(longitude);
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        getCommande(commande.getNumeroCommande(), mapboxMap);
    }

    private void getCommande(Long numeroCommande, MapboxMap mapboxMap) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("commandes");

        IconFactory iconFactory = IconFactory.getInstance(this);
        Icon iconClient = iconFactory.fromResource(R.drawable.towel_small);

        Icon iconLivreur = iconFactory.fromResource(R.drawable.logo_map);

        myRef.orderByChild("numeroCommande").equalTo(numeroCommande).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Commandes commande = dataSnapshot.getValue(Commandes.class);

                double clientLatitude = Double.valueOf(commande.getUserLat());
                double clientLongitude = Double.valueOf(commande.getUserLng());

                double livreurLatitude = Double.valueOf(commande.getLivreurLat());
                double livreurLongitude = Double.valueOf(commande.getLivreurLng());

                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(livreurLatitude, livreurLongitude))
                        .zoom(17)
                        .build();

                mapboxMap.setCameraPosition(position);

                markeurClient = mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(clientLatitude, clientLongitude))
                        .icon(iconClient));

                markeurLivreur = mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(livreurLatitude, livreurLongitude))
                        .icon(iconLivreur));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Commandes commande = dataSnapshot.getValue(Commandes.class);

                double clientLatitude = Double.valueOf(commande.getUserLat());
                double clientLongitude = Double.valueOf(commande.getUserLng());

                double livreurLatitude = Double.valueOf(commande.getLivreurLat());
                double livreurLongitude = Double.valueOf(commande.getLivreurLng());

                markeurClient.setPosition(new LatLng(clientLatitude, clientLongitude));
                markeurLivreur.setPosition(new LatLng(livreurLatitude, livreurLongitude));

                mapboxMap.updateMarker(markeurClient);
                mapboxMap.updateMarker(markeurLivreur);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                FirebaseAuth.getInstance().signOut();
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
