package alterego.solutions.company_information.modify_activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import java.util.regex.Pattern;

import alterego.solutions.company_information.R;
import alterego.solutions.company_information.add_company.AddActivity;
import alterego.solutions.company_information.dbHelper.DbManagmentPresenter;
import alterego.solutions.company_information.runtime_permission.PermissionManager;
import alterego.solutions.company_information.search_company.SearchActivity;
import butterknife.BindColor;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;


public class ModifyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /*@Bind(R.id.company_name)
    EditText name;

    @Bind(R.id.company_city)
    EditText city;

    @Bind(R.id.company_street)
    EditText street;

    @Bind(R.id.company_phone)
    EditText phone;

    @Bind(R.id.company_cell)
    EditText cell;

    @Bind(R.id.company_description)
    EditText description;*/

    @BindColor(R.color.colorPrimary)
    int mColorPrimary;

    DbManagmentPresenter mManagerPresenter;

    private CustomTabsHelperFragment mCustomTabsHelperFragment;

    private CustomTabsIntent mCustomTabsIntent;

    EditText name,city,street,phone,cell,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_add_company);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        PermissionManager pm = new PermissionManager(this);
        pm.managingPermission();
        pm.managingPermission();

        Bundle extras = getIntent().getExtras();

        name = (EditText) findViewById(R.id.company_name);
        city = (EditText) findViewById(R.id.company_city);
        street = (EditText) findViewById(R.id.company_street);
        phone = (EditText) findViewById(R.id.company_phone);
        cell = (EditText) findViewById(R.id.company_cell);
        description = (EditText) findViewById(R.id.company_description);


        if(extras != null) {
            name.setText(extras.getString("nome"));
            city.setText(extras.getString("citta"));
            street.setText(extras.getString("via"));
            phone.setText(extras.getString("tel"));
            cell.setText(extras.getString("cell"));
            description.setText(extras.getString("descr"));
        }

        mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo(this);

        mCustomTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(mColorPrimary)
                .setShowTitle(true)
                .build();

        mManagerPresenter = new DbManagmentPresenter(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(!String.valueOf(name.getText()).isEmpty() && !String.valueOf(city.getText()).isEmpty() && !String.valueOf(street.getText()).isEmpty()
                        && !String.valueOf(phone.getText()).isEmpty()) {

                    ModifyPresenter mPresenter = new ModifyPresenter(String.valueOf(name.getText()), String.valueOf(city.getText()),
                            String.valueOf(street.getText()), String.valueOf(phone.getText()), String.valueOf(cell.getText()), String.valueOf(description.getText()),
                            getApplicationContext());

                    boolean isAdded = mPresenter.addCompany();

                    if(!isAdded){
                        Snackbar.make(view, "Azienda modificata nel database", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        deleteField();
                    }
                }

                else{
                    Log.e("TEST EDITTEXT",String.valueOf(name.getText()));
                    Snackbar.make(view, "Campi obbligatori: Nome, Città, Strada, Telefono", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.action_dump_db:
                mManagerPresenter.backupDB();
                return super.onOptionsItemSelected(item);

            case R.id.action_restore_db:
                //mManagerPresenter.restoreDB();
                getPathForDb();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.search_company:
                Intent search = new Intent(this, SearchActivity.class);
                startActivity(search);
                break;
            case R.id.add_company:
                Intent add = new Intent(this, AddActivity.class);
                startActivity(add);
                break;
            case R.id.about_us:
                CustomTabsHelperFragment.open(this, mCustomTabsIntent, Uri.parse("http://alterego.solutions"), mCustomTabsFallback);
                break;
            default:
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_add_company);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void deleteField(){
        name.setText("");
        city.setText("");
        street.setText("");
        phone.setText("");
        cell.setText("");
        description.setText("");
    }

    protected final CustomTabsActivityHelper.CustomTabsFallback mCustomTabsFallback =
            (activity, uri) -> {
                Toast.makeText(activity, R.string.custom_tab_error, Toast.LENGTH_SHORT).show();
                try {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, R.string.custom_tab_error_activity, Toast.LENGTH_SHORT)
                            .show();
                }
            };

    //Launch File Picker and get the path of the file for db.
    public void getPathForDb(){

        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1)
                .withFilter(Pattern.compile(".*\\.sqlite$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(false) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Do anything with file
            Log.e("PATH OF THE DB",filePath);
            mManagerPresenter.restoreDB(filePath);
        }
    }

    public void setFields(String nomaz, String citaz, String viaaz, String telaz, String cellaz, String descraz){
        name.setText(nomaz);
        city.setText(citaz);
        street.setText(viaaz);
        phone.setText(telaz);
        cell.setText(cellaz);
        description.setText(descraz);
    }
}
