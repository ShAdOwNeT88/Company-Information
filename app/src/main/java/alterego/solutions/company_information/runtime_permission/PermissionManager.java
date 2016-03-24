package alterego.solutions.company_information.runtime_permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import alterego.solutions.company_information.R;

public class PermissionManager implements IPermissionManager {

    Activity activity;

    public PermissionManager(Activity Activity) {
        this.activity = Activity;
    }

    private int MY_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    public void managingPermission() {

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSION_WRITE_EXTERNAL_STORAGE) {

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Memory permission has been granted, preview can be displayed
                Toast.makeText(activity, R.string.permision_available_storage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, R.string.permissions_not_granted_storage, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
