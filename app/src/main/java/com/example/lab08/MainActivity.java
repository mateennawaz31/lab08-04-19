package com.example.lab08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore objectFirebaseFirestore;

    private static String HOTEL_DETAILS = "HotelDetails";

    private Dialog objectDialog;
    private EditText documentIDET, noOfRoomsET, nameOfRestaurantET, locationOfHotelET;

    private TextView collectionValuesTV;
    private DocumentReference objectDocumentReference;

    private SignInButton objectsigninbtn;

    FirebaseAuth mAuth;
    private int RC_SIGN_IN=1;


    private String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        mAuth=FirebaseAuth.getInstance();



        try {

            objectFirebaseFirestore = FirebaseFirestore.getInstance();
            objectDialog = new Dialog(this);

            objectDialog.setContentView(R.layout.please_wait_dialog_file);
            objectDialog.setCancelable(false);

            documentIDET = findViewById(R.id.documentIDET);
            noOfRoomsET = findViewById(R.id.noOfRoomsET);

            nameOfRestaurantET = findViewById(R.id.nameOfRestaurantET);
            locationOfHotelET = findViewById(R.id.locationOfHotelET);

            collectionValuesTV = findViewById(R.id.collectionValuesTV);

        } catch (Exception e) {
            Toast.makeText(this, "onCreate:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        mAuth=FirebaseAuth.getInstance();




    }



    public void addValuesToFireStore(View v)
    {
        try
        {
            if(!documentIDET.getText().toString().isEmpty() && !noOfRoomsET.getText().toString().isEmpty()
                    && !nameOfRestaurantET.getText().toString().isEmpty()
                    && !locationOfHotelET.getText().toString().isEmpty()) {
                objectDialog.show();
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("noofrooms", noOfRoomsET.getText().toString());
                objectMap.put("nameofresturant", nameOfRestaurantET.getText().toString());

                objectMap.put("locationofhotel", locationOfHotelET.getText().toString());
                objectFirebaseFirestore.collection(HOTEL_DETAILS)
                        .document(documentIDET.getText().toString())
                        .set(objectMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                objectDialog.dismiss();
                                documentIDET.setText("");

                                noOfRoomsET.setText("");
                                nameOfRestaurantET.setText("");

                                locationOfHotelET.setText("");
                                documentIDET.requestFocus();
                                Toast.makeText(MainActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        objectDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Fails to add data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e)
        {
            objectDialog.dismiss();
            Toast.makeText(this, "addValuesToFireStore:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateValue(View view)
    {
        try
        {
            if(!documentIDET.getText().toString().isEmpty() && !noOfRoomsET.getText().toString().isEmpty()) {
                objectDialog.show();
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("noofrooms", noOfRoomsET.getText().toString());
                objectMap.put("noofRestaurants", "5");
                objectDocumentReference = objectFirebaseFirestore.collection(HOTEL_DETAILS)
                        .document(documentIDET.getText().toString());

                objectDocumentReference.update(objectMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                objectDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Data updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                objectDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Fails to update data:"
                                        + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else
            {
                Toast.makeText(this, "Fill All Fields", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e)
        {
            objectDialog.dismiss();
            Toast.makeText(this, "updateValue:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void signin(View v)
    {
        try {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "SignIn"
                    +e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void signup(View v)
    {
        try {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "SignUp" +
                    e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }









}

