package toutpret.isep.com.toutpret.commandes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.map.DialogFragmentMap;
import toutpret.isep.com.toutpret.models.Commandes;
import toutpret.isep.com.toutpret.models.ProductPanier;
import toutpret.isep.com.toutpret.panier.DialogFragmentPanier;

public class RecyclerViewCommandesAdapter extends RecyclerView.Adapter<RecyclerViewCommandesAdapter.MyViewHolder> {
    private Context mContext;
    private List<Commandes> mData;
    private FragmentManager manager;

    public RecyclerViewCommandesAdapter(Context mContext, List<Commandes> mData, FragmentManager manager) {
        this.mContext = mContext;
        this.mData = mData;
        this.manager = manager;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_commandes_client, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.numero_commande.setText("Commande n°" + mData.get(i).getNumeroCommande());

        int nombreArticles = 0;

        Collection<ProductPanier> values = mData.get(i).getListProducts().values();

        List<ProductPanier> productList = new ArrayList<>(values);

        for (ProductPanier product : productList) {
            nombreArticles += product.getQuantity();
        }

        myViewHolder.nombre_articles.setText("Nombre d'articles : " + nombreArticles);

        myViewHolder.date_commande.setText("Commandée le " + mData.get(i).getDate());


        myViewHolder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DialogFragmentMap();
                dialogFragment.show(manager, "Map");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView numero_commande;
        TextView nombre_articles;
        TextView date_commande;
        Button status;

        public MyViewHolder(View itemView) {
            super(itemView);

            numero_commande = itemView.findViewById(R.id.client_commande_numero);
            nombre_articles = itemView.findViewById(R.id.client_commande_nombre_articles);
            date_commande = itemView.findViewById(R.id.client_commande_date);
            status = itemView.findViewById(R.id.client_commande_status);
        }
    }
}
