package alterego.solutions.company_information.runtime_permission;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;


public interface IPermissionManager extends ActivityCompat.OnRequestPermissionsResultCallback {

    void managingPermission();
}
