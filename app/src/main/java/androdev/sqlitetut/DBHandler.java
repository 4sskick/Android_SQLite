package androdev.sqlitetut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 21-Mar-16.
 */
public class DBHandler extends SQLiteOpenHelper {

    //database version
    private static int DB_VERSION = 1;

    //database name
    private static String DB_NAME = "sqliteTut.db";

    //tabel name
    private static String DB_TABLE = "sqliteTable";

    //column name
    private static String COL_ID = "id";
    private static String COL_NAME = "name";
    private static String COL_ADDRESS = "address";

    /**
     * constructor
     *
     * @param context
     */
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    //gonna create db here
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_NAME + " TEXT," +
                COL_ADDRESS + " TEXT )";
        db.execSQL(CREATE_TABLE);
    }

    //gonna upgrade if table was created before
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + DB_TABLE);

        //create again table from beginning
        onCreate(db);
    }

    /**
     * methods for control data that wanna store
     */
    public void addModel(Model model) {
        //write date into db
        SQLiteDatabase db = this.getWritableDatabase();

        //initialize data from model into contentvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, model.getName());
        contentValues.put(COL_ADDRESS, model.getAddress());

        db.insert(DB_TABLE, null, contentValues);
        db.close();//don't forget to close connection port of DB
    }

    //get data from model by id
    public Model getDatamodel(int id) {
        //read data from db
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DB_TABLE, new String[]{COL_ID, COL_NAME, COL_ADDRESS}, COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Model model = new Model(Integer.parseInt(cursor.getString(0)),
                String.valueOf(cursor.getString(1)),
                String.valueOf(cursor.getString(2)));

        return model;
    }

    //get all data from model
    public List<Model> getAllDataModel() {
        List<Model> modelList = new ArrayList<Model>();

        //select all queries
        String selectQuery = "SELECT * FROM " + DB_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model model = new Model();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setName(String.valueOf(cursor.getString(1)));
                model.setAddress(String.valueOf(cursor.getString(2)));

                //adding data into list
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        return modelList;
    }

    //update data in model by ID
    public int updateDataModel(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, model.getName());
        contentValues.put(COL_ADDRESS, model.getAddress());

        //updating rows
        return db.update(DB_TABLE, contentValues, COL_ID + "=?",
                new String[]{
                        String.valueOf(model.getId())
                });
    }

    //delete data in model by ID
    public void deleteDataModel(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(DB_TABLE, COL_ID + "=?",
                new String[]{
                        String.valueOf(model.getId())
                });
        db.close();
    }
}
