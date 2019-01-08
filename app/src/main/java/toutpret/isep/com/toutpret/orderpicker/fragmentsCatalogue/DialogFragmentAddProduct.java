package toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Product;
import toutpret.isep.com.toutpret.models.ProductPanier;

public class DialogFragmentAddProduct extends DialogFragment {
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private HashMap<String, ProductPanier> products;

    public static DialogFragmentAddProduct newInstance() {
        return new DialogFragmentAddProduct();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        EditText nom = view.findViewById(R.id.dialog_product_name);
        EditText price = view.findViewById(R.id.dialog_product_price);
        EditText img_url = view.findViewById(R.id.dialog_product_img_url);

        Button add = view.findViewById(R.id.dialog_product_button_add);

        String categoryId = getArguments().getString("categoryId");

        final DialogFragmentAddProduct parent = this;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference productDatabase = FirebaseDatabase.getInstance().getReference("products").push();

                long stock = 0;

                Product product = new Product(nom.getText().toString(), stock, categoryId, img_url.getText().toString(), Double.valueOf(price.getText().toString()));

                productDatabase.setValue(product);

                Toast.makeText(getActivity(), "Produit ajouté !", Toast.LENGTH_SHORT).show();
                parent.dismiss();
            }
        });
    }
}
