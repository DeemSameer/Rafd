package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.rafad.ChatJava.MainChatAllPeople;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Paint;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class homePage extends AppCompatActivity {
    Button logout ,profile1, post , clothes , furniture,device,all,Chat,device2k,access,tool,device3m;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    //////// for view list of items
    List<postinfo> arrayItem;
    List<postinfo> arrayItemC;
    List<postinfo> arrayItemD;
    List<postinfo> arrayItemF;
    //List<postinfo> arrayItemDK;
    List<postinfo> arrayItemT;
    //List<postinfo> arrayItemDM;
    List<postinfo> arrayItemAc;
    public static final String TAG = "TAG";
    ListView listView;
    TextView empty,BigT,f,c,d,dk,dm,ac,to;
    //////// above is for view list of items


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        clothes = findViewById(R.id.clothes1);
        furniture = findViewById(R.id.furniture);
        device = findViewById(R.id.device1);
        //all = findViewById(R.id.all1);
        Chat= findViewById(R.id.button12);
        ///
        //device2k = findViewById(R.id.devke);
        tool= findViewById(R.id.tool1);
        access= findViewById(R.id.acc);
        //device3m= findViewById(R.id.devicem);
        // dk =findViewById(R.id.devke);
        // dm=findViewById(R.id.devm);
        ac=findViewById(R.id.acce);
        to=findViewById(R.id.tool);
        ///
        BigT=findViewById(R.id.ifempty2);

        BigT.setTypeface(BigT.getTypeface(), Typeface.BOLD);


        f=findViewById(R.id.fun);
        c=findViewById(R.id.clu);
        d=findViewById(R.id.dev);
        //logout = findViewById(R.id.logoutButton);
        profile1= findViewById(R.id.profileb);
        post= findViewById(R.id.postItem);
        Button b=findViewById(R.id.button10);
        empty = findViewById(R.id.homepagetext);
        //////// for view list of items
        listView=(ListView)findViewById(R.id.postedlistHomePage);
        //////// above is for view list of items

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        String LoggedIn_User_Email =FirebaseAuth.getInstance().getCurrentUser().getEmail();
        OneSignal.sendTag("User_ID",LoggedIn_User_Email);

        device.setLayoutParams (new TableRow.LayoutParams(75, 75));
        clothes.setLayoutParams (new TableRow.LayoutParams(75, 75));
        furniture.setLayoutParams (new TableRow.LayoutParams(90, 90));
//
//        device2k.setLayoutParams (new TableRow.LayoutParams(60, 60));
        tool.setLayoutParams (new TableRow.LayoutParams(75, 75));
        access.setLayoutParams (new TableRow.LayoutParams(75, 75));
        //    device3m.setLayoutParams (new TableRow.LayoutParams(60, 60));

        //

        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clothes.setLayoutParams (new TableRow.LayoutParams(90, 90));
                device.setLayoutParams (new TableRow.LayoutParams(75, 75));
                furniture.setLayoutParams (new TableRow.LayoutParams(75, 75));
                //device2k.setLayoutParams (new TableRow.LayoutParams(60, 60));
                tool.setLayoutParams (new TableRow.LayoutParams(75, 75));
                access.setLayoutParams (new TableRow.LayoutParams(75, 75));
                // device3m.setLayoutParams (new TableRow.LayoutParams(60, 60));
                c.setText("ملابس");
                c.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                c.setTypeface(BigT.getTypeface(), Typeface.BOLD);
                d.setText("");
                f.setText("");
                //dk.setText("");
                //dm.setText("");
                ac.setText("");
                to.setText("");
                arrayItemC =new ArrayList<>();
                clothes.setPaintFlags(clothes.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Query p =  fStore.collection("item").whereEqualTo("Catogery", "ملابس")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemC.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("Demail")));
                                        Log.d(TAG, "SIZE item list => " + arrayItemC.size());
                                    }
                                    empty.setText(arrayItemC.size()+" منتج ");
                                    empty.setTypeface(BigT.getTypeface(), Typeface.BOLD);
                                    ListViewAdaptorBen adapter = new ListViewAdaptorBen(homePage.this, arrayItemC);

                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clothes.setLayoutParams (new TableRow.LayoutParams(75, 75));
                device.setLayoutParams (new TableRow.LayoutParams(75, 75));
                furniture.setLayoutParams (new TableRow.LayoutParams(90, 90));
                //  device2k.setLayoutParams (new TableRow.LayoutParams(60, 60));
                tool.setLayoutParams (new TableRow.LayoutParams(75, 75));
                access.setLayoutParams (new TableRow.LayoutParams(75, 75));
                //   device3m.setLayoutParams (new TableRow.LayoutParams(60, 60));
                f.setText("اثاث");
                f.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                f.setTypeface(BigT.getTypeface(), Typeface.BOLD);
                d.setText("");
                c.setText("");
                //  dk.setText("");
                //  dm.setText("");
                ac.setText("");
                to.setText("");
                arrayItemF =new ArrayList<>();
                Query p =  fStore.collection("item").whereEqualTo("Catogery", "أثاث")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemF.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("Demail")));
                                        Log.d(TAG, "SIZE item list => " + arrayItemF.size());
                                    }
                                    empty.setText(arrayItemF.size()+" منتج ");
                                    empty.setTypeface(BigT.getTypeface(), Typeface.BOLD);
                                    ListViewAdaptorBen adapter = new ListViewAdaptorBen(homePage.this, arrayItemF);

                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clothes.setLayoutParams (new TableRow.LayoutParams(75, 75));
                device.setLayoutParams (new TableRow.LayoutParams(90, 90));
                furniture.setLayoutParams (new TableRow.LayoutParams(75, 75));
                //  device2k.setLayoutParams (new TableRow.LayoutParams(60, 60));
                tool.setLayoutParams (new TableRow.LayoutParams(75, 75));
                access.setLayoutParams (new TableRow.LayoutParams(75, 75));
                //  device3m.setLayoutParams (new TableRow.LayoutParams(60, 60));
                d.setText("أجهزه");
                d.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                d.setTypeface(BigT.getTypeface(), Typeface.BOLD);
                f.setText("");
                c.setText("");
                //  dk.setText("");
                //   dm.setText("");
                ac.setText("");
                to.setText("");
                arrayItemD =new ArrayList<>();
                Query p =  fStore.collection("item").whereEqualTo("Catogery", "أجهزة")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemD.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("Demail")));
                                        Log.d(TAG, "SIZE item list => " + arrayItemD.size());
                                    }
                                    empty.setText(arrayItemD.size()+" منتج ");
                                    empty.setTypeface(BigT.getTypeface(), Typeface.BOLD);
                                    ListViewAdaptorBen adapter = new ListViewAdaptorBen(homePage.this, arrayItemD);

                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        //1

        //2
        tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clothes.setLayoutParams (new TableRow.LayoutParams(75, 75));
                device.setLayoutParams (new TableRow.LayoutParams(75, 75));
                furniture.setLayoutParams (new TableRow.LayoutParams(75, 75));
                //  device2k.setLayoutParams (new TableRow.LayoutParams(60, 60));
                tool.setLayoutParams (new TableRow.LayoutParams(90, 90));
                access.setLayoutParams (new TableRow.LayoutParams(75, 75));
                //  device3m.setLayoutParams (new TableRow.LayoutParams(60, 60));
                to.setText("أدوات");
                to.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                to.setTypeface(BigT.getTypeface(), Typeface.BOLD);
                d.setText("");
                c.setText("");
                //    dk.setText("");
                //    dm.setText("");
                ac.setText("");
                f.setText("");
                arrayItemT =new ArrayList<>();
                Query p =  fStore.collection("item").whereEqualTo("Catogery", "ادوات")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemT.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("Demail")));
                                        Log.d(TAG, "SIZE item list => " + arrayItemT.size());
                                    }
                                    empty.setText(arrayItemT.size()+" منتج ");
                                    empty.setTypeface(BigT.getTypeface(), Typeface.BOLD);
                                    ListViewAdaptorBen adapter = new ListViewAdaptorBen(homePage.this, arrayItemT);

                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        //3
        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clothes.setLayoutParams (new TableRow.LayoutParams(75, 75));
                device.setLayoutParams (new TableRow.LayoutParams(75, 75));
                furniture.setLayoutParams (new TableRow.LayoutParams(75, 75));
                //  device2k.setLayoutParams (new TableRow.LayoutParams(60, 60));
                tool.setLayoutParams (new TableRow.LayoutParams(75, 75));
                access.setLayoutParams (new TableRow.LayoutParams(90, 90));
                //  device3m.setLayoutParams (new TableRow.LayoutParams(60, 60));
                ac.setText("اكسسوارات");
                ac.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                ac.setTypeface(BigT.getTypeface(), Typeface.BOLD);
                d.setText("");
                c.setText("");
                //  dk.setText("");
                //   dm.setText("");
                f.setText("");
                to.setText("");
                arrayItemAc =new ArrayList<>();
                Query p =  fStore.collection("item").whereEqualTo("Catogery", "اكسسوارات")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemAc.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("Demail")));
                                        Log.d(TAG, "SIZE item list => " + arrayItemAc.size());
                                    }
                                    empty.setText(arrayItemAc.size()+" منتج ");
                                    empty.setTypeface(BigT.getTypeface(), Typeface.BOLD);
                                    ListViewAdaptorBen adapter = new ListViewAdaptorBen(homePage.this, arrayItemAc);

                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });

        // all.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        arrayItem =new ArrayList<>();
        // FirebaseFirestore db = FirebaseFirestore.getInstance();
        clothes.setLayoutParams (new TableRow.LayoutParams(75, 75));
        device.setLayoutParams (new TableRow.LayoutParams(75, 75));
        furniture.setLayoutParams (new TableRow.LayoutParams(90, 90));
        //  device2k.setLayoutParams (new TableRow.LayoutParams(60, 60));
        tool.setLayoutParams (new TableRow.LayoutParams(75, 75));
        access.setLayoutParams (new TableRow.LayoutParams(75, 75));
        //   device3m.setLayoutParams (new TableRow.LayoutParams(60, 60));
        f.setText("اثاث");
        f.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        f.setTypeface(BigT.getTypeface(), Typeface.BOLD);
        d.setText("");
        c.setText("");
        //  dk.setText("");
        //  dm.setText("");
        ac.setText("");
        to.setText("");
        //
        //
        arrayItem =new ArrayList<>();
        Query p =  fStore.collection("item").whereEqualTo("Catogery", "أثاث")
                .whereEqualTo("isRequested", "no" );

        p.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                arrayItem.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("Demail")));
                                Log.d(TAG, "SIZE item list => " + arrayItem.size());
                            }
                            empty.setText(arrayItem.size()+" منتج ");
                            empty.setTypeface(BigT.getTypeface(), Typeface.BOLD);
                            ListViewAdaptorBen adapter = new ListViewAdaptorBen(homePage.this, arrayItem);

                            listView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //4

/*
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clothes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                furniture.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                device.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                all.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                arrayItemA=new ArrayList<>();
                Query p =  fStore.collection("item")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemA.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("Demail")));
                                        Log.d(TAG, "SIZE item list => " + arrayItemA.size());
                                    }
                                    if (arrayItemA.size()==0)
                                    {
                                        empty.setText("لا يوجد بيانات للعرض");
                                    }
                                    else{
                                        empty.setText("");
                                    }
                                    HistoryItemAdapter adapter = new HistoryItemAdapter(homepageDonator.this, arrayItemA);
                                    listView = (ListView) findViewById(R.id.postedlistDonaterHome);
                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });*/
        logout = findViewById(R.id.logoutButton);
        Button profile= findViewById(R.id.profileb);
        Button toReq = findViewById(R.id.benHomeToReq);
        //////// for view list of items
        //listView=(ListView)findViewById(R.id.postedlist);
        //////// above is for view list of items
///
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homePage.this, BenMainProfile.class));
                finish();

            }
        });

/*        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(homePage.this, login.class));
                finish();
            }
        });*/
        toReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homePage.this, benReqView.class));
                finish();
            }
        });

/*
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(homepageDonator.this, login.class));
                finish();
            }
        });*/




    }


}