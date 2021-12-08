package algonquin.cst2335.sing1489.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalStorageDataBase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "OwlDataBase.db";


    /**
     * @param context parameter of LocalStorageDatabase
     */
    public LocalStorageDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * @param db parameter of SQLiteDatabase to create a database
     */
    //this function is creating a database and is executing
    // a sql qurey from the Entity class to create Entries for the OwlDataBase(main of 2st Rv)
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Entity.OwlDataBase.SQL_CREATE_ENTRIES);
    }

    /**
     * @param db  database parameter as db
     * @param oldVersion  it takes the oldversion of the schema
     * @param newVersion it update into new version of it after this function
     */
    //onUpgrade() function is used for upgrading database when changes occurs in the schema
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //query running from the ENTITY class
        db.execSQL(Entity.OwlDataBase.SQL_DELETE_ENTRIES);
        onCreate(db);
    }


}
