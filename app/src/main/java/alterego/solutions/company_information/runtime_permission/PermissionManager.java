package alterego.solutions.company_information.runtime_permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alterego.solutions.company_information.R;

public class PermissionManager implements IPermissionManager {

    Activity activity;

    public PermissionManager(Activity Activity) {
        this.activity = Activity;
    }

    private int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    public void managingPermission() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CALL_PHONE);
        int locationPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int positionPermission = ContextCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            //check for call permission
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            //check for write external storage permission
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (positionPermission != PackageManager.PERMISSION_GRANTED) {
            //check for write external storage permission
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            //Request multiple permission with multiple dialog
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }
}
