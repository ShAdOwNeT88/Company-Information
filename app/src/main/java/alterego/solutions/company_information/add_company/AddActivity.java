package alterego.solutions.company_information.add_company;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import alterego.solutions.company_information.R;
import alterego.solutions.company_information.dbHelper.DBHelper;
import alterego.solutions.company_information.search_company.SearchActivity;
import butterknife.Bind;
import butterknife.ButterKnife;


public class AddActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(R.id.company_name)
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
    EditText description;


    DBHelper dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        dbHandler = new DBHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_add_company);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final String nm = name.getText().toString();
        final String ct = city.getText().toString();
        final String str = street.getText().toString();
        final String ph = phone.getText().toString();
        final String cl = cell.getText().toString();
        final String desc = description.getText().toString();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddPresenter mPresenter = new AddPresenter(nm,ct,str,ph,cl,desc,getApplicationContext());
                mPresenter.addCompany();
                Snackbar.make(view, "Azienda aggiunta al database", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                break;
            default:
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_add_company);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}