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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.sql.SQLException;
import java.util.List;

import alterego.solutions.company_information.Company;
import alterego.solutions.company_information.R;
import alterego.solutions.company_information.dbHelper.DBHelper;
import alterego.solutions.company_information.search_company.SearchActivity;

public class AddActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DBHelper dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbHandler = new DBHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_add_company);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                //Company company = new Company("CazzoFiga", "Cazzolandia", "Via Pumicipu", "0811111", "32900000", "Alla rotonda fai come cazzo vuoi");
                //dbHandler.addCompany(company);

                dbHandler.exportDB();

                //dbHandler.deleteAllCompanys();

                /*// Reading all contacts
                Log.d("Reading: ", "Reading all company..");
                List<Company> companys = dbHandler.getAllCompanys();

                for (Company co : companys) {
                    String log = "Id: "+co.getId()+" ,Name: " + co.getName() + " ,Country: " + co.getCountry() + " ,Street: " + co.getStreet()
                            + " ,Tel: " + co.getTel() + " ,Cell: " + co.getCell() + " ,Description: " + co.getDescription();
                    // Writing Contacts to log
                    Log.d("Company in db: ", log);
                }*/

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
