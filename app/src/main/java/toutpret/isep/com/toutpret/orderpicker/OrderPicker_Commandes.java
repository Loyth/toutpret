package toutpret.isep.com.toutpret.orderpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.categories.RecyclerViewAdapter;
import toutpret.isep.com.toutpret.models.Category;

public class OrderPicker_Commandes extends AppCompatActivity {

    private List<Category> listCategory;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth auth;
    private RecyclerViewAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_picker__commandes);

        RecyclerView myrv = findViewById(R.id.category_recyclerview_id);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();


        myAdapter = new RecyclerViewAdapter(this, listCategory);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
    }
}
