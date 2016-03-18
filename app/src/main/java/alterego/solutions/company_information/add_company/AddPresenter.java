package alterego.solutions.company_information.add_company;

import android.content.Context;

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
    public void addCompany() {

        mDbManager = new DBHelper(context);
        Company cmp = new Company(name,country,street,tel,cell,description);
        mDbManager.addCompany(cmp);
    }
}
