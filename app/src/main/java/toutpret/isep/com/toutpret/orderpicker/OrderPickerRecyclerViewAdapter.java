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
import java.util.List;
import toutpret.isep.com.toutpret.R;

public class OrderPickerRecyclerViewAdapter extends RecyclerView.Adapter<OrderPickerRecyclerViewAdapter.MyViewHolder>  {


    private Context mContext;
    private List<String> mData;

    public OrderPickerRecyclerViewAdapter(Context mContext, List mData) {
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


        myViewHolder.tv_category_titleNC.setText(mData.get(i));
        myViewHolder.tv_category_titleNA.setText(mData.get(i));

    }


    @Override
    public int getItemCount() {

        return mData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_category_titleNC;
        TextView tv_category_titleNA;
        Button etat_button;
        Button etat_details;



        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category_titleNC = itemView.findViewById(R.id.orderpicker_numeroCommande);
            tv_category_titleNA = itemView.findViewById(R.id.orderpicker_nbArticles);
            etat_button = itemView.findViewById(R.id.button_etat);
            etat_details= itemView.findViewById(R.id.button_details);


        }
    }
}
