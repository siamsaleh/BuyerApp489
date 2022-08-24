package com.example.buyerapp489.global;


import com.example.buyerapp489.model.Favourite;
import com.example.buyerapp489.model.Product;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class GlobalClass {

    public static FirebaseAuth mAuthGlobal = FirebaseAuth.getInstance();

    public static float totalPrice = 0;
    public static ArrayList<Product> cartProducts = new ArrayList<>();
    public static ArrayList<Favourite> favouriteProducts = new ArrayList<>();

}
