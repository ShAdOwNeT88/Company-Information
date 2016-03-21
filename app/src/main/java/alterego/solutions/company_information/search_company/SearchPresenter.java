package alterego.solutions.company_information.search_company;


import android.content.Context;

import java.util.ArrayList;

import alterego.solutions.company_information.Company;
import alterego.solutions.company_information.dbHelper.DBHelper;

public class SearchPresenter {

    String search;
    Context context;
    DBHelper mHelper;
    ArrayList<Company> companies = new ArrayList<>();

    public SearchPresenter(String Companysearched, Context Context) {
        this.context = Context;
        this.search = Companysearched.toUpperCase();
    }

    public ArrayList<Company> manageQuery(){
        if(search.equals("TUTTE")){
            searchAllCompanies();
        }
        else searchCompany();


        return companies;
    }

    //Local method for search company using DBHelper for database manipulation
    private ArrayList<Company> searchCompany(){

        mHelper = new DBHelper(context);
        companies = mHelper.searchCompanyByName(search);

        return companies;
    }

    //Local method for return all compaies with blank query in search view
    private ArrayList<Company> searchAllCompanies(){
        mHelper = new DBHelper(context);
        companies = (ArrayList<Company>)mHelper.getAllCompanys();

        return companies;
    }
}
