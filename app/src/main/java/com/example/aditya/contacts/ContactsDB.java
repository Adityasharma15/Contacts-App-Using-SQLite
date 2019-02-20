package com.example.aditya.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDB {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "person_name";
    public static final String KEY_CELL = "_cell";

    public static final String DATABASE_NAME = "ContactsDB";
    public static final String DATABASE_TABLE = "ContactsTable";
    public static final int DATABASE_VERSION = 1;


    // Some Variables

    private DBHelper ourHelper;
    //just to get the clause/class that is using the specific database of ours
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    //Constructor for class

    public ContactsDB(Context context)
    {
        ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            /**
             * Called when the database is created for the first time. This is where the
             * creation of tables and the initial population of the tables should happen.
             *
             * @param db The database.
             */

            String sqlCode = "CREATE " + "TABLE " + DATABASE_TABLE + " ( "
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_NAME + " TEXT NOT NULL, "
                    + KEY_CELL + " TEXT NOT NULL" + ");";

            db.execSQL(sqlCode);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /**
             * Called when the database needs to be upgraded. The implementation
             * should use this method to drop tables, add tables, or do anything else it
             * needs to upgrade to the new schema version.
             *
             * <p>
             * The SQLite ALTER TABLE documentation can be found
             * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
             * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
             * you can use ALTER TABLE to rename the old table, then create the new table and then
             * populate the new table with the contents of the old table.
             * </p><p>
             * This method executes within a transaction.  If an exception is thrown, all changes
             * will automatically be rolled back.
             * </p>
             *
             * @param db         The database.
             * @param oldVersion The old database version.
             * @param newVersion The new database version.
             */

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public ContactsDB open() throws SQLException {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public long createEntry(String name, String cell) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_CELL, cell);

        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public String getData() {
        String[] columns = new String[]{KEY_ROWID, KEY_NAME, KEY_CELL};

        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        String result = "";

        int iRowID = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iCell = c.getColumnIndex(KEY_CELL);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + " " + c.getString(iRowID) + ".   " + c.getString(iName) + "  ->  " + c.getString(iCell) + "\n";
        }

        c.close();

        return result;

    }

    public long deleteEntry(String rowId) {
        return ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=?", new String[]{rowId});
    }

    public long udateEntry(String rowId, String name, String cell) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_CELL, cell);

        return ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=?", new String[]{rowId});

    }

    public String TopId() {
        String [] columns = new String[] {KEY_ROWID};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        int RowID = c.getColumnIndex(KEY_ROWID);

        c.moveToFirst();
        return c.getString(RowID);
    }


}