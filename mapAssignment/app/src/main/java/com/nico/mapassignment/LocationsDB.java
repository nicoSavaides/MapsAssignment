package com.nico.mapassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocationsDB extends SQLiteOpenHelper {
    public static final String PRIMARY_KEY = "primaryKey";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ZOOM_LEVEL = "zoomLevel";

    public static final String DATABASE_TABLE = "locations";
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    " %s integer primary key autoincrement, " +
                    " %s double," +
                    " %s double," +
                    " %s float)",
            DATABASE_TABLE, PRIMARY_KEY, LATITUDE,
            LONGITUDE, ZOOM_LEVEL);

    public LocationsDB(Context context) {
        super(context, DATABASE_TABLE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public void insert(double lat, double lng, float zoom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(LocationsDB.LATITUDE, lat);
        newValues.put(LocationsDB.LONGITUDE, lng);
        newValues.put(LocationsDB.ZOOM_LEVEL, zoom);

        db.insert(LocationsDB.DATABASE_TABLE, null, newValues);
    }

    public void delete(String primaryKey) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = LocationsDB.PRIMARY_KEY + "=?";
        String[] whereArgs = {primaryKey};
        db.delete(LocationsDB.DATABASE_TABLE, whereClause, whereArgs);
    }

    public void returnAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        String[] resultColumns = {LocationsDB.PRIMARY_KEY, LocationsDB.LATITUDE,
                LocationsDB.LONGITUDE, LocationsDB.ZOOM_LEVEL};

        Cursor cursor = db.query(LocationsDB.DATABASE_TABLE, resultColumns, where,
                whereArgs, groupBy, having, order);

        while (cursor.moveToNext()) {
            int primaryKey = cursor.getInt(0);
            String latitude = cursor.getString(1);
            String longitude = cursor.getString(2);
            String zoomLevel = cursor.getString(3);
            Log.d("Table", String.format("%s,%s,%s,%s", primaryKey, latitude, longitude,
                    zoomLevel));

        }
    }
}
