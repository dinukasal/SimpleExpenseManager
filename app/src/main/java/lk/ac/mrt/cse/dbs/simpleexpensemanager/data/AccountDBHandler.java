package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InDBAccountDAO;

/**
 * Created by Dinuka Salwathura on 12/4/2015.
 */
public class AccountDBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "data";

    // Account table name
    private static final String TABLE_ACCOUNT = "account";

    // Account Table Columns names
    private static final String KEY_ACCNO = "accountNo";
    private static final String KEY_BALANCE = "balance";
    private static final String KEY_BANK="bank";
    private static final String KEY_ACC_HOLDER="accHolder";


    public AccountDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + "("
                + KEY_ACCNO + " INTEGER PRIMARY KEY," +KEY_BANK+" TEXT ,"+KEY_ACC_HOLDER+" TEXT ,"+
                KEY_BALANCE + " DOUBLE " + ")";
        db.execSQL(CREATE_ACCOUNT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);

        // Create tables again
        onCreate(db);
    }
    // Adding new account
    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ACCNO, account.getAccountNo()); //
        values.put(KEY_BANK,account.getBankName());
        values.put(KEY_BALANCE, account.getBalance());


        // Inserting Row
        db.insert(TABLE_ACCOUNT, null, values);
        db.close(); // Closing database connection
    }


    // Getting single account
    public Account getAccount(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ACCOUNT, new String[] { KEY_ACCNO
                         ,KEY_BALANCE }, KEY_ACCNO + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Account account= new Account(cursor.getString(0),
                cursor.getString(1), cursor.getString(2),Double.valueOf(cursor.getString(3)));
        // return account
        return account;
    }
    // Getting All Accounts
    public Map<String,Account> getAllAccounts() {
        Map<String,Account> accounts = new HashMap<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Account account = new Account(cursor.getString(0),cursor.getString(1),cursor.getString(2),Double.valueOf(cursor.getString(3)));
                // Adding account to list
                accounts.put(account.getAccountNo(),account);
            } while (cursor.moveToNext());
        }

        // return account list
        return accounts;
    }

    // Updating single contact
    public int updateAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_BANK, account.getBankName());
        values.put(KEY_ACC_HOLDER, account.getAccountHolderName());
        values.put(KEY_BALANCE,account.getBalance());

        // updating row
        return db.update(TABLE_ACCOUNT, values, KEY_ACCNO + " = ?",
                new String[] { String.valueOf(account.getAccountNo()) });
    }

    // Deleting single contact
    public void deleteAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNT, KEY_ACCNO + " = ?",
                new String[] { String.valueOf(account.getAccountNo()) });
        db.close();
    }
}
