package alterego.solutions.company_information.position_activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import alterego.solutions.company_information.R;

public class PositionActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        Bundle extras = getIntent().getExtras();
        latitude = extras.getDouble("Latitude");
        longitude = extras.getDouble("Longitude");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Company Position and move the camera
        LatLng position = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(position).title("Posizione Azienda"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
}
