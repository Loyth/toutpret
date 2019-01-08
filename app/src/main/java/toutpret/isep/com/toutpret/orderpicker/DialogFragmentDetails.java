package toutpret.isep.com.toutpret.orderpicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.ProductPanier;

public class DialogFragmentDetails extends DialogFragment {
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private HashMap<String, ProductPanier> products;

    public static DialogFragmentDetails newInstance() {
        return new DialogFragmentDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_commande, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        products = (HashMap<String, ProductPanier>) getArguments().getSerializable("HashMap");

        List<ProductPanier> listProducts = new ArrayList<>(products.values());

        RecyclerView myrv = view.findViewById(R.id.details_commande_recyclerview_id);
        DetailsCommandesAdapter myAdapter = new DetailsCommandesAdapter(getContext(), listProducts);
        myrv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        myrv.setAdapter(myAdapter);
    }
}
