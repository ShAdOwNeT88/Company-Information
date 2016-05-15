package alterego.solutions.company_information.position_activity;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

public interface IPositionPresenter {
    public void searchPosition() throws IOException;
    public LatLng[] getLatLong(String address) throws IOException;
}
