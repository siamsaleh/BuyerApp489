package com.example.buyerapp489.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyerapp489.R;
import com.example.buyerapp489.adapter.CheckInfoProductAdapter;
import com.example.buyerapp489.global.GlobalClass;
import com.example.buyerapp489.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckoutInfoActivity extends AppCompatActivity {

    //Initialize Variables
    private EditText etAddress, etPhone;
    private TextView txtSubtotal, txtDelivery, txtTotal, txtFinalTotal;
    private Button btPlaceOrder;

    //Cart RecyclerView
    private RecyclerView recyclerView;
    private List<Product> cartList;
    private CheckInfoProductAdapter recyclerViewAdapter;

    //Firebase
    FirebaseAuth mAuth;
    DatabaseReference buyerRef, pendingOrders, orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize Variables
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        txtSubtotal = findViewById(R.id.subtotal_text);
        txtDelivery = findViewById(R.id.delivery_text);
        txtTotal = findViewById(R.id.total_text);
        txtFinalTotal = findViewById(R.id.finalTotal_text);
        btPlaceOrder = findViewById(R.id.btPlaceOrder);
        recyclerView = findViewById(R.id.cart_recyclerView);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        buyerRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("BuyerInfo");
        pendingOrders = FirebaseDatabase.getInstance().getReference().child("Users");
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        buyerRef.child("phone").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                etPhone.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewAdapter = new CheckInfoProductAdapter(GlobalClass.cartProducts, getApplicationContext());
        recyclerView.setAdapter(recyclerViewAdapter);

//        if (GlobalClass.cartProducts.size() == 0){
//            emptyImage.setVisibility(View.VISIBLE);
//        }else {
//            emptyImage.setVisibility(View.GONE);
//        }


        txtSubtotal.setText("Subtotal : "+GlobalClass.totalPrice);
        txtDelivery.setText("Deliver: 50.00");
        txtTotal.setText("Total "+ (GlobalClass.totalPrice+50.00));
        txtFinalTotal.setText("Total "+ (GlobalClass.totalPrice+50.00));

        btPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

    }

    private void placeOrder() {
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String buyerId = mAuth.getCurrentUser().getUid();


        if (TextUtils.isEmpty(address)){
            Toast.makeText(this, "Fill Address", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Fill Phone", Toast.LENGTH_SHORT).show();
        } else{
            for (int i = 0; GlobalClass.cartProducts.size() > i; i++){
                GlobalClass.cartProducts.get(i).setOrder_date(currentDate);
                GlobalClass.cartProducts.get(i).setDelivery_address(address);
                GlobalClass.cartProducts.get(i).setBuyer_phone(phone);
                GlobalClass.cartProducts.get(i).setBuyerId(buyerId);

                //First and last character (Order ID)
                char[] charArray = GlobalClass.cartProducts.get(i).getProduct_name().trim().toCharArray();
                String orderId = String.valueOf(System.currentTimeMillis()) + charArray[0] + "" + charArray[charArray.length-1];
                GlobalClass.cartProducts.get(i).setOrder_id(orderId);
            }

            if (GlobalClass.cartProducts.size() > 0){
                for (int i = 0; i < GlobalClass.cartProducts.size(); i++) {
                    Product product = GlobalClass.cartProducts.get(i);
                    product.setOrder_status("pending");

                    orderRef.child(product.getOrder_id()).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            GlobalClass.cartProducts = new ArrayList<>();
                            GlobalClass.totalPrice = 0;
                            startActivity(new Intent(CheckoutInfoActivity.this, HomeActivity.class));
                            finish();
                        }
                    });


                    ////////////////OLD////////////////////////

//                    //Save In Seller
//                    pendingOrders.child(product.getSellerId()).child("SellerPendingOrder").child(mAuth.getCurrentUser().getUid()).child(product.getProduct_key()).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            //Save In Buyer
//                            pendingOrders.child(mAuth.getCurrentUser().getUid()).child("BuyerPendingOrder").child(product.getSellerId()).child(product.getProduct_key()).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    GlobalClass.cartProducts = new ArrayList<>();
//                                    GlobalClass.totalPrice = 0;
//                                    startActivity(new Intent(CheckoutInfoActivity.this, HomeActivity.class));
//                                    finish();
//                                }
//                            });
//                        }
//                    });


                }
            }

        }

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