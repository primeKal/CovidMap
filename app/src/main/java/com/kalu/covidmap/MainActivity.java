package com.kalu.covidmap;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity  extends FragmentActivity implements OnMapReadyCallback {
    Dialog sysptom1,whttodo;
    PatientCase patientCase;
    TextView sysquestions,reportbtn;
    Button yes,no,alldone;
    List<String> questions;
    int offset=0;
    GoogleMap map;
    FusedLocationProviderClient mylocation;
    private static final int REQUEST_COde=1000;
    GeoPoint geoPoint;
    private ClusterManager<PatientsOnMap> mClusterManager;
    FloatingActionButton initiatepop,fab2,fab3,fab4;

    DatabaseReference myRef;
    List<PatientCase> myPatiens;
    private ImageView chechimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.setMenuVisibility(true);
        mapFragment.getMapAsync(this);

        initiatepop=findViewById(R.id.floatingActionButton);
        initiatepop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initipopupchecksys();
            }
        });
        fab2=findViewById(R.id.floatingActionButton2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intipopup2();
            }
        });

        fab3=findViewById(R.id.floatingActionButton3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DisccusionRoom.class));
            }
        });

        fab4=findViewById(R.id.floatingActionButton4);
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < myPatiens.size(); i++) {
                    double lat =myPatiens.get(i).getLatitude();
                    double lng =myPatiens.get(i).getLongitude();
                    PatientsOnMap offsetItem = new PatientsOnMap(lat, lng);
                    mClusterManager.addItem(offsetItem);
                }
                //startActivity(new Intent(getApplicationContext(),PreventionSlider.class));


            }
        });


        mylocation= LocationServices.getFusedLocationProviderClient(MainActivity.this);
        initfusedlocatio();
        initipopupchecksys();



    }

    private void intipopup2() {
        Dialog update = new Dialog(MainActivity.this);
        update.setContentView(R.layout.liveupdatecorona);
        update.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        update.getWindow().getAttributes().gravity = Gravity.TOP;
        update.show();

        final TextView confiremed=update.findViewById(R.id.confiremedcases);
        final TextView recovered=update.findViewById(R.id.recovered);
        final TextView inland=update.findViewById(R.id.inland);
        myRef= FirebaseDatabase.getInstance().getReference("UpdateCoronaData");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UpdateData data=dataSnapshot.getValue(UpdateData.class);
                confiremed.setText(data.getConfiremed());
                recovered.setText(data.getRecovered());
                inland.setText(data.getInland());
                Toast.makeText(getApplicationContext(),"Loved worked",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void initfusedlocatio() {
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_COde);
            return; }

        mylocation.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful()){
                    Location location=task.getResult();
                    geoPoint=new GeoPoint(location.getLatitude(),location.getLongitude());
                    addmarkeronmap(geoPoint);
                    setUpClusterer();

                }
            }
        });
    }


    private void addmarkeronmap(GeoPoint geoPoint) {
        LatLng bairdar=new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
        map.addMarker(new MarkerOptions()
                .position(bairdar)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconscorona)).title("Patien112"));
        map.moveCamera(CameraUpdateFactory.newLatLng(bairdar));
    }

    private void initipopupchecksys() {
        patientCase=new PatientCase();
        sysptom1 = new Dialog(MainActivity.this);
        sysptom1.setContentView(R.layout.checksysyp);
        sysptom1.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        sysptom1.getWindow().getAttributes().gravity = Gravity.TOP;
        sysptom1.show();

        questions=new ArrayList<>();
        questions.add("Do you have cough?");
        questions.add("Do you have a fever?");
        questions.add("Do you have loss of senses?");
        questions.add("Please check your temperature,is it in good value");

        sysquestions=sysptom1.findViewById(R.id.question1);
        yes=sysptom1.findViewById(R.id.yes);
        no=sysptom1.findViewById(R.id.nobtn);
        reportbtn=sysptom1.findViewById(R.id.reportbtn);
        alldone=sysptom1.findViewById(R.id.donebtn);
        chechimg=sysptom1.findViewById(R.id.chechsysimg);
        chechimg.setImageResource(R.drawable.checkcough);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(offset==0){
                    offset=offset+1;
                    patientCase.setDrycough(true);
                    sysquestions.setText(questions.get(offset));
                    chechimg.setImageResource(R.drawable.checkfever);}
                else if (offset==1){
                    offset=offset+1;
                    patientCase.setHeadache(true);
                    sysquestions.setText(questions.get(offset));
                    chechimg.setImageResource(R.drawable.checknose);
                }
                else if(offset==2){
                    patientCase.setSenselose(false);
                    offset=offset+1;
                    sysquestions.setText(questions.get(offset));
                    chechimg.setImageResource(R.drawable.checktemprature);
                }
                else if(offset==3){
                    patientCase.setYourtemperature(true);
                    sysquestions.setVisibility(View.INVISIBLE);
                    yes.setVisibility(View.INVISIBLE);
                    no.setVisibility(View.INVISIBLE);
                    reportbtn.setVisibility(View.INVISIBLE);
                    alldone.setVisibility(View.VISIBLE);
                    offset=0;
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(offset==0){
                patientCase.setDrycough(false);
                    offset=offset+1;
                    sysquestions.setText(questions.get(offset));
                    chechimg.setImageResource(R.drawable.checkfever);
                }
                else if (offset==1){
                    patientCase.setHeadache(false);
                    offset=offset+1;
                    sysquestions.setText(questions.get(offset));
                    chechimg.setImageResource(R.drawable.checknose);
                }
                else if(offset==2){
                    patientCase.setYourtemperature(false);
                    offset=offset+1;
                    sysquestions.setText(questions.get(offset));
                    chechimg.setImageResource(R.drawable.checktemprature);


                }
                else if(offset==3){
                    patientCase.setYourtemperature(false);
                    sysquestions.setText("Congrats all done");
                    yes.setVisibility(View.INVISIBLE);
                    no.setVisibility(View.INVISIBLE);
                    reportbtn.setVisibility(View.INVISIBLE);
                    alldone.setVisibility(View.VISIBLE);
                    chechimg.setImageResource(R.drawable.bestwayquarentine);
                    offset=0;


                }
            }
        });
        alldone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientCase.setLatitude(geoPoint.getLatitude());
                patientCase.setLongitude(geoPoint.getLongitude());
                myRef=FirebaseDatabase.getInstance().getReference("patientcase").push();
                String l=myRef.getKey();
                patientCase.setKey(l);
                myRef.setValue(patientCase).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Saved patientcase",Toast.LENGTH_LONG).show();
                        sysptom1.dismiss();
                    }
                });

            }
        });
    }
    private void setUpClusterer() {
        myPatiens=new ArrayList<>();
        // Position the map.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<PatientsOnMap>(this, map);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
       map.setOnCameraIdleListener(mClusterManager);
        map.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
     // addItems();
       additemsfromdatabsedata();
    }
    private  void additemsfromdatabsedata(){
        myRef=FirebaseDatabase.getInstance().getReference("patientcase");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myPatiens.clear();
                myPatiens=new ArrayList<>();
                for(DataSnapshot postsnap:dataSnapshot.getChildren()){
                    final PatientCase k=postsnap.getValue(PatientCase.class);
                    Toast.makeText(getApplicationContext(),k.getLatitude()+"lojk",Toast.LENGTH_LONG).show();
                    myPatiens.add(k);
                    mClusterManager.addItem(new PatientsOnMap(k.getLatitude(),k.getLongitude()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
    private void addItems() {
        // Set some lat/lng coordinates to start with.
        double lat = geoPoint.getLatitude();
        double lng = geoPoint.getLongitude();
        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            PatientsOnMap offsetItem = new PatientsOnMap(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
           }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.setMaxZoomPreference(10);
        map.setMyLocationEnabled(true);
         map.setBuildingsEnabled(true);
         map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

    }
}