package alterego.solutions.company_information.models;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;

import alterego.solutions.company_information.Company;
import alterego.solutions.company_information.R;
import alterego.solutions.company_information.add_company.AddActivity;
import alterego.solutions.company_information.dbHelper.DBHelper;
import alterego.solutions.company_information.modify_activity.ModifyActivity;
import alterego.solutions.company_information.position_activity.PositionActivity;
import alterego.solutions.company_information.position_activity.PositionPresenter;
import alterego.solutions.company_information.runtime_permission.PermissionManager;

public class CompanyAdapter extends RecyclerView.
        Adapter<CompanyAdapter.CompanyHolder> {

    private static String LOG_TAG = "CompanyAdapter";
    private ArrayList<Company> mDataset;
    String description;
    Context context;
    int clickedPos;

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

        //get the position of click in the list
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            //On click display the description of company in MaterialDialog
            @Override
            public void onClick(View v) {
                clickedPos = holder.getAdapterPosition();

                //retrive description based on the position clicked
                description = mDataset.get(clickedPos).getDescription();

                MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                        .title("Indicazioni Stradali " + holder.name.getText())
                        .content(description)
                        .positiveText("OK");

                MaterialDialog dialog = builder.build();
                dialog.show();

            }
        });
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

        TextView name, country, street, phone, cellphone;

        public CompanyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.company_name);
            country = (TextView) itemView.findViewById(R.id.company_country);
            street = (TextView) itemView.findViewById(R.id.company_street);
            phone = (TextView) itemView.findViewById(R.id.company_phone);
            cellphone = (TextView) itemView.findViewById(R.id.company_cell);

            //Launch call when click on phone number
            phone.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("MissingPermission")
                @Override
                public void onClick(View v) {
                    String number = "tel:" + phone.getText().toString().trim();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                    context.startActivity(callIntent);
                }
            });

            //Launch call when click on cellphone number
            cellphone.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("MissingPermission")
                @Override
                public void onClick(View v) {
                    String number = "tel:" + cellphone.getText().toString().trim();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                    context.startActivity(callIntent);
                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int clickedpos = getAdapterPosition();
                    Log.e("Long press pos:", String.valueOf(clickedpos));

                    //Launch submenu with entry delete/edit for an entry
                    itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){

                        DBHelper dbHelper = new DBHelper(context);
                        String descr = mDataset.get(clickedpos).getDescription();

                        @Override
                        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

                            menu.add("Elimina").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    Company c = new Company(name.getText().toString(),country.getText().toString(),street.getText().toString()
                                            ,phone.getText().toString(),cellphone.getText().toString(),descr);

                                    dbHelper.deleteCompany(c);
                                    CharSequence text = "Eliminazione Azienda: " + name.getText();
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    return true;

                                }
                            });

                            menu.add("Modifica").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    Company c = new Company(name.getText().toString(),country.getText().toString(),street.getText().toString()
                                            ,phone.getText().toString(),cellphone.getText().toString(),descr);

                                    Intent intent = new Intent(context, ModifyActivity.class);

                                    //Passing extras for updating Company
                                    intent.putExtra("nome", c.getName());
                                    intent.putExtra("citta",c.getCountry());
                                    intent.putExtra("via",c.getStreet());
                                    intent.putExtra("tel",c.getTel());
                                    intent.putExtra("cell",c.getCell());
                                    intent.putExtra("descr",c.getDescription());

                                    context.startActivity(intent);

                                    CharSequence text = "Modifica Voce!";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();

                                    //delete old company
                                    dbHelper.deleteCompany(c);

                                    return true;

                                }
                            });

                            menu.add("Posizione Azienda").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    Company c = new Company(name.getText().toString(),country.getText().toString(),street.getText().toString()
                                            ,phone.getText().toString(),cellphone.getText().toString(),descr);

                                    PositionPresenter presenter = new PositionPresenter(context,c);
                                    try {
                                        presenter.searchPosition();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    CharSequence text = "Posizione Azienda!!";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();

                                    return true;

                                }
                            });
                        }

                    });
                    return false;
                }
            });
        }
    }
}