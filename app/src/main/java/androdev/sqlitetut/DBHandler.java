package androdev.sqlitetut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androdev.sqlitetut.model.Model;

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
    private static String COL_DATE_BIRTH = "date_birth";
    private static String COL_GENDER = "gender";

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
                COL_ADDRESS + " TEXT," +
                COL_DATE_BIRTH + " TEXT," +
                COL_GENDER + " TEXT )";
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
    public void addDataModel(Model model) {
        //write date into db
        SQLiteDatabase db = this.getWritableDatabase();

        //initialize data from model into contentvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, model.getName());
        contentValues.put(COL_ADDRESS, model.getAddress());
        contentValues.put(COL_DATE_BIRTH, model.getDate_birth());
        contentValues.put(COL_GENDER, model.getGender());

        if (!checkDuplicateDataModel(contentValues)) {
            db.insert(DB_TABLE, null, contentValues);
        }
        db.close();//don't forget to close connection port of DB
    }

    private boolean checkDuplicateDataModel(ContentValues contentValues) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, null, COL_NAME + " = ? AND " + COL_ADDRESS + " = ?",
                new String[]{
                        String.valueOf(contentValues.get(COL_NAME)),
                        String.valueOf(contentValues.get(COL_ADDRESS))
                },
                null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            //duplicate found
            cursor.close();//closed when cursor not null

            return true;
        } else {
            return false;
        }
    }

    //get all data from model
    public List<Model> getSumarryDataModel() {
        List<Model> modelList = new ArrayList<>();

        //select all queries
        String selectQuery = "SELECT * FROM " + DB_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model model = new Model();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setName(String.valueOf(cursor.getString(1)));
                model.setAddress(String.valueOf(cursor.getString(2)));
                model.setDate_birth(String.valueOf(cursor.getString(3)));
                model.setGender(String.valueOf(cursor.getString(4)));

                //adding data into list
                modelList.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return modelList;
    }

    public List<Model> getSummaryDataModelWhere(String stringData) {
        List<Model> modelList = new ArrayList<>();

        //select query
        String selectWhereQuery = "SELECT * FROM " + DB_TABLE + " WHERE " + COL_NAME + " = '" + stringData + "'";

        //configure database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectWhereQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Model model = new Model();
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setName(String.valueOf(cursor.getString(1)));
                model.setAddress(String.valueOf(cursor.getString(2)));
                model.setDate_birth(String.valueOf(cursor.getString(3)));
                model.setGender(String.valueOf(cursor.getString(4)));

                modelList.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return modelList;
    }

    //update data in model by ID
    public void updateDataModel(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, model.getName());
        contentValues.put(COL_ADDRESS, model.getAddress());
        contentValues.put(COL_DATE_BIRTH, model.getDate_birth());
        contentValues.put(COL_GENDER, model.getGender());

        //updating rows
        db.update(DB_TABLE, contentValues, COL_ID + " = ?",
                new String[]{
                        String.valueOf(model.getId())
                }
        );
        db.close();
    }

    //delete data in model by ID
    public void deleteDataModel(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(DB_TABLE, COL_NAME + " = ?",
                new String[]{
                        String.valueOf(model.getName())
                });
        db.close();
    }
}
