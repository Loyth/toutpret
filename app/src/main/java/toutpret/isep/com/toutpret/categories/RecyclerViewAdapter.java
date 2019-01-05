package toutpret.isep.com.toutpret.categories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import toutpret.isep.com.toutpret.R;
import toutpret.isep.com.toutpret.models.Category;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Category> mData;
    ArrayList<Integer> imgs;

    public RecyclerViewAdapter(Context mContext, List<Category> mData) {
        this.mContext = mContext;
        this.mData = mData;

        imgs = new ArrayList<>();
        imgs.add(R.drawable.apple);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_category, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_category_title.setText(mData.get(i).getName());
        myViewHolder.img_category_thumbnail.setImageResource(imgs.get(mData.get(i).getThumbnail()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_category_title;
        ImageView img_category_thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_category_title = itemView.findViewById(R.id.category_title_id);
            img_category_thumbnail = itemView.findViewById(R.id.category_img_id);
        }
    }
}
