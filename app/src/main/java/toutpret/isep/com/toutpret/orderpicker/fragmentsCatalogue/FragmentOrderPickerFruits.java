package toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class FragmentOrderPickerFruits extends Fragment {
    View view;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private List<Product> listProducts;
    private OrderPickerProductAdapter myAdapter;

    public FragmentOrderPickerFruits() {

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
        view = inflater.inflate(R.layout.fragment_orderpicker_fruits, container, false);

        listProducts = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        getProducts("a");

        RecyclerView myrv = view.findViewById(R.id.orderpicker_fruits_recyclerview_id);
        myAdapter = new OrderPickerProductAdapter(getContext(), listProducts);
        myrv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        myrv.setAdapter(myAdapter);

        Button add = view.findViewById(R.id.orderpicker_fruits_add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                DialogFragment dialogFragment = new DialogFragmentAddProduct();

                Bundle extras = new Bundle();
                extras.putString("categoryId", "a");
                dialogFragment.setArguments(extras);

                dialogFragment.show(manager, "AddProduct");
            }
        });

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
                Product productChanged = dataSnapshot.getValue(Product.class);
                productChanged.setId(dataSnapshot.getKey());

                for (Product product : listProducts) {
                    if (product.getId().equals(productChanged.getId())) {
                        int position = listProducts.indexOf(product);

                        listProducts.remove(position);
                        myAdapter.notifyDataSetChanged();

                        return;
                    }
                }
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
