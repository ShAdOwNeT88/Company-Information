package alterego.solutions.company_information.position_activity;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import alterego.solutions.company_information.Company;
import rx.Observable;
import rx.Observer;

public class PositionPresenter implements IPositionPresenter {

    Context context;
    Company company;
    int MAX_RESULT = 3;

    public PositionPresenter(Context Context, Company Company) {

        this.context = Context;
        this.company = Company;
    }

    @Override
    public void searchPosition() throws IOException {
        //get the address of the company
        String address = company.getStreet().concat(" , ").concat(company.getCountry());
        //get Latitude and longitude for opening map
        LatLng[] coordinates = getLatLong(address);

        if(isNetworkAvailable()) {

            //Launching intent and passing latitude and longitude of company
            Intent intent = new Intent(context, PositionActivity.class);
            intent.putExtra("CompanyName",company.getName());
            intent.putExtra("Latitude", coordinates[0].latitude);
            intent.putExtra("Longitude", coordinates[0].longitude);
            context.startActivity(intent);
        }

        else{

            MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                    .title("Informazione di servizio ")
                    .content("Attivarre la connessione dati / Wifi per l'utlilizzo delle mappe")
                    .positiveText("OK");

            MaterialDialog dialog = builder.build();
            dialog.show();
        }
    }

    @Override
    public LatLng[] getLatLong(String address) throws IOException {
        Geocoder geocoder = new Geocoder(context);

        List<Address> addresses = geocoder.getFromLocationName(address, MAX_RESULT);
        final LatLng[] coordinates = new LatLng[1];

        Observable.from(addresses)
                .subscribe(new Observer<Address>() {
                    @Override
                    public void onCompleted() {
                        //Log.e("Address from observable: ","On Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e("On Error", e.toString());
                    }

                    @Override
                    public void onNext(Address address) {
                        coordinates[0] = new LatLng(address.getLatitude(),address.getLongitude());
                    }
                });
        return coordinates;
    }

    //Check if network is available
    @Override
    public boolean isNetworkAvailable() {
        boolean connection = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
            connection = true;
            //Log.e("NETWORK CONNECTION: " , String.valueOf(connection));
        }

        else if(activeNetworkInfo == null){
            connection = false;
            //Log.e("NETWORK CONNECTION: " , String.valueOf(connection));
        }

        return connection;
    }
}
