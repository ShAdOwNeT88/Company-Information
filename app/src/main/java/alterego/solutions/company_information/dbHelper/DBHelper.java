package alterego.solutions.company_information.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import alterego.solutions.company_information.Company;

public class DBHelper extends SQLiteOpenHelper implements IDBHelper {

    public static final String DATABASE_NAME = "CompanyInformation";
    public static final String TABLE_COMPANY = "company";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMPANY_NAME = "Name";
    public static final String COLUMN_COMPANY_COUNTRY = "Country";
    public static final String COLUMN_COMPANY_STREET = "Street";
    public static final String COLUMN_COMPANY_TEL = "Phone";
    public static final String COLUMN_COMPANY_CELL = "Cellphone";
    public static final String COLUMN_COMPANY_DESCRIPTION = "Description";
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Database creation sql statement
        final String DATABASE_CREATE = "CREATE TABLE "
                + TABLE_COMPANY + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_COMPANY_NAME + " TEXT,"
                + COLUMN_COMPANY_COUNTRY + " TEXT,"
                + COLUMN_COMPANY_STREET + " TEXT,"
                + COLUMN_COMPANY_TEL + " TEXT,"
                + COLUMN_COMPANY_CELL + " TEXT,"
                + COLUMN_COMPANY_DESCRIPTION + " TEXT" + ")";

        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
        onCreate(db);
    }

    @Override
    public boolean addCompany(Company company){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_COMPANY_NAME,company.getName());
        values.put(DBHelper.COLUMN_COMPANY_COUNTRY,company.getCountry());
        values.put(DBHelper.COLUMN_COMPANY_STREET,company.getStreet());
        values.put(DBHelper.COLUMN_COMPANY_TEL,company.getTel());
        values.put(DBHelper.COLUMN_COMPANY_CELL, company.getCell());
        values.put(DBHelper.COLUMN_COMPANY_DESCRIPTION, company.getDescription());

        database.insert(TABLE_COMPANY, null, values);

        database.close();
        return true;
    }

    // Getting single company by id
    @Override
    public Company getCompany(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COMPANY, new String[]{COLUMN_ID, COLUMN_COMPANY_NAME,
                        COLUMN_COMPANY_COUNTRY, COLUMN_COMPANY_STREET, COLUMN_COMPANY_TEL, COLUMN_COMPANY_CELL, COLUMN_COMPANY_DESCRIPTION}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Company company = new Company(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));

        return company;
    }

    @Override
    public Company searchCompanyByName(String companyName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COMPANY, new String[]{COLUMN_ID, COLUMN_COMPANY_NAME,
                        COLUMN_COMPANY_COUNTRY, COLUMN_COMPANY_STREET, COLUMN_COMPANY_TEL, COLUMN_COMPANY_CELL, COLUMN_COMPANY_DESCRIPTION}, COLUMN_COMPANY_NAME + "=?",
                new String[]{companyName}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Company company = new Company(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));

        return company;
    }

    // Getting All Company
    @Override
    public List<Company> getAllCompanys() {
        List<Company> companyList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COMPANY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Company company = new Company();
                company.setId(Integer.parseInt(cursor.getString(0)));
                company.setName(cursor.getString(1));
                company.setCountry(cursor.getString(2));
                company.setStreet(cursor.getString(3));
                company.setTel(cursor.getString(4));
                company.setCell(cursor.getString(5));
                company.setDescription(cursor.getString(6));

                // Adding contact to list
                companyList.add(company);
            } while (cursor.moveToNext());
        }

        // return company list
        return companyList;
    }

    // Updating single company
    @Override
    public int updateCompany(Company company) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_COMPANY_NAME,company.getName());
        values.put(DBHelper.COLUMN_COMPANY_COUNTRY,company.getCountry());
        values.put(DBHelper.COLUMN_COMPANY_STREET,company.getStreet());
        values.put(DBHelper.COLUMN_COMPANY_TEL,company.getTel());
        values.put(DBHelper.COLUMN_COMPANY_CELL, company.getCell());
        values.put(DBHelper.COLUMN_COMPANY_DESCRIPTION, company.getDescription());

        // updating row
        return db.update(TABLE_COMPANY, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(company.getId())});
    }

    // Deleting single contact
    @Override
    public void deleteCompany(Company company) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPANY, COLUMN_ID + " = ?",
                new String[]{String.valueOf(company.getId())});
        db.close();
    }

    //Deleting all contacts from Company Table
    @Override
    public void deleteAllCompanys(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPANY, null, null);
    }

    //importing DB
    @Override
    public void importDB() {

        //TODO Fix folder
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "PackageName" + "//databases//" + "DatabaseName";
                String backupDBPath  = "/BackupFolder/DatabaseName";
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Log.e("Import DB", backupDB.toString());

            }
        } catch (Exception e) {
            Log.e("Import DB error:", e.toString());
        }
    }

    //export DB
    @Override
    public void exportDB() {

        SQLiteDatabase db = this.getReadableDatabase();

        File direct = new File(Environment.getExternalStorageDirectory() + "/CompanyInformation_Backup");

        if(!direct.exists())
        {
            if(direct.mkdir())
            {
                //directory is created;
            }

        }

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = db.getPath();
                String backupDBPath  = "/DatabaseDump";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(direct, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Log.e("Export DB", backupDB.toString());

            }
        } catch (Exception e) {

            Log.e("Export DB error", e.toString());

        }
    }

}
