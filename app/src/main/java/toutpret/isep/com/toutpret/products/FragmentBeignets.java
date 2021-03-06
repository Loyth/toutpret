package toutpret.isep.com.toutpret.products;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import toutpret.isep.com.toutpret.models.Product;
import toutpret.isep.com.toutpret.models.ProductPanier;
import toutpret.isep.com.toutpret.panier.DialogFragmentPanier;
import toutpret.isep.com.toutpret.panier.Panier;

public class FragmentBeignets extends Fragment implements FragmentInterface {
    View view;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private List<Product> listProducts;
    private ProductRecyclerViewAdapter myAdapter;
    private FloatingActionButton panier;

    public FragmentBeignets() {
        Panier.addFragment(this);
        listProducts = new ArrayList<>();
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
        view = inflater.inflate(R.layout.fragment_beignets, container, false);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        listProducts = new ArrayList<>();

        getProducts("d");

        RecyclerView myrv = view.findViewById(R.id.beignets_recyclerview_id);
        myAdapter = new ProductRecyclerViewAdapter(getContext(), listProducts);
        myrv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        myrv.setAdapter(myAdapter);

        /*panier = view.findViewById(R.id.beignets_panier);

        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment dialogFragment = new DialogFragmentPanier();
                dialogFragment.show(ft, "Mon panier");
            }
        });*/

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

                syncQuantity();
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

    @Override
    public void checkIfProductIsMine_add_update(ProductPanier p) {
        for (Product product : listProducts) {
            if (product.getId().equals(p.getId())) {
                int position = listProducts.indexOf(product);

                listProducts.get(position).setQuantity(p.getQuantity());
                myAdapter.notifyItemChanged(position);
            }
        }
    }

    @Override
    public void checkIfProductIsMine_remove(ProductPanier p) {
        for (Product product : listProducts) {
            if (product.getId().equals(p.getId())) {
                int position = listProducts.indexOf(product);

                listProducts.get(position).setQuantity(0);
                myAdapter.notifyItemChanged(position);
            }
        }
    }

    public void syncQuantity() {
        for (ProductPanier productPanier : Panier.getListProducts()) {
            for (Product product : listProducts) {
                if (productPanier.getId().equals(product.getId())) {
                    int position = listProducts.indexOf(product);
                    listProducts.get(position).setQuantity(productPanier.getQuantity());
                    myAdapter.notifyItemChanged(position);
                    break;
                }
            }
        }
    }

    @Override
    public void resetProducts() {
        for (Product product : listProducts) {
            int position = listProducts.indexOf(product);

            listProducts.get(position).setQuantity(0);
            myAdapter.notifyItemChanged(position);
        }
    }
}
