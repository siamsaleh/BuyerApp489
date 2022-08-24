package com.example.buyerapp489.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buyerapp489.R;
import com.example.buyerapp489.global.GlobalClass;
import com.example.buyerapp489.model.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    public interface UpdateTotalPrice{
        void updatePrice(float price);
    }
    private UpdateTotalPrice updateTotalPrice;


    List<Product> cartList;
    Context context;


    public CartAdapter(CheckInfoProductAdapter.UpdateTotalPrice updateTotalPrice, List<Product> categoriesList, Context context) {
        this.cartList = categoriesList;
        this.context = context;
    }

    public CartAdapter(UpdateTotalPrice updateTotalPrice, List<Product> categoriesList, Context context) {
        this.updateTotalPrice = updateTotalPrice;
        this.cartList = categoriesList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.shopping_cart_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.product_name.setText(cartList.get(position).getProduct_name());
        holder.product_price.setText("Price: "+cartList.get(position).getProduct_price());
        float pp = Integer.parseInt((cartList.get(position).getProduct_price()));
        final int[] qu = {Integer.parseInt((cartList.get(position).getProduct_quantity()))};
        holder.quantity.setText(""+ qu[0]);
        holder.total_price.setText("Total Price: "+(pp* qu[0]));

        //Image
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
            Log.d("TESTING", "onDataChange: "+cartList.get(position).getProductImageArray().img0);
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qu[0]++;
                holder.total_price.setText("Total Price: "+(pp* qu[0]));
                holder.quantity.setText(""+ qu[0]);
                GlobalClass.cartProducts.get(position).setProduct_quantity(Float.toString(qu[0]));
                GlobalClass.totalPrice = GlobalClass.totalPrice + pp;
                updateTotalPrice.updatePrice(GlobalClass.totalPrice);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qu[0] > Integer.parseInt(cartList.get(position).getMinimum_order())) {
                    qu[0]--;
                    holder.total_price.setText("Total Price: " + (pp * qu[0]));
                    holder.quantity.setText("" + qu[0]);
                    GlobalClass.cartProducts.get(position).setProduct_quantity(Float.toString(qu[0]));
                    GlobalClass.totalPrice = GlobalClass.totalPrice - pp;
                    updateTotalPrice.updatePrice(GlobalClass.totalPrice);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float productQuantity = Float.parseFloat(GlobalClass.cartProducts.get(position).getProduct_quantity());
                float productPrice = Float.parseFloat(GlobalClass.cartProducts.get(position).getProduct_price());
                float totalItemPrice = productQuantity*productPrice;
                GlobalClass.totalPrice = GlobalClass.totalPrice - totalItemPrice;
                GlobalClass.cartProducts.remove(cartList.get(position));
                updateTotalPrice.updatePrice(GlobalClass.totalPrice);
                Toast.makeText(context, "Total Price" + GlobalClass.totalPrice, Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });




    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,total_price,product_price,quantity;
        ImageView picture;
        Button remove, add, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            total_price = itemView.findViewById(R.id.total_price_text);
            product_price=itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity_text);
            picture = itemView.findViewById(R.id.product_image);
            remove = itemView.findViewById(R.id.cart_remove);
            add = itemView.findViewById(R.id.cart_add);
            delete=itemView.findViewById(R.id.delete_button);
        }
    }
}
