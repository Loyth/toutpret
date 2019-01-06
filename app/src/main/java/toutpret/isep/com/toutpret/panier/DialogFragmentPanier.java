package toutpret.isep.com.toutpret.panier;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
import toutpret.isep.com.toutpret.models.Commandes;
import toutpret.isep.com.toutpret.models.ProductPanier;

public class DialogFragmentPanier extends DialogFragment {
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private Button commander;
    private TextView panierVide;
    private TextView totalAmount;
    private List<ProductPanier> products;

    public static DialogFragmentPanier newInstance() {
        return new DialogFragmentPanier();
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

                HashMap<Integer, ProductPanier> map = new HashMap<>();

                for (int i = 0; i < listProducts.size(); i++) {
                    map.put(i, listProducts.get(i));
                }

                Commandes commande = new Commandes(number, map, dateFormat.format(date), auth.getUid());

                commandesDatabase.setValue(commande);

                Toast.makeText(activity, "Commande envoyée !", Toast.LENGTH_LONG).show();

                numberCommandReference.setValue(number + 1);

                Panier.clean();
                parent.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });


    }
}
