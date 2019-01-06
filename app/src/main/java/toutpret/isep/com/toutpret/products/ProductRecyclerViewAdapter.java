package toutpret.isep.com.toutpret.products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Product;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Product> mData;
    ArrayList<Integer> imgs;

    public ProductRecyclerViewAdapter(Context mContext, List<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;

        imgs = new ArrayList<>();
        imgs.add(R.drawable.apple);
        imgs.add(R.drawable.viennoiserie);
        imgs.add(R.drawable.boissons);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_product, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_product_title.setText(mData.get(i).getName());
        myViewHolder.img_product_thumbnail.setImageResource(imgs.get(mData.get(i).getThumbnail()));
        myViewHolder.tv_product_quantity.setText(String.valueOf(mData.get(i).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_product_title;
        ImageView img_product_thumbnail;
        TextView tv_product_quantity;
        Button b_product_minus;
        Button b_product_plus;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_product_title = itemView.findViewById(R.id.product_title_id);
            img_product_thumbnail = itemView.findViewById(R.id.product_img_id);
            tv_product_quantity = itemView.findViewById(R.id.product_quantity_id);
            b_product_minus = itemView.findViewById(R.id.product_minus_id);
            b_product_plus = itemView.findViewById(R.id.product_plus_id);
        }
    }
}
