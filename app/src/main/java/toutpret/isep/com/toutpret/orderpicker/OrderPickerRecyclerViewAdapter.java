package toutpret.isep.com.toutpret.orderpicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;

import java.util.Date;
import java.util.List;
import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Commandes;
import toutpret.isep.com.toutpret.models.Product;

public class OrderPickerRecyclerViewAdapter extends RecyclerView.Adapter<OrderPickerRecyclerViewAdapter.MyViewHolder>  {


    private Context mContext;
    private List<Commandes> mData;

    Date currentTime = Calendar.getInstance().getTime();

    public OrderPickerRecyclerViewAdapter(Context mContext, List<Commandes>  mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_commande , viewGroup, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        myViewHolder.tv_category_titleNC.setText("Commande n° "+mData.get(i).getNumeroCommande());
        //myViewHolder.tv_category_titleNA.setText(" Nombre d'articles "+mData.get(i).getNbArticles());
        myViewHolder.tv_category_titleDate.setText("Commandé il y a : " +mData.get(i).getDate());


    }


    @Override
    public int getItemCount() {

        return mData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_category_titleNC;
        TextView tv_category_titleNA;
        TextView tv_category_titleDate;
        Button etat_button;
        Button etat_details;



        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category_titleNC = itemView.findViewById(R.id.orderpicker_numeroCommande);
            tv_category_titleNA = itemView.findViewById(R.id.orderpicker_nbArticles);
            tv_category_titleDate = itemView.findViewById(R.id.orderpicker_dateCommande);

            etat_button = itemView.findViewById(R.id.button_etat);
            etat_details= itemView.findViewById(R.id.button_details);


        }
    }
}
