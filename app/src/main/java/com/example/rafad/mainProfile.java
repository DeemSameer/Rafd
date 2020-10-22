package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class mainProfile extends AppCompatActivity {
    Button back , homebutton, editbutton2,post ;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId ;
    ImageView profileImageView;
    TextView fullName , email , phone;
     Adapter2 adapter;

    //////// for view list of items
    List<postinfo> arrayItem=new ArrayList<>();
    public static final String TAG = "TAG";
    ListView listView;
    //////// above is for view list of items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        back=findViewById(R.id.backHome);
        homebutton = findViewById(R.id.bHome);
        editbutton2 = findViewById(R.id.edit);

        phone = findViewById(R.id.phonenumber);
        fullName = findViewById(R.id.fullname);
        email = findViewById(R.id.email1);
        storageReference = FirebaseStorage.getInstance().getReference();
        profileImageView = findViewById(R.id.profileImg);

        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
post = findViewById(R.id.postItem);




        //////// for view list of items
        listView=(ListView)findViewById(R.id.postedlist);
        //////// above is for view list of items



        //profile pic
        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });


        DocumentReference documentReference = fStore.collection("donators").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                phone.setText(value.getString("phoneNumber"));
                fullName.setText(value.getString("userName"));
                email.setText(value.getString("email"));
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainProfile.this, homepageDonator.class));
                finish();

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(homepageDonator.this, postitem.class);
                startActivity(new Intent(mainProfile.this, post3.class));
                finish();
            }
        });


        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainProfile.this, homepageDonator.class));
                finish();

            }
        });




        editbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent i = new Intent(view.getContext(),donProfile.class);
               //pass the data
                i.putExtra("fullName",fullName.getText().toString());
                i.putExtra("email",email.getText().toString());
                i.putExtra("phone", phone.getText().toString());
               startActivity(i);

            }
        });
/*
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference p = adapter.storageRef;
                adapter.profileRef.delete();
            fStore.collection("item").document(adapter.itemID).delete();*/
               /* Intent i = new Intent(view.getContext(),donProfile.class);
                startActivity(i);
            }
        });*/

        //////////////////// for list of items second try////////////////////////


       // FirebaseFirestore db = FirebaseFirestore.getInstance();
        fStore.collection("item")
                    .whereEqualTo("User id", userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    arrayItem.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ));
                                    Log.d(TAG, "SIZE item list => " + arrayItem.size());
                                }
                                 adapter = new Adapter2(mainProfile.this, arrayItem);
                                listView = (ListView) findViewById(R.id.postedlist);
                                listView.setAdapter(adapter);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });



        //////////////////// for list of items second try////////////////////////

    }
}