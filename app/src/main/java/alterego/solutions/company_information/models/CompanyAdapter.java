package alterego.solutions.company_information.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import alterego.solutions.company_information.Company;
import alterego.solutions.company_information.R;

public class CompanyAdapter extends RecyclerView.
        Adapter<CompanyAdapter.CompanyHolder>{

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Company> mDataset;
    //private static MyClickListener myClickListener;

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

    public class CompanyHolder extends RecyclerView.ViewHolder implements OnClickListener {

        TextView name,country,street,phone,cellphone;

        public CompanyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.company_name);
            country = (TextView) itemView.findViewById(R.id.company_country);
            street = (TextView) itemView.findViewById(R.id.company_street);
            phone = (TextView) itemView.findViewById(R.id.company_phone);
            cellphone = (TextView) itemView.findViewById(R.id.company_cell);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //add click reaction
        }
    }

    public CompanyAdapter(ArrayList<Company> myDataset) {
        mDataset = myDataset;
    }

}

