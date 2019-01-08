package toutpret.isep.com.toutpret.orderpicker;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Commandes;
import toutpret.isep.com.toutpret.models.Product;
import toutpret.isep.com.toutpret.models.ProductPanier;
import toutpret.isep.com.toutpret.models.User;

public class DetailsCommandesAdapter extends RecyclerView.Adapter<DetailsCommandesAdapter.MyViewHolder> {
    private Context mContext;
    private List<ProductPanier> mData;

    public DetailsCommandesAdapter(Context mContext, List<ProductPanier> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_details_product, viewGroup, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(mData.get(i).getName());
        myViewHolder.quantity.setText("Quantité : " + mData.get(i).getQuantity());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView quantity;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.details_commande_title_id);
            quantity = itemView.findViewById(R.id.details_commande_quantity_id);
        }
    }
}
