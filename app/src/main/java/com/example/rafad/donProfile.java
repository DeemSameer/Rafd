package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class donProfile extends AppCompatActivity {

    private static final int GALLERY_INTENT_CODE=1023;
    public static final String TAG = "TAG";
    TextView fullname , email , phone , pass;
    Button changeProfileIMG, resetPassLocal, resendCode;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageRefrence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_profile);



        phone = findViewById(R.id.newPhone);
        fullname =findViewById(R.id.newName);
        email = findViewById(R.id.newEmail);
        profileImage= findViewById(R.id.profileImg);
        changeProfileIMG=findViewById(R.id.change);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageRefrence = FirebaseStorage.getInstance().getReference();


        //code monday

        //pring data from mainProfile activity
        Intent data = getIntent();
        String fullName=data.getStringExtra("fullName");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");

        //to check if passed or not

        Log.d(TAG, "onCreate: " + fullName + " " + email + " " + phone);
        //code monday




        StorageReference profileRef = storageRefrence.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg"); //هنا يقول حط اسمها الصدقي مافهمت مره
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });


        changeProfileIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to open the gallery , create new intent
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);

            }
        });

    }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //profileImage.setImageURI(imageUri);
                //upload to firebase
                uploadImageToFirebase(imageUri);

            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        //to upload image to firebase
        final StorageReference fileRef = storageRefrence.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                  @Override
                  public void onSuccess(Uri uri) {
                      Picasso.get().load(uri).into(profileImage);
                  }
              });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(donProfile.this, " فشل تغيير الصورة ", Toast.LENGTH_SHORT).show();
            }
        });




    }

    }


