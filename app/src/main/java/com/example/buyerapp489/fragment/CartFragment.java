package com.example.buyerapp489.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyerapp489.R;
import com.example.buyerapp489.activity.CheckoutInfoActivity;
import com.example.buyerapp489.adapter.CartAdapter;
import com.example.buyerapp489.global.GlobalClass;
import com.example.buyerapp489.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    //Cart RecyclerView
    private RecyclerView cartRecyclerView;
    private List<Product> cartList;
    private CartAdapter recyclerViewAdapter;
    String currentDate,currentTime;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,orderHistory, pendingOrders;
    FirebaseAuth mAuth;
    private Button confirmOrder;
    TextView totalP,finalP;
    String uId;

    RelativeLayout emptyLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        //Tollbar
        /*Toolbar toolbar = view.findViewById(R.id.toolbar);
        //for crate home button
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        cartList = new ArrayList<>();
        Context context = inflater.getContext();
        confirmOrder = view.findViewById(R.id.confirmOrder);
        cartRecyclerView = view.findViewById(R.id.cart_recyclerView);
//        totalP = view.findViewById(R.id.product_total_price_text_cart_frag);
        finalP = view.findViewById(R.id.total_price_text_cart_frag);

        emptyLayout = view.findViewById(R.id.emptyLayout);


        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        pendingOrders = FirebaseDatabase.getInstance().getReference().child("Users");




        cartRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewAdapter = new CartAdapter(new CartAdapter.UpdateTotalPrice() {
            @Override
            public void updatePrice(float price) {
                //totalP.setText("Price : "+price);
                finalP.setText("Total : "+(price));
            }
        }, GlobalClass.cartProducts, context);
        cartRecyclerView.setAdapter(recyclerViewAdapter);

//        if (GlobalClass.cartProducts.size() == 0){
//            emptyImage.setVisibility(View.VISIBLE);
//        }else {
//            emptyImage.setVisibility(View.GONE);
//        }

        //totalP.setText("Price : "+GlobalClass.totalPrice);
        finalP.setText("Total "+ (GlobalClass.totalPrice));

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmOrder();
                if (GlobalClass.cartProducts.size() > 0) {
                    startActivity(new Intent(getActivity(), CheckoutInfoActivity.class));
                }
            }
        });


        return view;
    }

    private void confirmOrder() {
        if (GlobalClass.cartProducts.size() > 0){
            for (int i = 0; i < GlobalClass.cartProducts.size(); i++) {
                Product product = GlobalClass.cartProducts.get(i);
                product.setOrder_status("pending");
                //Save In Seller
                pendingOrders.child(product.getSellerId()).child("SellerPendingOrder").child(mAuth.getCurrentUser().getUid()).child(product.getProduct_key()).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Save In Buyer
                        pendingOrders.child(mAuth.getCurrentUser().getUid()).child("BuyerPendingOrder").child(product.getSellerId()).child(product.getProduct_key()).setValue(product);
                    }
                });

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (GlobalClass.cartProducts.size() == 0){
            emptyLayout.setVisibility(View.VISIBLE);
        }else {
            emptyLayout.setVisibility(View.GONE);
        }
    }
}
