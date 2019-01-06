package toutpret.isep.com.toutpret.panier;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.ProductPanier;

public class PanierRecyclerViewAdapter extends RecyclerView.Adapter<PanierRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<ProductPanier> mData;
    private DialogFragmentPanier panier;
    ArrayList<Integer> imgs;

    public PanierRecyclerViewAdapter(Context mContext, List<ProductPanier> mData, DialogFragmentPanier panier) {
        this.mContext = mContext;
        this.mData = mData;
        this.panier = panier;

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
        view = mInflater.inflate(R.layout.cardview_item_panier, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        DecimalFormat df = new DecimalFormat("#.#");

        myViewHolder.tv_panier_title.setText(mData.get(i).getName());
        myViewHolder.tv_panier_price_unite.setText("Unité : " + String.valueOf(mData.get(i).getPrice()) + " €");
        myViewHolder.tv_panier_price_total.setText("Total : " + String.valueOf(df.format(mData.get(i).getPrice() * mData.get(i).getQuantity()) + " €"));
        myViewHolder.tv_panier_quantity.setText("Quantité : " + String.valueOf(mData.get(i).getQuantity()));

        final int position = i;
        final PanierRecyclerViewAdapter adapter = this;

        myViewHolder.b_panier_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Panier.remove(mData.get(position));
               adapter.notifyDataSetChanged();

               panier.ifPanierVide();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_panier_title;
        TextView tv_panier_price_unite;
        TextView tv_panier_quantity;
        TextView tv_panier_price_total;
        Button b_panier_trash;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_panier_title = itemView.findViewById(R.id.panier_title_id);
            tv_panier_price_unite = itemView.findViewById(R.id.panier_price_unite_id);
            tv_panier_quantity = itemView.findViewById(R.id.panier_quantity_id);
            tv_panier_price_total = itemView.findViewById(R.id.panier_price_total_id);
            b_panier_trash = itemView.findViewById(R.id.panier_trash);
        }
    }
}
