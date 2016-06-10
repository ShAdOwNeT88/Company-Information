package alterego.solutions.company_information.position_activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.api.BooleanResult;
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
    String nameCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        Bundle extras = getIntent().getExtras();
        latitude = extras.getDouble("Latitude");
        longitude = extras.getDouble("Longitude");
        nameCompany = extras.getString("CompanyName");

        if(latitude != 0 && longitude != 0){
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        else{
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                    .title("Informazione di servizio ")
                    .content("L'indirizzo registrato per l'azienda non è corretto e non può essere aperto nella mappa.")
                    .positiveText("OK");

            MaterialDialog dialog = builder.build();
            dialog.show();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;
            // Add a marker in Company Position and move the camera
            LatLng position = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(position).title(nameCompany));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
    }
}
