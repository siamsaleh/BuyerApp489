package com.example.buyerapp489.fragment;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.buyerapp489.R;
import com.example.buyerapp489.activity.FavouriteProductActivity;
import com.example.buyerapp489.adapter.CategoriesAdapter;
import com.example.buyerapp489.adapter.ProductAdapter;
import com.example.buyerapp489.global.GlobalClass;
import com.example.buyerapp489.model.Categories;
import com.example.buyerapp489.model.Favourite;
import com.example.buyerapp489.model.Location;
import com.example.buyerapp489.model.Product;
import com.example.buyerapp489.model.ProductImageArray;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    Context context;

    //Categories RecyclerView /
    private RecyclerView categoriesRecyclerView;
    private List<Categories> categoriesList;
    private CategoriesAdapter categoriesAdapter;

    //Product RecyclerView /
    private RecyclerView productRecyclerView;
    private List<Product> productList;
    private ProductAdapter productAdapter;

    //Product Favourite
    private List<Favourite> favouriteList;

    //database
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference catReference, sliderReference, userRef, productRef, favouriteRef;

    //Initialize Variables
    private ProgressBar progressBar;
    private TextView categoryText, productText;
    private Spinner spDivision;

    //nav
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton drawerButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = inflater.getContext();

        //Initialize Variables
        progressBar = view.findViewById(R.id.progressBar_cat);
        categoryText = view.findViewById(R.id.cat_text);
        productText = view.findViewById(R.id.pro_text);
        spDivision = view.findViewById(R.id.spDist);


        //initializing firebase
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        catReference = firebaseDatabase.getReference("Category");
        sliderReference = firebaseDatabase.getReference("ImageSlider");

        userRef = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("BuyerInfo");
        productRef = firebaseDatabase.getReference("Products");
        favouriteRef = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("BuyerFavouriteProduct");

        favouriteList = new ArrayList<>();

        favouriteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Favourite favorite = dataSnapshot.getValue(Favourite.class);
                        favouriteList.add(favorite);
                        Log.d("FAVOURITE", "onDataChange: " + favorite.getKey());
                    }
                }

                GlobalClass.favouriteProducts = (ArrayList<Favourite>) favouriteList;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        view.findViewById(R.id.btFavourite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, FavouriteProductActivity.class));
            }
        });

        //Categories RecyclerView
        categoriesRecyclerView = view.findViewById(R.id.horizontal_recyclerview);
        categoriesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        categoriesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        categoriesList = new ArrayList<>();
        catReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Categories categories = dataSnapshot.getValue(Categories.class);
                        categoriesList.add(categories);

                        //Progress Bar
                        if (progressBar.getVisibility() == View.VISIBLE){
                            progressBar.setVisibility(View.GONE);
                            categoryText.setVisibility(View.VISIBLE);
                            productText.setVisibility(View.VISIBLE);
                        }
                    }

                    categoriesAdapter = new CategoriesAdapter(categoriesList, context);
                    categoriesRecyclerView.setAdapter(categoriesAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Product RecyclerView
        productRecyclerView = view.findViewById(R.id.product_recyclerview);
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
                            DataSnapshot image = npsnapshot.child("productImageArray");

                            Location location = locationS.getValue(Location.class);
                            ProductImageArray productImageArray = image.getValue(ProductImageArray.class);

                            product.setLocation(location);
                            product.setProductImageArray(productImageArray);

                            Log.d("LOCATION1", "onDataChange: " + product.getLocation().isMirpur());

                            productList.add(product);
                            //Progress Bar
                            if (progressBar.getVisibility() == View.VISIBLE) {
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    }
                    productAdapter = new ProductAdapter(productList, context);
                    productRecyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //nav
        drawerButton = view.findViewById(R.id.menu_button);
        navigationView = view.findViewById(R.id.nav_View);
        drawerLayout = view.findViewById(R.id.drawer);
        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.contact_menu:
                        call("01686768903");
                        return true;
//                    case R.id.custom_menu:
//                        startActivity(new Intent(getActivity(), CustomOrderActivity.class));
//                        return true;

                }
                return false;
            }
        });

        return view;
    }

    private void call(String num) {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + num));
                getContext().startActivity(callIntent);
            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + num));
                getContext().startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
