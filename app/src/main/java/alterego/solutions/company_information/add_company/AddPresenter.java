package alterego.solutions.company_information.add_company;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import alterego.solutions.company_information.Company;
import alterego.solutions.company_information.dbHelper.DBHelper;

public class AddPresenter implements IAddPresenter{

    String name,country,street,tel,cell,description;
    Context context;
    DBHelper mDbManager;

    public AddPresenter(String Name, String Country, String Street, String Tel, String Cell, String Description,Context Context) {

        this.name = Name;
        this.country = Country;
        this.street = Street;
        this.tel = Tel;
        this.cell = Cell;
        this.description = Description;

        this.context = Context;

    }




    @Override
    public boolean addCompany() {
        mDbManager = new DBHelper(context);

        Company cmp = new Company(name.toUpperCase(),country.toUpperCase(),street.toUpperCase(),tel.toUpperCase(),cell.toUpperCase(),description.toUpperCase());

        boolean exist = checkIfCompanyExist(cmp,mDbManager);

        if(exist){
            return true;
        }
        else {
            mDbManager.addCompany(cmp);
            return false;
        }
    }

    //Method for searching if Company is in database

    @Override
    public boolean checkIfCompanyExist(Company cmp,DBHelper helper) {

        boolean exsist = false;

        ArrayList<Company> companies = (ArrayList<Company>) helper.getAllCompanys();

        for(int i=0; i<companies.size(); i++){
            if(companies.get(i).getName().equalsIgnoreCase(cmp.getName())){
                exsist = true;
            }
        }

        return exsist;
    }


}
