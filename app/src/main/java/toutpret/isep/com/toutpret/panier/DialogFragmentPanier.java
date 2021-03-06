package toutpret.isep.com.toutpret.panier;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.commandes.CommandesActivity;
import toutpret.isep.com.toutpret.models.Commandes;
import toutpret.isep.com.toutpret.models.Product;
import toutpret.isep.com.toutpret.models.ProductPanier;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class DialogFragmentPanier extends DialogFragment {
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private Button commander;
    private TextView panierVide;
    private TextView totalAmount;
    private List<ProductPanier> products;
    private LocationManager locationManager;
    private String provider;
    private Context mContext;

    public static DialogFragmentPanier newInstance() {
        return new DialogFragmentPanier();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_panier, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        products = Panier.getListProducts();

        RecyclerView myrv = view.findViewById(R.id.panier_recyclerview_id);
        PanierRecyclerViewAdapter myAdapter = new PanierRecyclerViewAdapter(getContext(), products, this);
        myrv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        myrv.setAdapter(myAdapter);

        totalAmount = view.findViewById(R.id.panier_amount);
        totalAmount.setText(Panier.getTotalAmount() + " €");

        panierVide = view.findViewById(R.id.panier_vide_text);

        Button continuer = view.findViewById(R.id.panier_continuer);
        commander = view.findViewById(R.id.panier_commander);

        final DialogFragment parent = this;

        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.dismiss();
            }
        });

        commander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCommand(products);
                parent.dismiss();
            }
        });

        ifPanierVide();
    }

    public void ifPanierVide() {
        panierVide.setVisibility(View.GONE);
        commander.setVisibility(View.VISIBLE);


        if (products.isEmpty()) {
            totalAmount.setText("0.0 €");
            commander.setVisibility(View.GONE);
            panierVide.setVisibility(View.VISIBLE);
        }
    }

    public void sendCommand(List<ProductPanier> products) {
        final List<ProductPanier> listProducts = products;
        final DialogFragment parent = this;
        final DatabaseReference numberCommandReference = mDatabase.getReference("numberCommand");

        final Activity activity = getActivity();

        numberCommandReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long number = dataSnapshot.getValue(Long.class);

                DatabaseReference commandesDatabase = mDatabase.getReference("commandes").push();

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.FRANCE);
                Date date = new Date();

                HashMap<String, ProductPanier> map = new HashMap<>();

                for (int i = 0; i < listProducts.size(); i++) {
                    map.put("ID" + String.valueOf(i), listProducts.get(i));
                }

                Location location = getLastKnownLocation();

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    Commandes commande = new Commandes(number, map, dateFormat.format(date), auth.getUid(), "En préparation", latitude, longitude, 0.0, 0.0);
                    commandesDatabase.setValue(commande);
                    Toast.makeText(activity, "Commande envoyée !", Toast.LENGTH_LONG).show();
                    numberCommandReference.setValue(number + 1);


                    for (ProductPanier p : listProducts) {
                        decrementStock(p);
                    }

                    Panier.clean();
                } else {
                    Toast.makeText(activity, "Position GPS indisponible, commande non envoyée !", Toast.LENGTH_LONG).show();
                }

                parent.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private void decrementStock(ProductPanier p) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("products").child(p.getProductId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);

                myRef.child("stock").setValue(product.getStock() - p.getQuantity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private Location getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }
            return bestLocation;
        }

        return null;
    }
}
