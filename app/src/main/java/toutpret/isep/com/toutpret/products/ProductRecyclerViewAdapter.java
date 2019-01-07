package toutpret.isep.com.toutpret.products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import toutpret.isep.com.toutpret.models.ProductPanier;
import toutpret.isep.com.toutpret.panier.Panier;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Product> mData;
    ArrayList<Integer> imgs;

    public ProductRecyclerViewAdapter(Context mContext, List<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;

        imgs = new ArrayList<>();
        imgs.add(R.drawable.fruits);
        imgs.add(R.drawable.crepe);
        imgs.add(R.drawable.gaufres);
        imgs.add(R.drawable.beignet);
        imgs.add(R.drawable.glace);
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

        final int position = i;

        myViewHolder.b_product_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ProductPanier> listPanierProducts = Panier.getListProducts();

                boolean productAlreadyAdded = false;
                int currentQuantity = 0;


                for (ProductPanier panierProduct : listPanierProducts) {
                    if (panierProduct.getId().equals(mData.get(position).getId())) {
                        productAlreadyAdded = true;
                        currentQuantity = panierProduct.getQuantity();
                        break;
                    }
                }

                if (productAlreadyAdded) {
                    Panier.update(new ProductPanier(mData.get(position).getId(), mData.get(position).getName(), currentQuantity + 1, mData.get(position).getPrice()), false);
                } else {
                    Panier.add(new ProductPanier(mData.get(position).getId(), mData.get(position).getName(), 1, mData.get(position).getPrice()));
                }
            }
        });

        myViewHolder.b_product_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ProductPanier> listPanierProducts = Panier.getListProducts();

                for (ProductPanier panierProduct : listPanierProducts) {
                    if (panierProduct.getId().equals(mData.get(position).getId())) {

                        panierProduct.setQuantity(panierProduct.getQuantity() - 1);

                        if (panierProduct.getQuantity() > 0) {
                            Panier.update(panierProduct, true);
                        } else {
                            Panier.remove(panierProduct);
                        }

                        return;
                    }
                }
            }
        });
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
