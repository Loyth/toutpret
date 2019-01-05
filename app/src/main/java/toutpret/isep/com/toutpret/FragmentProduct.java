package toutpret.isep.com.toutpret;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import toutpret.isep.com.toutpret.models.Category;
import toutpret.isep.com.toutpret.models.Product;

public class FragmentProduct extends Fragment {

    View view;
    private String categoryID;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private List<Product> listProducts;


    public FragmentProduct() {
    }


    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products,container,false);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        listProducts = new ArrayList<>();
        getProducts();
        return view;
    }

    private void getProducts () {
        DatabaseReference myRef = mDatabase.getReference("products");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Product newProduct = dataSnapshot.getValue(Product.class);
                listProducts.add(newProduct);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    }
}
