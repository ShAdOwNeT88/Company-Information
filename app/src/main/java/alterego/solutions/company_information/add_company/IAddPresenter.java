package alterego.solutions.company_information.add_company;

import alterego.solutions.company_information.Company;
import alterego.solutions.company_information.dbHelper.DBHelper;

public interface IAddPresenter {

    boolean addCompany();
    boolean checkIfCompanyExist(Company cmp, DBHelper helper);
}
