package com.example.buyerapp489.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.buyerapp489.R;
import com.example.buyerapp489.adapter.ProductAdapter;
import com.example.buyerapp489.global.GlobalClass;
import com.example.buyerapp489.model.Location;
import com.example.buyerapp489.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductDescriptionActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    public static int cart = -1;


    private ImageView productImage, productImage0, productImage1, productImage2, quantityPlus, quantityMinus, productCart, star1, star2, star3, star4, star5;
    private TextView priceProductTV, totalPriceProductTV, shopNameTV, quantityTV, descriptionTV, productNameTV, productMinTV, stock_text, mrp_text, policy_text, rating_txt;
    private Button addToCart;
    Product product;
    int quantity;

    //Product RecyclerView
    private RecyclerView productRecyclerView;
    private List<Product> productList;
    private ProductAdapter recyclerViewAdapter;

    //database
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference productReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting product
        product = (Product) getIntent().getSerializableExtra("product");

        //Initialize Variables
        productImage = findViewById(R.id.product_image_name);
        productImage0 = findViewById(R.id.product_image0);
        productImage1 = findViewById(R.id.product_image1);
        productImage2 = findViewById(R.id.product_image2);
        productCart = findViewById(R.id.product_cart);
        priceProductTV = findViewById(R.id.price_text);
        totalPriceProductTV = findViewById(R.id.total_price_text);
        shopNameTV = findViewById(R.id.shop_name_text);
        productNameTV = findViewById(R.id.txtName);
        descriptionTV = findViewById(R.id.description_text);
        productMinTV = findViewById(R.id.min_text);
        rating_txt = findViewById(R.id.rating_txt);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        stock_text = findViewById(R.id.stock_text);
        stock_text.setText(product.getStock());

        policy_text = findViewById(R.id.policy_text);
        policy_text.setText(product.getPolicy());

        mrp_text = findViewById(R.id.mrp_text);
        if (product.getMrp() != null) {
            mrp_text.setText(product.getMrp());
        }

        //Rating
        String rating = product.getReview();
        rating_txt.setText("Rating : (" + rating + ")");
        if (Integer.parseInt(rating)==5){
            star1.setImageResource(R.drawable.ic_baseline_star_green);
            star2.setImageResource(R.drawable.ic_baseline_star_green);
            star3.setImageResource(R.drawable.ic_baseline_star_green);
            star4.setImageResource(R.drawable.ic_baseline_star_green);
            star5.setImageResource(R.drawable.ic_baseline_star_green);
        }else if (Integer.parseInt(rating)>=4){
            star1.setImageResource(R.drawable.ic_baseline_star_green);
            star2.setImageResource(R.drawable.ic_baseline_star_green);
            star3.setImageResource(R.drawable.ic_baseline_star_green);
            star4.setImageResource(R.drawable.ic_baseline_star_green);
            star5.setImageResource(R.drawable.baseline_star_24);
        }else if (Integer.parseInt(rating)>=3){
            star1.setImageResource(R.drawable.ic_baseline_star_green);
            star2.setImageResource(R.drawable.ic_baseline_star_green);
            star3.setImageResource(R.drawable.ic_baseline_star_green);
            star4.setImageResource(R.drawable.baseline_star_24);
            star5.setImageResource(R.drawable.baseline_star_24);
        }else if (Integer.parseInt(rating)>=2){
            star1.setImageResource(R.drawable.ic_baseline_star_green);
            star2.setImageResource(R.drawable.ic_baseline_star_green);
            star3.setImageResource(R.drawable.baseline_star_24);
            star4.setImageResource(R.drawable.baseline_star_24);
            star5.setImageResource(R.drawable.baseline_star_24);
        }else if (Integer.parseInt(rating)>=1){
            star1.setImageResource(R.drawable.ic_baseline_star_green);
            star2.setImageResource(R.drawable.baseline_star_24);
            star3.setImageResource(R.drawable.baseline_star_24);
            star4.setImageResource(R.drawable.baseline_star_24);
            star5.setImageResource(R.drawable.baseline_star_24);
        }else {
            star1.setImageResource(R.drawable.baseline_star_24);
            star2.setImageResource(R.drawable.baseline_star_24);
            star3.setImageResource(R.drawable.baseline_star_24);
            star4.setImageResource(R.drawable.baseline_star_24);
            star5.setImageResource(R.drawable.baseline_star_24);
        }

        quantityPlus = findViewById(R.id.plus_image);
        quantityMinus = findViewById(R.id.minus_image);
        quantityTV = findViewById(R.id.quantity_text);
        addToCart = findViewById(R.id.add_to_cart_button);

        priceProductTV = findViewById(R.id.price_text);
        totalPriceProductTV = findViewById(R.id.total_price_text);
        quantity = Integer.parseInt(product.getMinimum_order());
        quantityTV.setText(quantity+"");


        //Initializing firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        productReference = firebaseDatabase.getReference("Products");

        //Related Product
        getRelatedProductFromFirebase();

        //Set On Click Listener
        quantityPlus.setOnClickListener(this);
        quantityMinus.setOnClickListener(this);
        addToCart.setOnClickListener(this);
        productCart.setOnClickListener(this);
        productImage0.setOnClickListener(this);
        productImage1.setOnClickListener(this);
        productImage2.setOnClickListener(this);
        findViewById(R.id.shop_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDescriptionActivity.this, ShopDetailsActivity.class).putExtra("SellerID", product.getSellerId()));
            }
        });

        //Set Product Image
        if (product.getProductImageArray().img0 != null) {
            setProductImage(product.getProductImageArray().img0, productImage);
            setProductImage(product.getProductImageArray().img0, productImage0);
        }else if (product.getProductImageArray().img1 != null){
            productImage1.setVisibility(View.VISIBLE);
            setProductImage(product.getProductImageArray().img1, productImage1);
        }else if (product.getProductImageArray().img2 != null){
            productImage1.setVisibility(View.VISIBLE);
            setProductImage(product.getProductImageArray().img2, productImage2);
        }

        //Set Product Name
        setProductName(product.getProduct_name());
        setTotalPrice(Integer.parseInt(product.getProduct_price()) * Integer.parseInt(product.getMinimum_order())+"");
        setPrice(product.getProduct_price());
        setMinimum(product.getMinimum_order());
        setDescription(product.getProduct_description());
        setShopName(product.getProduct_shop());
    }

    private void getRelatedProductFromFirebase() {
        //Product RecyclerView
        productRecyclerView = findViewById(R.id.recyclerview_related);
        productRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
//        productRecyclerView.setLayoutManager(
//                new GridLayoutManager(productRecyclerView.getContext(), 1, GridLayoutManager.HORIZONTAL, false));

        productList = new ArrayList<>();

        //getting products from firebase
        productReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot npsnapshot : snapshot.getChildren()){
                        Product l = npsnapshot.getValue(Product.class);

                        if (npsnapshot.hasChild("location")) {
                            DataSnapshot locationS = npsnapshot.child("location");

                            Location location = locationS.getValue(Location.class);
                            l.setLocation(location);

                            Log.d("LOCATION1", "onDataChange: " + l.getLocation().isMirpur());

                            //Category Check
                            if (l.getProduct_category().equals(product.getProduct_category())) {
                                if (!product.getProduct_key().equals(l.getProduct_key())) {
                                    productList.add(l);
                                }
                                //Progress Bar
                                /*if (progressBar.getVisibility() == View.VISIBLE) {
                                    progressBar.setVisibility(View.GONE);
                                }*/
                            }
                        }


                    }
                    recyclerViewAdapter = new ProductAdapter(productList, getApplicationContext());
                    productRecyclerView.setAdapter(recyclerViewAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.plus_image:
                quantity++;
                quantityTV.setText(""+quantity);
                totalPriceProductTV.setText(""+(Integer.parseInt(product.getProduct_price())*quantity));
                break;
            case R.id.minus_image:
                if (quantity > Integer.parseInt(product.getMinimum_order())) {
                    quantity--;
                    quantityTV.setText("" + quantity);
                    totalPriceProductTV.setText("" + (Integer.parseInt(product.getProduct_price()) * quantity));
                }
                break;
            case R.id.add_to_cart_button:
                //Product(String product_name, String product_catagory, String product_price, String product_image, String product_quantity, String product_shop, String discount, boolean isFeatured)
                product.setProduct_quantity(""+quantity);
                GlobalClass.cartProducts.add(product);
                GlobalClass.totalPrice = GlobalClass.totalPrice + (Integer.parseInt(product.getProduct_price())*quantity);
                Toast.makeText(this, "Total Price: "+ GlobalClass.totalPrice, Toast.LENGTH_SHORT).show();
                break;
            case R.id.product_cart:
                cart = 1;
                Intent intent = new Intent(ProductDescriptionActivity.this, HomeActivity.class);
                intent.putExtra("Siam", "Siam");
                startActivity(intent);

//                startActivity(new Intent(ProductDescriptionActivity.this, MainActivity.class).putExtra("f", "1"));
//                finish();
                break;

            //Image
            case R.id.product_image0:
                setProductImage(product.getProductImageArray().img0, productImage);
                break;
            case R.id.product_image1:
                setProductImage(product.getProductImageArray().img1, productImage);
                break;
            case R.id.product_image2:
                setProductImage(product.getProductImageArray().img2, productImage);
                break;
        }
    }

    private void setDescription(String description) {
        descriptionTV.setText(description);
    }
    private void setShopName(String shopName) {
        shopNameTV.setText(shopName);
    }
    private void setTotalPrice(String totalPrice) {
        totalPriceProductTV.setText(totalPrice);
    }
    private void setMinimum(String minimum_order) {
        productMinTV.setText(minimum_order+"");
    }
    private void setPrice(String s) {
        priceProductTV.setText(s);
    }

    private void setProductName(String productName){
//        getSupportActionBar().setTitle(productName);
        productNameTV.setText(productName);
    }

    private void setProductImage(String url, ImageView productImage){
        Glide.with(getApplicationContext())
                .load(url)
                .placeholder(R.drawable.img_loading)
                .centerCrop()
                .into(productImage);
//        setToolbarColor();
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