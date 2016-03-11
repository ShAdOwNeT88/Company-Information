package alterego.solutions.company_information.dbHelper;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import alterego.solutions.company_information.Company;

public interface IDBHelper{

    boolean addCompany(Company company);
    Company getCompany(int id);
    List<Company> getAllCompanys();
    int updateCompany(Company company);
    void deleteCompany(Company company);
    void deleteAllCompanys();
    void importDB();
    void exportDB();

}
