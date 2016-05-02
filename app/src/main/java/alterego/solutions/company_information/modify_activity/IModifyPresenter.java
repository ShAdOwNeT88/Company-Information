package alterego.solutions.company_information.modify_activity;

import alterego.solutions.company_information.Company;
import alterego.solutions.company_information.dbHelper.DBHelper;

public interface IModifyPresenter {

    boolean addCompany();
    boolean checkIfCompanyExist(Company cmp, DBHelper helper);
}
