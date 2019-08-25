package com.airit.test.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airit.test.Models.HomeModel;
import com.airit.test.R;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Bind the data to reyclerview
 */
public class HomeDetailsAdapter extends RecyclerView.Adapter<HomeDetailsAdapter.HomeViewHolder> {

    private List<HomeModel> lstHome;

    public HomeDetailsAdapter(List<HomeModel> lstHome) {
        this.lstHome = lstHome;
    }
    @NonNull
    @Override
    public HomeDetailsAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items, parent, false);
        return new HomeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HomeDetailsAdapter.HomeViewHolder holder, int position) {
        holder.tvTitle.setText(lstHome.get(position).getName());
        holder.tvPhone.setText("Released: " + lstHome.get(position).getPhone());
        Picasso.get().load(lstHome.get(position).getImage()).into(holder.imgMoviePoster);
    }

    @Override
    public int getItemCount() {
        return lstHome.size();
    }

    public HomeModel getItemAt(int adapterPosition) {
        return this.lstHome.get(adapterPosition);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvPhone;
        private ImageView imgMoviePoster;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            imgMoviePoster = itemView.findViewById(R.id.img_poster);
        }
    }
}
