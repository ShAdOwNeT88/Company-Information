package alterego.solutions.company_information.search_company;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import com.crashlytics.android.Crashlytics;

import alterego.solutions.company_information.runtime_permission.PermissionManager;
import io.fabric.sdk.android.Fabric;
import org.chromium.customtabsclient.CustomTabsActivityHelper;

import java.util.ArrayList;
import java.util.List;

import alterego.solutions.company_information.Company;
import alterego.solutions.company_information.R;
import alterego.solutions.company_information.add_company.AddActivity;
import alterego.solutions.company_information.dbHelper.DBHelper;
import alterego.solutions.company_information.dbHelper.DbManagmentPresenter;
import alterego.solutions.company_information.models.CompanyAdapter;
import butterknife.Bind;
import butterknife.BindColor;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;

public class SearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(R.id.searchView_company)
    SearchView mCompanySearchView;

    @BindColor(R.color.colorPrimary)
    int mColorPrimary;

    DbManagmentPresenter mManagerPresenter;

    SearchPresenter mSearchPresenter;

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    private static String LOG_TAG = "CardViewActivity";

    private CustomTabsHelperFragment mCustomTabsHelperFragment;

    private CustomTabsIntent mCustomTabsIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_search_company);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        PermissionManager pm = new PermissionManager(this);
        pm.managingPermission();
        pm.managingPermission();

        final Context ctx = this;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.search_company_recycle_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mManagerPresenter = new DbManagmentPresenter(this);

        mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo(this);

        mCustomTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(mColorPrimary)
                .setShowTitle(true)
                .build();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mCompanySearchView = (SearchView) findViewById(R.id.searchView_company);

        mCompanySearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mCompanySearchView.setIconifiedByDefault(false);

        mCompanySearchView.setQueryHint("Nome Azienda");
        mCompanySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchPresenter = new SearchPresenter(query, getApplicationContext());
                mAdapter = new CompanyAdapter(mSearchPresenter.manageQuery(),ctx);
                mRecyclerView.setAdapter(mAdapter);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_search_company);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

