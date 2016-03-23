package alterego.solutions.company_information.add_company;

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

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import alterego.solutions.company_information.R;
import alterego.solutions.company_information.dbHelper.DBHelper;
import alterego.solutions.company_information.dbHelper.DbManagmentPresenter;
import alterego.solutions.company_information.search_company.SearchActivity;
import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;


public class AddActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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

        name = (EditText) findViewById(R.id.company_name);
        city = (EditText) findViewById(R.id.company_city);
        street = (EditText) findViewById(R.id.company_street);
        phone = (EditText) findViewById(R.id.company_phone);
        cell = (EditText) findViewById(R.id.company_cell);
        description = (EditText) findViewById(R.id.company_description);

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

                    AddPresenter mPresenter = new AddPresenter(String.valueOf(name.getText()), String.valueOf(city.getText()),
                            String.valueOf(street.getText()), String.valueOf(phone.getText()), String.valueOf(cell.getText()), String.valueOf(description.getText()),
                            getApplicationContext());
                    mPresenter.addCompany();
                    Snackbar.make(view, "Azienda aggiunta al database", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    deleteField();
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
                mManagerPresenter.restoreDB();
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
        name.setText("Nome Azienda");
        city.setText("Città Azienda");
        street.setText("Via Azienda");
        phone.setText("Telefono Azienda");
        cell.setText("Cellulare Azienda");
        description.setText("Indicazioni Stradali Azienda");
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
}
