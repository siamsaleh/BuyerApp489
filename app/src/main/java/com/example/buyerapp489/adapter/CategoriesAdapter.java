package com.example.buyerapp489.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buyerapp489.R;
import com.example.buyerapp489.model.Categories;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    List<Categories> categoriesList;
    FragmentActivity fragmentActivity;
    Context context;

    public CategoriesAdapter(List<Categories> categoriesList, FragmentActivity fragmentActivity) {
        this.categoriesList = categoriesList;
        this.fragmentActivity = fragmentActivity;
    }

    public CategoriesAdapter(List<Categories> categoriesList, Context context) {
        this.categoriesList = categoriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.categories_view_container,parent,false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        holder.cat_name.setText(categoriesList.get(position).getName());
        Glide
                .with(context)
                .load(categoriesList.get(position).getImage())
                .placeholder(R.drawable.img_alibaba)
                .centerCrop().into(holder.image);

        //Product showing
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView cat_name;
        LinearLayout linearLayout;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.icon_view);
            cat_name = itemView.findViewById(R.id.text_cat);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}

