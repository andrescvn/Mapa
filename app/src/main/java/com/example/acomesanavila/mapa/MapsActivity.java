package com.example.acomesanavila.mapa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.*;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final String TAG = "gpslog";
    private final int LOCATION_REQUEST_CODE = 1;
    private final long MIN_CAMBIO_DISTANCIA_PARA_UPDATES = (long) 20; // 20 metro
    private final long MIN_TIEMPO_ENTRE_UPDATES = 10000; // 10 sg
    private CircleOptions circle1;
    private CircleOptions circle2;
    private CircleOptions circle3;
    private GoogleMap mMap;
    private LocationManager mLocMgr;
    private LatLng tesoro;
    private Location tesoroLoc;
    private LatLng tesoro2;
    private Location tesoroLoc2;
    private LatLng tesoro3;
    private Location tesoroLoc3;
    private TextView text;
    private Circle circles;
    private Circle circles2;
    private Circle circles3;
    public Intent intento;
    private int codigo = 0;
    private Marker marca1;
    private Marker marca2;
    private Marker marca3;
    private boolean cogido1 = false;
    private boolean cogido2 = false;
    private boolean cogido3 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        text = findViewById(R.id.lat);
        mLocMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "No se tienen permisos necesarios!, se requieren.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 225);
            return;
        } else {
            Log.i(TAG, "Permisos necesarios OK!.");
            mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIEMPO_ENTRE_UPDATES, MIN_CAMBIO_DISTANCIA_PARA_UPDATES, locListener, Looper.getMainLooper());
        }
        tesoro = new LatLng(42.236947372570185, -8.717481046915054);
        tesoroLoc = new Location(LocationManager.GPS_PROVIDER);
        tesoroLoc.setLatitude(42.236947372570185);
        tesoroLoc.setLongitude(-8.717481046915054);
        tesoro2 = new LatLng(42.23766782774763, -8.715309798717499);
        tesoroLoc2 = new Location(LocationManager.GPS_PROVIDER);
        tesoroLoc2.setLatitude(42.23766782774763);
        tesoroLoc2.setLongitude(-8.715309798717499);
        tesoro3 = new LatLng(42.23695293287117, -8.712654411792755);
        tesoroLoc3 = new Location(LocationManager.GPS_PROVIDER);
        tesoroLoc3.setLatitude(42.23695293287117);
        tesoroLoc3.setLongitude(-8.712654411792755);
        circle1 = new CircleOptions();
        circle2 = new CircleOptions();
        circle3 = new CircleOptions();
        //creo intent y metodo on click
        intento = new Intent(MapsActivity.this, MainActivity.class);
        Button fab = (Button) findViewById(R.id.boton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(intento, codigo);
            }
        });
    }

    public LocationListener locListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.i(TAG, "Lat " + location.getLatitude() + " Long " + location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16.5F));
            text.setText("Distancia : " + String.valueOf((int) location.distanceTo(tesoroLoc)) + "m");
            if ((int) location.distanceTo(tesoroLoc) <= 50 && (int) location.distanceTo(tesoroLoc) > 25 && cogido1 == false) {
                circle1.center(new LatLng(42.236947372570185, -8.717481046915054))
                        .radius(50)
                        .strokeColor(Color.parseColor("#E74C3C"))//rojo
                        .strokeWidth(4)
                        .fillColor(Color.argb(32, 243, 150, 32));
                circles = mMap.addCircle(circle1);
            } else if ((int) location.distanceTo(tesoroLoc) > 50 && cogido1 == false) {
                circle1.center(new LatLng(42.236485069555, -8.71738851070404))
                        .radius(100)
                        .strokeColor(Color.parseColor("#0D47A1"))
                        .strokeWidth(4)
                        .fillColor(Color.argb(32, 33, 150, 243));
                circles = mMap.addCircle(circle1);
            } else if ((int) location.distanceTo(tesoroLoc) < 25 && cogido1 == false) {
                circles.setVisible(false);
                marca1 = mMap.addMarker(new MarkerOptions().position(tesoro).title("Marca de Tesoro"));
            }

            if ((int) location.distanceTo(tesoroLoc2) <= 50 && (int) location.distanceTo(tesoroLoc) > 25 && cogido2 == false) {
                circle2.center(new LatLng(42.23766782774763, -8.715309798717499))
                        .radius(50)
                        .strokeColor(Color.parseColor("#E74C3C"))
                        .strokeWidth(4)
                        .fillColor(Color.argb(32, 243, 150, 32));
                circles2 = mMap.addCircle(circle2);
            } else if ((int) location.distanceTo(tesoroLoc2) > 50 && cogido2 == false) {
                circle2.center(new LatLng(42.237626920141054, -8.714895397424698))
                        .radius(100)
                        .strokeColor(Color.parseColor("#0D47A1"))
                        .strokeWidth(4)
                        .fillColor(Color.argb(32, 33, 150, 243));
                circles2 = mMap.addCircle(circle2);


            } else if ((int) location.distanceTo(tesoroLoc2) < 25 && cogido2 == false) {
                circles2.setVisible(false);
                marca2 = mMap.addMarker(new MarkerOptions().position(tesoro2).title("Marca de Tesoro"));
            }

            if ((int) location.distanceTo(tesoroLoc3) <= 50 && (int) location.distanceTo(tesoroLoc) > 25 && cogido3 == false) {
                circle3.center(new LatLng(42.23695293287117, -8.712654411792755))
                        .radius(50)
                        .strokeColor(Color.parseColor("#E74C3C"))
                        .strokeWidth(4)
                        .fillColor(Color.argb(32, 243, 150, 32));
                circles3 = mMap.addCircle(circle3);

            } else if ((int) location.distanceTo(tesoroLoc3) > 50 && cogido3 == false) {
                circle3.center(new LatLng(42.237521672168846, -8.712643682956696))
                        .radius(100)
                        .strokeColor(Color.parseColor("#0D47A1"))
                        .strokeWidth(4)
                        .fillColor(Color.argb(32, 33, 150, 243));
                circles3 = mMap.addCircle(circle3);
            } else if ((int) location.distanceTo(tesoroLoc3) < 25 && cogido3 == false) {
                circles3.setVisible(false);
                marca3 = mMap.addMarker(new MarkerOptions().position(tesoro3).title("Marca de Tesoro"));

            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "onStatusChanged()");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i(TAG, "onProviderDisabled()");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(TAG, "onStatusChanged()");
        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.23647553788579, -8.714223504066467), 16.5F));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_OK".
        if (resultCode == RESULT_OK) {
            // Comprobamos el codigo de nuestra llamada
            if (requestCode == codigo) {
                // Recojemos el dato que viene en el Intent (se pasa por parámetro con el nombre de data)
                String qr = data.getExtras().getString("retorno");
                if (qr.equals("tesoro1")) {
                   marca1.setVisible(false);
                    cogido1 = true;
                } else if (qr.equals("tesoro2")) {
                    marca2.setVisible(false);
                    cogido2 = true;
                } else if (qr.equals("tesoro3")){
                    marca3.setVisible(false);
                    cogido3 = true;
                }
            }
        }
    }
}



