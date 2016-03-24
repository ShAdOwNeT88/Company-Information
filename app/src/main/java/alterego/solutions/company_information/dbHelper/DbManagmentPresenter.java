package alterego.solutions.company_information.dbHelper;


import android.content.Context;
import android.widget.Toast;

public class DbManagmentPresenter implements IDbManagment{

    Context context;
    DBHelper manager;
    public DbManagmentPresenter(Context Context) {

        this.context = Context;
        manager =  new DBHelper(context);
    }

    @Override
    public void backupDB() {
        String s = manager.exportDB();
        Toast toast = Toast.makeText(context,s,Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void restoreDB() {
        String s = manager.importDB();
        Toast toast = Toast.makeText(context,s,Toast.LENGTH_LONG);
        toast.show();
    }
}
