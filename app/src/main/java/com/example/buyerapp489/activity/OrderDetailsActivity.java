package com.example.buyerapp489.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.buyerapp489.R;
import com.example.buyerapp489.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderDetailsActivity extends AppCompatActivity {

    //Variable
    TextView order_id, order_date, total_amount, price, qty, payment_method;
    Button cancelBT, packingBT, shippedBT, deliveredBT, btComplete;
    Product product;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting product
        product = (Product) getIntent().getSerializableExtra("product");

        //Initializing firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        //Initialize Variable
        order_id = findViewById(R.id.order_id);
        order_date = findViewById(R.id.order_date);
        total_amount = findViewById(R.id.total_id);
        price = findViewById(R.id.price_id);
        qty = findViewById(R.id.qty_id);
        payment_method = findViewById(R.id.payment_method_id);
        cancelBT = findViewById(R.id.btCancel);
        packingBT = findViewById(R.id.btPackaging);
        shippedBT = findViewById(R.id.btShipped);
        deliveredBT = findViewById(R.id.btDelivered);
        btComplete = findViewById(R.id.btComplete);

        order_id.setText(product.getOrder_id());
        order_date.setText(product.getOrder_date());
        //total_amount.setText("৳" + Integer.parseInt(product.getProduct_quantity()) * Float.parseFloat(product.getProduct_price()) + "");
        float total = Integer.parseInt(product.getProduct_price()) * Float.parseFloat(product.getProduct_quantity());
        total_amount.setText("৳" + total);

        price.setText("Price : " + "৳" + product.getProduct_price());
        qty.setText("Qty : " + product.getProduct_quantity());
        payment_method.setText("Cash on delivery");

        //Cancel
        if (product.getOrder_status().equalsIgnoreCase("shipped")
                || product.getOrder_status().equalsIgnoreCase("delivered")
                || product.getOrder_status().equalsIgnoreCase("cancel")
                || product.getOrder_status().equalsIgnoreCase("complete")){
            findViewById(R.id.cancel_layout).setVisibility(View.GONE);
        }
        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderRef.child(product.getOrder_id()).child("order_status").setValue("cancel").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(OrderDetailsActivity.this, BuyerOrderActivity.class));
                        finish();
                    }
                });
            }
        });

        //Complete
        if (product.getOrder_status().equalsIgnoreCase("delivered")){
            btComplete.setVisibility(View.VISIBLE);
        }
        btComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderRef.child(product.getOrder_id()).child("order_status").setValue("complete").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(OrderDetailsActivity.this, BuyerOrderActivity.class));
                        finish();
                    }
                });
            }
        });
    }


    //For Back Button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}