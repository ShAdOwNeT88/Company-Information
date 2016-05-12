package alterego.solutions.company_information.position_activity;


import android.content.Context;
import android.content.Intent;

import alterego.solutions.company_information.Company;

public class PositionPresenter implements IPositionPresenter{

    Context context;
    Company company;

    public PositionPresenter(Context Context, Company Company) {

        this.context = Context;
        this.company = Company;
    }

    @Override
    public void searchPosition() {

        Intent intent = new Intent(context, PositionActivity.class);
        context.startActivity(intent);
    }
}
