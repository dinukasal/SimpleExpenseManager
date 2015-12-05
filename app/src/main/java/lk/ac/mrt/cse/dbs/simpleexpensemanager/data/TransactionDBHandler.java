package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dinuka Salwathura on 12/4/2015.
 */
public class TransactionDBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "data";

    // Account table name
    private static final String TABLE_ACCOUNT = "transaction";

    // Account Table Columns names
    private static final String KEY_ACCNO = "accountNo";
    private static final String KEY_DATE = "date";
    private static final String KEY_EXPENSE_TYPE = "expenseType";
    private static final String KEY_AMOUNT = "amount";


    public TransactionDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + "("
                + KEY_ACCNO + " INTEGER PRIMARY KEY,"+KEY_DATE+"DATE," + KEY_EXPENSE_TYPE + " TEXT,"
                + KEY_AMOUNT + " TEXT)" ;
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
}
