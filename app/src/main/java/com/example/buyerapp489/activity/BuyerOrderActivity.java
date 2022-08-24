package com.example.buyerapp489.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyerapp489.R;
import com.example.buyerapp489.adapter.OrderListAdapter;
import com.example.buyerapp489.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BuyerOrderActivity extends AppCompatActivity {

    //Product RecyclerView
    private RecyclerView shopRecyclerView;
    private List<Product> productList;
    private OrderListAdapter recyclerViewAdapter;

    //database
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference productReference,catReference,shopReference, orderRef;

    //Initialize Variables
    private ProgressBar progressBar;
    private RelativeLayout noOrderLayout;

    private Spinner spinner;
    private String[] status = new String[]{"All", "Pending", "Reschedule", "Packed", "Shipped", "Hold", "Cancel", "Delivered", "Complete"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Initialize Variables
        progressBar = findViewById(R.id.progressBar_pro);
        noOrderLayout = findViewById(R.id.imgNoOrder);

        //create menu top
        initializeMenu();

        //Initializing firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        productReference = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("BuyerPendingOrder");
        catReference = firebaseDatabase.getReference("Category");
        shopReference = firebaseDatabase.getReference("Category");
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        getProductFromFirebase("all");


    }

    private void getProductFromFirebase(String pageName) {
        //Product RecyclerView
        shopRecyclerView = findViewById(R.id.shop_recyclerView);
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        productList = new ArrayList<>();


        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Product product = dataSnapshot.getValue(Product.class);

                        assert product != null;
                        if (product.getBuyerId().equals(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())) {
                            try {
                                if (product.getOrder_status().equals(pageName.toLowerCase()) || pageName.equalsIgnoreCase("all")) {
                                    productList.add(product);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        //Progress Bar
                        progressBar.setVisibility(View.GONE);
                    }
                }
                recyclerViewAdapter = new OrderListAdapter(productList, getApplicationContext());
                shopRecyclerView.setAdapter(recyclerViewAdapter);
                if (productList.size() == 0) {
                    noOrderLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else {
                    noOrderLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeMenu() {
        spinner = findViewById(R.id.menu);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, status);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getProductFromFirebase(status[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}