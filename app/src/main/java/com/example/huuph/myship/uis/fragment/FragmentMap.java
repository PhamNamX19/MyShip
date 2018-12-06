package com.example.huuph.myship.uis.fragment;



import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.huuph.myship.R;
import com.example.huuph.myship.adapter.DirectionPointListener;
import com.example.huuph.myship.adapter.GetPathFromLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FragmentMap extends Fragment   {
    private static FragmentMap instance;
    MapView mMapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient client;



    private Context context;


    public static FragmentMap getInstance() {
        if (instance == null){
            instance = new FragmentMap();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_maps,container,false);
        Button button1 =(Button)view.findViewById(R.id.button2);
        final EditText editText  = (EditText)view.findViewById(R.id.editText1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String location = editText.getText().toString().trim();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    LatLng source = new LatLng(20.9835893, 105.79761588);
                    LatLng destination = new LatLng(address.getLatitude(), address.getLongitude());

                    new GetPathFromLocation(source, destination, new DirectionPointListener() {
                        @Override
                        public void onPath(PolylineOptions polyLine) {
                            googleMap.addPolyline(polyLine);
                        }
                    }).execute();
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            marker.remove();
                            return true;
                        }
                    });


                }

                else {

                    Toast.makeText(getActivity(), "location not found", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {

            @SuppressLint("MissingPermission")
            @Override

            public void onMapReady(GoogleMap mMap) {

                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(20.9835893, 105.79761588);

                googleMap.addMarker(new MarkerOptions().position(sydney).title("Triều Khúc City").snippet("Marker Description"));





                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                googleMap.getUiSettings().setMapToolbarEnabled(true);
                googleMap.getUiSettings().setTiltGesturesEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);

                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setCompassEnabled(true);




            }
        });


        return view;

    }






    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
