package toutpret.isep.com.toutpret.orderpicker.fragmentsCatalogue;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Product;

public class OrderPickerProductAdapter extends RecyclerView.Adapter<OrderPickerProductAdapter.MyViewHolder> {
    private Context mContext;
    private List<Product> mData;

    public OrderPickerProductAdapter(Context mContext, List<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_orderpicker_product, viewGroup, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Picasso.get().load(mData.get(i).getImageURL()).into(myViewHolder.img);

        myViewHolder.title.setText(mData.get(i).getName());
        myViewHolder.price.setText("Prix : " + mData.get(i).getPrice() + " €");
        myViewHolder.quantity.setText("Quantité : " + mData.get(i).getStock());

        myViewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mData.get(i).getStock() > 0) {
                    DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("products").child(mData.get(i).getId());
                    updateData.child("stock").setValue(mData.get(i).getStock() - 1);
                }
            }
        });

        myViewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("products").child(mData.get(i).getId());
                updateData.child("stock").setValue(mData.get(i).getStock() + 1);
            }
        });

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("products").child(mData.get(i).getId());
                updateData.removeValue();
            }
        });
    }


    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        TextView price;
        TextView quantity;
        Button minus;
        Button plus;
        Button delete;

        public MyViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.orderpicker_product_img_id);
            title = itemView.findViewById(R.id.orderpicker_product_title_id);
            price = itemView.findViewById(R.id.orderpicker_product_price_id);
            quantity = itemView.findViewById(R.id.orderpicker_product_quantity_id);
            minus = itemView.findViewById(R.id.orderpicker_product_minus_id);
            plus = itemView.findViewById(R.id.orderpicker_product_plus_id);
            delete = itemView.findViewById(R.id.orderpicker_product_delete_id);
        }
    }
}
