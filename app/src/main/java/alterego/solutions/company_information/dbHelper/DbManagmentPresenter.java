package alterego.solutions.company_information.dbHelper;


import android.content.Context;

public class DbManagmentPresenter implements IDbManagment{

    Context context;
    DBHelper manager;
    public DbManagmentPresenter(Context Context) {

        this.context = Context;
        manager =  new DBHelper(context);
    }

    @Override
    public void backupDB() {
        manager.exportDB();
    }

    @Override
    public void restoreDB() {
        manager.importDB();
    }
}
