package alterego.solutions.company_information.dbHelper;

import java.util.List;

import alterego.solutions.company_information.Company;

public interface IDBHelper{

    boolean addCompany(Company company);
    Company getCompany(int id);
    List<Company> searchCompanyByName(String companyName);
    List<Company> getAllCompanys();
    int updateCompany(Company company);
    void deleteCompany(Company company);
    void deleteAllCompanys();
    String importDB(String pathOfImport);
    String exportDB();

}
