package com.kalu.covidmap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class PatientsOnMap implements ClusterItem {
    public LatLng position;
    public String title;
    public String snippet;

    public PatientsOnMap() {
    }

    public PatientsOnMap(double lat, double lon) {
        this.position = new LatLng(lat,lon);
    }

//    public PatientsOnMap(double lat, double lon, String patientid, String phonenumber) {
//        this.position = new LatLng(lat,lon);
//        this.patientid = patientid;
//        this.phonenumber = phonenumber;
//    }
//
//    public PatientsOnMap(String patientid, String phonenumber) {
//        this.patientid = patientid;
//        this.phonenumber = phonenumber;
//    }

    @Override
    public LatLng getPosition() {
        return position;
    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }

}