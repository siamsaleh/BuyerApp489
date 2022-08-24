package com.example.buyerapp489.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buyerapp489.R;
import com.example.buyerapp489.model.Product;

import java.util.List;

public class CheckInfoProductAdapter extends RecyclerView.Adapter<CheckInfoProductAdapter.ViewHolder> {
    public interface UpdateTotalPrice{
        void updatePrice(float price);
    }
    private UpdateTotalPrice updateTotalPrice;


    List<Product> cartList;
    Context context;


    public CheckInfoProductAdapter(List<Product> categoriesList, Context context) {
        this.cartList = categoriesList;
        this.context = context;
    }

    public CheckInfoProductAdapter(UpdateTotalPrice updateTotalPrice, List<Product> categoriesList, Context context) {
        this.updateTotalPrice = updateTotalPrice;
        this.cartList = categoriesList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.shopping_check_info_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.product_name.setText(cartList.get(position).getProduct_name());
        holder.product_price.setText("Price: "+cartList.get(position).getProduct_price());
        float pp = Integer.parseInt((cartList.get(position).getProduct_price()));
        final float[] qu = {Float.parseFloat((cartList.get(position).getProduct_quantity()))};
        holder.quantity.setText("Quantity: "+ qu[0]);
        holder.total_price.setText("Total Price: "+(pp* qu[0]));
        holder.shop.setText("Shop: "+(cartList.get(position).getProduct_shop()));

//        Glide.with(context)
//                .load(cartList.get(position).getProduct_image())
//                .placeholder(R.drawable.logo_reline)
//                .into(holder.picture);
        //Image
        if (cartList.get(position).getProductImageArray()!=null) {
            Glide.with(context)
                    .load(cartList.get(position).getProductImageArray().img0)
                    .placeholder(R.drawable.img_loading)
                    .centerCrop()
                    .into(holder.picture);
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,total_price,product_price,quantity,shop;
        ImageView picture;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            total_price = itemView.findViewById(R.id.total_price_text);
            product_price=itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            picture = itemView.findViewById(R.id.product_image);
            shop = itemView.findViewById(R.id.shop);
        }
    }
}
