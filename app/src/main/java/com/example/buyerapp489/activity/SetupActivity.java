package com.example.buyerapp489.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.buyerapp489.R;
import com.example.buyerapp489.model.Buyer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class SetupActivity extends AppCompatActivity {

    //Initialize variables
    private EditText etName, etPhone, etEmail, etOwnerName, etAddress, etAddressCity, etAddressState, etPostalCode;
    private ImageView profileImage;
    private Button btSubmit;
    private ProgressDialog loadingBar;
    private ProgressBar progressBar;
    private Spinner spDivision;
    private String stEmail = "";

    private Buyer buyer;
    private int INTENT_ID;


    //Initialize Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private StorageReference userStoreImageRef;
    private String currentUserId;
    private String currentUserEmail;

    final static int GALLERY_PICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        //Initialize variable
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etOwnerName = findViewById(R.id.etOwnerName);
        etAddress = findViewById(R.id.etAddress);
        etAddressCity = findViewById(R.id.etAddressCity);
        etAddressState = findViewById(R.id.etAddressState);
        etPostalCode = findViewById(R.id.etPostalCode);
        btSubmit = findViewById(R.id.btSubmit);
        profileImage = findViewById(R.id.profile_image);
        loadingBar = new ProgressDialog(this);
        loadingBar.setCancelable(false);
        progressBar = findViewById(R.id.progressbar);

        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        currentUserEmail = mAuth.getCurrentUser().getEmail();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("BuyerInfo");
        userStoreImageRef = FirebaseStorage.getInstance().getReference().child("store images");

        //Set Email Text
        etEmail.setText(currentUserEmail);

        //Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            stEmail = bundle.getString("email");
            etEmail.setText(stEmail);
            try {
                INTENT_ID = bundle.getInt("INTENT_ID");
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        if(INTENT_ID==1){
            try {
                buyer = (Buyer) getIntent().getSerializableExtra("BUYER");
                setValueUI();
            }catch (Exception e){
                e.printStackTrace();
            }
        }




        //Set up Spinner
        spDivision = findViewById(R.id.spDivision);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.division_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDivision.setAdapter(adapter);

        //Submit Data
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });

        //Choose Image
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_PICK);
            }
        });

    }

    private void setValueUI() {
        etName.setText(buyer.getStoreName()+"");
        etEmail.setText(buyer.getEmail()+"");
        etPhone.setText(buyer.getPhone()+"");
        etOwnerName.setText(buyer.getOwnerName()+"");
        etAddress.setText(buyer.getAddress()+"");
        etAddressCity.setText(buyer.getCity()+"");
        etAddressState.setText(buyer.getState()+"");
        etPostalCode.setText(buyer.getPostalCode()+"");
        /*spDivision.setPo;*/
    }

    private void saveUserInfo() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String ownerName = etOwnerName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String city = etAddressCity.getText().toString().trim();
        String state = etAddressState.getText().toString().trim();
        String postalCode = etPostalCode.getText().toString().trim();
        String division = spDivision.getSelectedItem().toString().trim();



        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(ownerName) || TextUtils.isEmpty(address) || TextUtils.isEmpty(city)
                || TextUtils.isEmpty(postalCode) || TextUtils.isEmpty(state)){
            Toast.makeText(this, "Fill Up All", Toast.LENGTH_SHORT).show();
        }else if(division.equals("Select Division")){
            Toast.makeText(this, "Select Division", Toast.LENGTH_SHORT).show();
        }else{
            //ProgressBar
            progressBar.setVisibility(View.VISIBLE);
            btSubmit.setVisibility(View.GONE);

            //Save User Data
            HashMap userMap = new HashMap();
            userMap.put("storeName", name);
            userMap.put("email", email);
            userMap.put("phone", phone);
            userMap.put("division", division);

            //Apply condition
            if (INTENT_ID==-1) {
                userMap.put("isVerified", -1);
                userMap.put("rating", 0);
                userMap.put("uniqueID", "0");
            }

            userMap.put("ownerName", ownerName);
            userMap.put("address", address);
            userMap.put("city", city);
            userMap.put("state", state);
            userMap.put("postalCode", postalCode);
            userMap.put("id", mAuth.getCurrentUser().getUid());
            userRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        //Save Done
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
//                        Toast.makeText(SetupActivity.this, "Your account created successfully", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        btSubmit.setVisibility(View.VISIBLE);
                    }else {
                        Toast.makeText(SetupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        btSubmit.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data!=null ){
            Uri imageUri = data.getData();

            loadingBar.setTitle("Profile Image");
            loadingBar.setMessage("Please wait, while we updating your store image...");
            loadingBar.show();

            final StorageReference filepath = userStoreImageRef.child(currentUserId+".jpg");

            filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){

                        //get Image Uri
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //get Image Uri
                                userRef.child("profileImage").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            profileImage.setImageURI(imageUri);
                                            loadingBar.dismiss();
                                        }
                                        else {
                                            loadingBar.dismiss();
                                        }
                                    }
                                });
                            }
                        });

                    }else{
                        loadingBar.dismiss();
                        Toast.makeText(SetupActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}