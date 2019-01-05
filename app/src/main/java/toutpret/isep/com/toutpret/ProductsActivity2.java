package toutpret.isep.com.toutpret;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class ProductsActivity2 extends AppCompatActivity {



    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products2);

        tablayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tout Prêt");

        ArrayList<String> listCategory = new ArrayList<String>();

        listCategory.add("Prod1");
        listCategory.add("Prod2");
        listCategory.add("Prod4");
        listCategory.add("Test");
        listCategory.add("Prod2");


        for(int i = 0; i < listCategory.size(); i++)
        {
            adapter.AddFragment(new FragmentProduct(),"" + listCategory.get(i));
        }

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }


}
