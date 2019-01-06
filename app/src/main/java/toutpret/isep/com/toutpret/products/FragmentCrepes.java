package toutpret.isep.com.toutpret.products;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Product;

public class FragmentCrepes extends Fragment {
    View view;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private List<Product> listProducts;
    private ProductRecyclerViewAdapter myAdapter;


    public FragmentCrepes() {

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
        view = inflater.inflate(R.layout.fragment_crepes, container, false);

        listProducts = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        getProducts("b");

        RecyclerView myrv = view.findViewById(R.id.crepes_recyclerview_id);
        myAdapter = new ProductRecyclerViewAdapter(getContext(), listProducts);
        myrv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        myrv.setAdapter(myAdapter);

        return view;
    }

    public void getProducts(String categoryId) {
        DatabaseReference myRef = mDatabase.getReference("products");

        myRef.orderByChild("categoryId").equalTo(categoryId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Product newProduct = dataSnapshot.getValue(Product.class);
                newProduct.setId(dataSnapshot.getKey());

                listProducts.add(newProduct);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Product productChanged = dataSnapshot.getValue(Product.class);
                productChanged.setId(dataSnapshot.getKey());

                for (Product product : listProducts) {
                    if (product.getId().equals(productChanged.getId())) {
                        int position = listProducts.indexOf(product);

                        listProducts.set(position, productChanged);
                        myAdapter.notifyItemChanged(position);

                        return;
                    }
                }
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
