package com.kazy.copedia.openHelper;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "ejdict";

    private static String DB_NAME_ASSET = "ejdict.sqlite3";

    private static final int DATABASE_VERSION = 17;

    private final Context context;

    private final File databasePath;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
        databasePath = this.context.getDatabasePath(DB_NAME);
    }

    private boolean doesDatabaseExist() {
        File dbFile = context.getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            return false;
        }
        SQLiteDatabase db;
        try {
            db = openDataBase();
            Cursor cursor = db.rawQuery("select count(*) from items", null);
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    public void createEmptyDataBase() throws IOException {
        if (!doesDatabaseExist()) {
            getReadableDatabase();
            try {
                copyDataBaseFromAsset();
                String dbPath = databasePath.getAbsolutePath();
                SQLiteDatabase checkDb = null;
                try {
                    checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
                } catch (SQLiteException e) {
                    e.toString();
                }
                if (checkDb != null) {
                    checkDb.setVersion(DATABASE_VERSION);
                    checkDb.close();
                }

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private void copyDataBaseFromAsset() throws IOException {
        InputStream mInput = context.getAssets().open(DB_NAME_ASSET);
        OutputStream mOutput = new FileOutputStream(databasePath);
        byte[] buffer = new byte[1024];
        int size;
        while ((size = mInput.read(buffer)) > 0) {
            mOutput.write(buffer, 0, size);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        return getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
