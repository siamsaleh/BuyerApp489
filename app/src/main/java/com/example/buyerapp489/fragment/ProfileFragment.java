package com.example.buyerapp489.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.buyerapp489.R;
import com.example.buyerapp489.activity.BuyerOrderActivity;
import com.example.buyerapp489.activity.LoginActivity;
import com.example.buyerapp489.activity.NotificationActivity;
import com.example.buyerapp489.model.Buyer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    //Firebase
    FirebaseAuth mAuth;
    DatabaseReference buyerRef;

    //Initialize Variable
    Button logoutBT;
    TextView txtStoreName;

    Buyer buyer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        buyerRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("BuyerInfo");

        logoutBT = view.findViewById(R.id.logout);

        logoutBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        txtStoreName = view.findViewById(R.id.store_name);

        buyerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                buyer = snapshot.getValue(Buyer.class);
                txtStoreName.setText(buyer.getStoreName().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        view.findViewById(R.id.order_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BuyerOrderActivity.class));
            }
        });

        view.findViewById(R.id.notification_pro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NotificationActivity.class));
            }
        });


        return view;
    }
}
