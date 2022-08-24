package com.example.buyerapp489.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buyerapp489.R;
import com.example.buyerapp489.activity.OrderDetailsActivity;
import com.example.buyerapp489.model.Product;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    List<Product> orderList;
    Context context;

    public OrderListAdapter(List<Product> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.sample_ordered_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        float total = Integer.parseInt(orderList.get(position).getProduct_price()) * Float.parseFloat(orderList.get(position).getProduct_quantity());

        holder.product_name.setText(orderList.get(position).getProduct_name());
        holder.product_price.setText("Price: "+orderList.get(position).getProduct_price());
        holder.total_price.setText("Total: "+ total);
        holder.quantity.setText("Qty: "+orderList.get(position).getProduct_quantity());
        holder.shop_name.setText(orderList.get(position).getProduct_shop());
        try{
            holder.order_id.setText("Order ID : " + orderList.get(position).getOrder_id());
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if (orderList.get(position).getOrder_status() != null) {
            holder.status.setText(orderList.get(position).getOrder_status());
        }

//        Glide.with(context)
//                .load(orderList.get(position).getProduct_image())
//                .placeholder(R.drawable.logo_reline)
//                .into(holder.picture);
        //Image
        if (orderList.get(position).getProductImageArray()!=null) {
            Glide.with(context)
                    .load(orderList.get(position).getProductImageArray().img0)
                    .placeholder(R.drawable.img_loading)
                    .centerCrop()
                    .into(holder.picture);
        }

        holder.sample_ordered_product_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("product", orderList.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView product_name, total_price, product_price, quantity, status, shop_name, order_id;
        ImageView picture;
        CardView sample_ordered_product_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            total_price = itemView.findViewById(R.id.total_price_text);
            product_price=itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity_text);
            picture = itemView.findViewById(R.id.product_image);
            status = itemView.findViewById(R.id.order_status_text);
            shop_name = itemView.findViewById(R.id.store_name_text);
            order_id = itemView.findViewById(R.id.order_id);
            sample_ordered_product_layout = itemView.findViewById(R.id.sample_ordered_product_layout);
        }

    }
}
