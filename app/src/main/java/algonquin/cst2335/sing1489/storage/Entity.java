package algonquin.cst2335.sing1489.storage;

import android.provider.BaseColumns;

public final class Entity {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Entity() {
    }

    // Inner class that defines the table contents
    //
    public static class OwlDataBase implements BaseColumns {
        public static final String TABLE_NAME = "OwlLocalDataBase";
        public static final String COLUMN_NAME_DEFINITION = "Definition";
        public static final String COLUMN_NAME_TYPE = "Type";
        public static final String COLUMN_NAME_EXAMPLE = "Example";
        public static final String COLUMN_NAME_IMAGE_URL = "ImageUrl";

//this is responsible for creating a TABLE with all the required items and declaring into srting (SQL_CREATE_ENTRIES)
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + OwlDataBase.TABLE_NAME + " (" +
                        OwlDataBase._ID + " INTEGER PRIMARY KEY," +
                        OwlDataBase.COLUMN_NAME_DEFINITION + " TEXT," +
                        OwlDataBase.COLUMN_NAME_TYPE + " TEXT," +
                        OwlDataBase.COLUMN_NAME_EXAMPLE + " TEXT," +
                        OwlDataBase.COLUMN_NAME_IMAGE_URL + " TEXT)";
//this query is responsible for deleting the table if exist
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + OwlDataBase.TABLE_NAME;

    }

}

