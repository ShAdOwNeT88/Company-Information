package alterego.solutions.company_information.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import java.util.ArrayList;

import alterego.solutions.company_information.Company;
import alterego.solutions.company_information.R;

public class CompanyAdapter extends RecyclerView.
        Adapter<CompanyAdapter.CompanyHolder>{

    private static String LOG_TAG = "CompanyAdapter";
    private ArrayList<Company> mDataset;
    String description;
    Context context;

    public CompanyAdapter(ArrayList<Company> myDataset, Context applicationContext) {
        mDataset = myDataset;
        context = applicationContext;
    }

    @Override
    public CompanyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        CompanyHolder companyHolder = new CompanyHolder(view);
        return companyHolder;
    }

    @Override
    public void onBindViewHolder(CompanyHolder holder, int position) {
        holder.name.setText(mDataset.get(position).getName());
        holder.country.setText(mDataset.get(position).getCountry());
        holder.street.setText(mDataset.get(position).getStreet());
        holder.phone.setText(mDataset.get(position).getTel());
        holder.cellphone.setText(mDataset.get(position).getCell());
        //save company description into a string
        description = mDataset.get(position).getDescription();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItem(Company company, int index) {
        mDataset.add(index, company);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public class CompanyHolder extends RecyclerView.ViewHolder{

        TextView name,country,street,phone,cellphone;

        public CompanyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.company_name);
            country = (TextView) itemView.findViewById(R.id.company_country);
            street = (TextView) itemView.findViewById(R.id.company_street);
            phone = (TextView) itemView.findViewById(R.id.company_phone);
            cellphone = (TextView) itemView.findViewById(R.id.company_cell);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    //on click show a material dialog with information to easly find the way

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                            .title("Indicazioni Stradali " + name.getText())
                            .content(description)
                            .positiveText("OK");

                    MaterialDialog dialog = builder.build();
                    dialog.show();
                }
            });
        }
    }
}

