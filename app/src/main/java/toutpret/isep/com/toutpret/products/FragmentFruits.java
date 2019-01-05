package toutpret.isep.com.toutpret.products;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Category;
import toutpret.isep.com.toutpret.models.Product;

public class FragmentFruits extends Fragment {
    View view;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private List<Product> listProducts;
    private TextView title;

    public FragmentFruits() {

    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products, container, false);

        title = view.findViewById(R.id.fragment_title);
        title.setText("Fruits");

        listProducts = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        getProducts("a");

        return view;
    }

    public void getProducts(String categoryId) {
        DatabaseReference myRef = mDatabase.getReference("products");

        myRef.orderByChild("categoryId").equalTo(categoryId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Product newProduct = dataSnapshot.getValue(Product.class);
                listProducts.add(newProduct);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

