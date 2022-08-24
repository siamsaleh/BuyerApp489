package com.example.buyerapp489.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.buyerapp489.R;
import com.example.buyerapp489.adapter.ProductAdapter;
import com.example.buyerapp489.model.Location;
import com.example.buyerapp489.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavouriteProductActivity extends AppCompatActivity {

    //Product RecyclerView /
    private androidx.recyclerview.widget.RecyclerView productRecyclerView;
    private List<Product> productList;
    private ProductAdapter productAdapter;

    private List<String> favouriteProductList;

    //Initialize Variables
    private ProgressBar progressBar;

    //database
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference productRef;
    DatabaseReference favRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize Variables
        progressBar = findViewById(R.id.progressBar_cat);

        //initializing firebase
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        productRef = firebaseDatabase.getReference("Products");
        favRef = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid());

        getFavouriteProductList();

        //Product RecyclerView
        productRecyclerView = findViewById(R.id.product_recyclerview);
        productRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        productList = new ArrayList<>();
        //getting products from firebase
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot npsnapshot : snapshot.getChildren()){
                        Product product = npsnapshot.getValue(Product.class);

                        if (npsnapshot.hasChild("location")) {
                            DataSnapshot locationS = npsnapshot.child("location");

                            Location location = locationS.getValue(Location.class);
                            product.setLocation(location);

                            Log.d("LOCATION1", "onDataChange: " + product.getLocation().isMirpur());

                            //checking with fav list
                            for (String favPro : favouriteProductList){
                                if (product.getProduct_key().equals(favPro)) {
                                    productList.add(product);
                                }
                            }

                            //Progress Bar
                            if (progressBar.getVisibility() == View.VISIBLE) {
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                    productAdapter = new ProductAdapter(productList, getApplicationContext());
                    productRecyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFavouriteProductList() {
        favouriteProductList = new ArrayList<>();
        DatabaseReference userRef1 = FirebaseDatabase.getInstance().getReference().child("Users");
        userRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot newSnapshot = snapshot.child(mAuth.getUid()).child("BuyerFavouriteProduct");
                if (newSnapshot.exists()) {
                    for (DataSnapshot key : newSnapshot.getChildren()){
                        favouriteProductList.add((String) key.child("key").getValue());
                        Log.d("PRODUCT1", "onDataChange: "+key.child("key").getValue());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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